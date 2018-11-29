# -*- coding: utf-8 -*-

import random


def generate():
    tests = list()

    for i in range(1, 10 + 1):
        test = (str(i) + "\n", i)
        tests.append(test)
        tests.append(test)

    tests.append(("11\n", 11))
    tests.append(("42\n", 42))

    return tests


def getWrongNumberVerdict(number, clue):
    if len(number) != clue:
        return '"%s" has a wrong length (%s was expected)' % (number, clue)

    if len(number) != len(set(number)):
        return '"%s" has a similar symbols' % number

    assert False, "The number is right (%s, %s)" % (number, clue)


def check(reply, clue):
    if clue > 10:
        return (True, "Good job! :)")

    onlyDigits = "".join(map(lambda char: char if char.isdigit() else " ", reply))

    numbers = tuple(filter(lambda word: len(word) != 0, onlyDigits.split()))

    if any(map(lambda number: clue == len(number) == len(set(number)), numbers)):
        return (True, "Good job! :)")

    verdicts = map(lambda number: getWrongNumberVerdict(number, clue), numbers)

    return (False, "All numbers are wrong:\n" + "\n".join(verdicts))


def solve(dataset):
    digits = int(dataset.splitlines()[0])

    if digits > 10:
        return "Sorry\n"

    code = ""

    while digits > 0:
        nextDigit = str(random.randrange(0, 10))

        while nextDigit in code:
            nextDigit = str(random.randrange(0, 10))

        code += nextDigit

        digits -= 1

    return "some symbols " + code + ", and more symbols\n"
