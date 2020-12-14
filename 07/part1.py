#!/usr/bin/env python3

rules = {}
with open("input") as f:
    for line in f:
        color, content = line[:-1].split(" bags contain")
        for num in range(9):
            content = content.replace(f" {num} ", "")
        for word in ["bags.", "bag.", "bags", "bag"]:
            content = content.replace(" " + word, "")
        rules[color] = content.split(",")


def can_contain_shiny_gold(color):
    content = rules[color]
    if "shiny gold" in content:
        return True
    elif " no other" in content:
        return False
    else:
        result = False
        for color in content:
            result = result or can_contain_shiny_gold(color)
        return result


count = 0
for color in rules:
    if can_contain_shiny_gold(color):
        count += 1
print(count)
