def summator(seq):
    if len(seq) == 0:
        return 0
    return seq[0] + summator(seq[1:])


def count(seq):
    if len(seq) == 0:
        return 0
    return 1 + count(seq[1:])


def search_max(seq):
    if len(seq) == 1:
        return seq[0]
    if len(seq) == 2:
        return seq[0] if seq[0] > seq[1] else seq[1]
    sub_max = max(seq[1:])
    return seq[0] if seq[0] > sub_max else sub_max
