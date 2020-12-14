#!/usr/bin/env python3

trees = 0
with open("input") as f:
    width = len(f.readline()[:-1])
    for i, line in enumerate(f):
        if line[(3 * i + 3) % width] == "#":
            trees += 1
print(trees)
