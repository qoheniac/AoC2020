#!/usr/bin/env python3

result = 0
with open("input") as f:
    line = True
    while line:
        answers = set()
        while ((line := f.readline()) != "\n" and line):
            for answer in line[:-1]:
                answers.add(answer)
        result += len(answers)
print(result)
