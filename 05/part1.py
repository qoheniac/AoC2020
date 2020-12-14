#!/usr/bin/env python3

IDs = set()
with open("input") as f:
    for line in f:
        s = line[:-1]
        s = s.replace("B", "1")
        s = s.replace("R", "1")
        s = s.replace("F", "0")
        s = s.replace("L", "0")
        IDs.add(int(s, base=2))
print(max(IDs))
