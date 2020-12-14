#!/usr/bin/env python3

valids = 0
with open("input") as f:
    while(line := f.readline()):
        cols = line.split()
        least, most = (int(n) for n in cols[0].split('-'))
        char = cols[1][:-1]
        password = cols[2]
        occurrences = 0
        for c in password:
            if c == char:
                occurrences += 1
        if occurrences >= least and occurrences <= most:
            valids += 1
print(valids)
