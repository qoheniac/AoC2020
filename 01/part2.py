#!/usr/bin/env python3
from numpy import loadtxt
from sys import exit

with open("input") as f:
    data = loadtxt(f, dtype=int)
    N = len(data)
    for i in range(N - 2):
        a = data[i]
        for j in range(i + 1, N - 1):
            b = data[j]
            for c in data[j + 1 :]:
                if a + b + c == 2020:
                    print(a * b * c)
                    exit(0)
