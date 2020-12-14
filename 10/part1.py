#!/usr/bin/env python3

from numpy import loadtxt, append, count_nonzero

data = loadtxt("input", dtype=int)
data.sort()
data = append(0, data)
data = append(data, data[-1] + 3)
Δ = data[1:] - data[:-1]
print(count_nonzero(Δ == 1) * count_nonzero(Δ == 3))
