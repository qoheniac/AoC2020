#!/usr/bin/env python3
from numpy import loadtxt
from sys import exit

with open("input") as f:
    data = loadtxt(f, dtype=int)
    N = len(data)
    for i in range(N - 1):
        a = data[i]
        for b in data[i + 1:]:
            if a + b == 2020:
                print(a * b)
                exit(0)
