#!/usr/bin/env python3

from numpy import array, unique

data = []
with open("input") as f:
    for line in f:
        data += [array(list(line[:-1]))]
    data = array(data)
rows = len(data)
cols = len(data[0])


def count(data, i, j):
    c = 0
    for m in range(max(0, i - 1), min(i + 2, rows)):
        for n in range(max(0, j - 1), min(j + 2, cols)):
            if data[m, n] == "#":
                c += 1
    return c


def update(data):
    new_data = data.copy()
    for i, row in enumerate(data):
        for j, loc in enumerate(row):
            if not loc == ".":
                c = count(data, i, j)
                if c == 0:
                    new_data[i, j] = "#"
                elif c >= 5:
                    new_data[i, j] = "L"
    return new_data


while (data != (data := update(data))).any():
    pass
print(dict(list(zip(*unique(data, return_counts=True))))["#"])
