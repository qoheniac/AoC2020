#!/usr/bin/env python3

from sys import exit

programm = []
with open("input") as f:
    for line in f:
        keyword, offset = line.split()
        programm.append([keyword, int(offset), False])

SLOC = len(programm)
for fix_address in range(SLOC):
    address = 0
    accumulator = 0
    while not (command := programm[address])[2]:
        command[2] = True
        keyword, offset = command[:2]
        jmp = "nop" if address == fix_address else "jmp"
        address += offset if keyword == jmp else 1
        if keyword == "acc":
            accumulator += offset
        if address >= SLOC:
            print(accumulator)
            exit()
    for command in programm:
        command[2] = False
