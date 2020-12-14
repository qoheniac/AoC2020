#!/usr/bin/env python3

valids = 0
with open("input") as f:
    while(line := f.readline()):
        cols = line.split()
        i, j = (int(n) - 1 for n in cols[0].split('-'))
        char = cols[1][:-1]
        password = cols[2]
        if (password[i] == char) != (password[j] == char):
            valids += 1
print(valids)
