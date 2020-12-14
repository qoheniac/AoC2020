#!/usr/bin/env python3

from numpy import loadtxt
from sys import exit

invalid = 217430975
data = loadtxt("input", dtype=int)
N = len(data)
for start in range(N - 1):
    Σ = data[start] + data[start + 1]
    for end in range(start + 2, N):
        Σ += data[end]
        if Σ == invalid:
            print(data[start:end].min() + data[start:end].max())
            exit()
