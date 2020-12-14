#!/usr/bin/env python3

expected = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
valids = 0
with open("input") as f:
    line = True
    while line:
        found = {key: False for key in expected}
        while ((line := f.readline()) != "\n" and line):
            for entry in line.split():
                key = entry.split(":")[0]
                if key in expected:
                    found[key] = True
        if False not in found.values():
            valids += 1
print(valids)
