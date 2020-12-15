from asynctest import TestCase
import unittest

from funcy import omit
from marshmallow import INCLUDE, RAISE

from compel_shared.logger import get_logger
from compel_shared.model import DB
from compel_shared.classes.class_ import ClassImport, Class
from compel_shared.conf.service_config import ServiceConfig
from compel_shared.params.param import Param
from compel_shared.params.param_import import ParamImport
from compel_shared.params.unit import UnitTemplate
from compel_shared.params.recog_template import RecogTemplate

log = get_logger(__name__)


class MongoConfigurationTest(TestCase):
    """
    Проверка конфигураций на соответствие схемам
    """

    async def setUp(self) -> None:
        self.db = DB('test').db
        self.collections = {
            'classes': Class,
            'classes_import': ClassImport,
            'param': Param,
            'param_import': ParamImport,
            'unit_template': UnitTemplate,
            'recog_template': RecogTemplate,
            'service_config': ServiceConfig
        }

    @staticmethod
    def _write_log(accumulated_errors, extra_fields):
        for collection_name, errors in accumulated_errors.items():
            for error in errors:
                log.error(f'error in collection - {collection_name}, document id = {error[0]}: errors {error[1]}')

        for collection_name, extra_fields in extra_fields.items():
            for _id, *fields in extra_fields:
                log.error(f'extra fields in collection {collection_name}, document_id = {_id}: extra fields: {fields}')

    @staticmethod
    def _check_document_for_compliance_with_schema(document, schema, collection, accumulated_errors):
        """
        Проверяет документы на соответствие схемам
        """
        validation = omit(schema.validate(document), {'created', 'updated', '_id'})
        if validation:  # значит есть несоответствие схеме
            error_info = document['_id'], validation
            accumulated_errors[f'{collection}'].append(error_info)

    @staticmethod
    def _check_document_for_extra_fields(document, schema, collection, extra_fields):
        """
        Проверяет документы на наличие лишних полей
        """
        fields = [field for field in schema.fields] + ['created', 'updated']
        extra_fields_from_document = [document['_id']]
        for field in document:
            if field.lstrip('_') not in fields:  # '_id'
                extra_fields_from_document.append(field)

        if len(extra_fields_from_document) > 1:
            extra_fields[f'{collection}'].append(extra_fields_from_document)

    async def test_collections(self):
        accumulated_errors = dict([(col, []) for col in self.collections])
        extra_fields = dict([(col, []) for col in self.collections])

        for collection, schema in self.collections.items():
            schema = schema.Schema()
            async for document in self.db.get_collection(collection).find({}):
                self._check_document_for_compliance_with_schema(document, schema, collection, accumulated_errors)
                self._check_document_for_extra_fields(document, schema, collection, extra_fields)

        self._write_log(accumulated_errors, extra_fields)

        for k, v in accumulated_errors.items():
            if not v:
                accumulated_errors = omit(accumulated_errors, k)

        self.assertEqual(accumulated_errors, {})


if __name__ == '__main__':
    unittest.main()
