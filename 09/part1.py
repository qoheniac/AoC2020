#!/usr/bin/env python3

from sys import exit

N = 25
with open("input") as f:
    block = []
    for i in range(N):
        block.append(int(f.readline()[:-1]))
    for line in f:
        number = int(line[:-1])
        is_sum = False
        for i in range(N - 1):
            for j in range(i + 1, N):
                if block[i] + block[j] == number:
                    is_sum = True
        if is_sum:
            block = block[1:] + [number]
        else:
            print(number)
            exit(0)
