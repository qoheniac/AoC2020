#!/usr/bin/env python3

rules = {}
with open("input") as f:
    for line in f:
        color, content = line[:-1].split(" bags contain")
        for word in ["bags.", "bag.", "bags", "bag"]:
            content = content.replace(" " + word, "")
        if content == " no other":
            content = [(0, "no other")]
        else:
            content = content.split(",")
            for i in range(len(content)):
                content[i] = (int(content[i][1]), content[i][3:])
        rules[color] = content


def bags_needed(color):
    content = rules[color]
    if content[0][0] == 0:
        return 0
    else:
        result = 0
        for num, color in content:
            result += num
            result += num * bags_needed(color)
        return result


print(bags_needed("shiny gold"))
