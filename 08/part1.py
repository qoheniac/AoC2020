#!/usr/bin/env python3

programm = []
with open("input") as f:
    for line in f:
        keyword, offset = line.split()
        programm.append([keyword, int(offset), False])

address = 0
accumulator = 0
while not (command := programm[address])[2]:
    command[2] = True
    keyword, offset = command[:2]
    address += offset if keyword == "jmp" else 1
    if keyword == "acc":
        accumulator += offset
print(accumulator)
