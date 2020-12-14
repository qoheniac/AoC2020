#!/usr/bin/env python3

from numpy import loadtxt, append

data = loadtxt("input", dtype=int)
data.sort()
data = append(0, data)
data = append(data, data[-1] + 3)
Δ = data[1:] - data[:-1]
onecount = [len(ones) for ones in "".join([str(δ) for δ in Δ]).split("3")]
combinations = 1
for count in onecount:
    if count == 2:
        combinations *= 2
    elif count == 3:
        combinations *= 4
    elif count == 4:
        combinations *= 7
print(combinations)
