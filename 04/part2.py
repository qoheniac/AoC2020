#!/usr/bin/env python3

hexdigits = set("0123456789abcdef")
rules = {
    "byr": lambda s: s.isdigit() and int(s) >= 1920 and int(s) <= 2002,
    "iyr": lambda s: s.isdigit() and int(s) >= 2010 and int(s) <= 2020,
    "eyr": lambda s: s.isdigit() and int(s) >= 2020 and int(s) <= 2030,
    "hgt": lambda s: len(s) > 3
    and (h := s[:-2]).isdigit()
    and (
        (s[-2:] == "cm" and int(h) >= 150 and int(h) <= 193)
        or (s[-2:] == "in" and int(h) >= 59 and int(h) <= 76)
    ),
    "hcl": lambda s: len(s) == 7 and s[0] == "#" and set(s[1:]).issubset(hexdigits),
    "ecl": lambda s: s in ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"],
    "pid": lambda s: len(s) == 9 and s.isdigit(),
}
valids = 0
with open("input") as f:
    line = True
    while line:
        valid = {key: False for key in rules.keys()}
        while (line := f.readline()) != "\n" and line:
            for entry in line.split():
                key, value = entry.split(":")
                if key in rules.keys() and rules[key](value):
                    valid[key] = True
        if False not in valid.values():
            valids += 1
print(valids)
