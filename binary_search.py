def binary_search(seq:list, item:int) -> int:
    """ Функция производит бинарный поиск
        Список должен быть отстортированным
    """
    low = 0
    high = len(seq)-1
    while low <= high: 
        mid = (low + high) // 2
        guess = seq[mid]
        if guess == item:
            return mid
        elif guess < item: 
            low = mid + 1 
        else: 
            high = mid - 1
    return None
