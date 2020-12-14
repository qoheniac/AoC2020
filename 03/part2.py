#!/usr/bin/env python3

result = 1
for right, down in [(1, 1), (3, 1), (5, 1), (7, 1), (1, 2)]:
    with open("input") as f:
        trees = 0
        width = len(f.readline()[:-1])
        for i, line in enumerate(f):
            if (i + 1) % down == 0:
                if line[(right * (1 + i // down)) % width] == "#":
                    trees += 1
        result *= trees
print(result)
