import random


def quick_sort(seq):
    if len(seq) <= 1:
        return
    barier = random.choice(seq)
    L, R, M = [], [], []
    for x in seq:
        if x < barier:
            L.append(x)
        elif x == barier:
            M.append(x)
        else:
            R.append(x)
    quick_sort(L)
    quick_sort(R)
    k = 0
    for x in L+M+R:
        seq[k] = x
        k += 1


def quicksort(seq):
    if len(seq) < 2:
        return seq
    else:
        barier = seq[0]
        less = [i for i in seq[1:] if i <= barier]
        greater = [i for i in seq[1:] if i > barier]

        return quicksort(less) + [barier] + quicksort(greater)


print(quicksort([10, 5, 3, 2]))
