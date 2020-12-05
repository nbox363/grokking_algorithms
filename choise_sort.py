def choise_sort(seq):
    """Функция производит сортировку методом вставки"""
    for pos in range(0, len(seq)-1):
        for i in range(pos+1, len(seq)):
            if seq[i] < seq[pos]:
                seq[i], seq[pos] = seq[pos], seq[i]
    return seq


################################################################


def find_smallest(seq):
    """Функция находит минимум в последовательности"""
    smallest = seq[0]
    smallest_index = 0
    for i, v in enumerate(seq):
        if v < smallest:
            smallest = v
            smallest_index = i
    return smallest_index


def selection_sort(seq):
    """Функция производит сортировку методом вставки"""
    res = []
    for i in range(len(seq)):
        smallest = find_smallest(seq)
        res.append(seq.pop(smallest))
    return res
