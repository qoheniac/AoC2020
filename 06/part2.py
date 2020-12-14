#!/usr/bin/env python3

result = 0
with open("input") as f:
    line = True
    while line:
        answers = set(f.readline()[:-1])
        while ((line := f.readline()) != "\n" and line):
            answers = answers.intersection(set(line[:-1]))
        result += len(answers)
print(result)
