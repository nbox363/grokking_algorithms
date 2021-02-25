from collections import deque


data = {
    'a': ['b', 'c'],
    'b': ['f'],
    'c': ['d', 'e'],
    'd': ['b', 'f'],
    'e': ['a', 'f', 'g'],
    'f': ['a'],
    'g': ['ok']
}


def search(el):
    search_queue = deque()
    search_queue += data[el]
    searched = []
    while search_queue:
        d = search_queue.popleft()
        if not d in searched:
            if d == 'ok':
                return d
            else:
                search_queue += data[d]
                searched.append(d)
    return False



