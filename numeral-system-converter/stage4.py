# -*- coding: utf-8 -*-

SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz"


def intToString(number, base):
    assert 1 <= base <= 36

    if base == 1:
        return "1" * number

    if number < base:
        return SYMBOLS[number]
    else:
        return intToString(number // base, base) + SYMBOLS[number % base]


def stringToInt(string, base):
    assert 1 <= base <= 36

    if base == 1:
        return len(string)

    string = string.lower()

    answer = 0
    degree = 1

    while len(string) != 0:
        answer += degree * SYMBOLS.find(string[-1])
        string = string[:-1]
        degree *= base

    return answer


def generate():
    tests = [
        ((10, "11", 2), "1011"),
        ((1, "11111", 10), "5"),
        ((10, "1000", 36), "rs"),
        ((21, "4242", 6), "451552"),
        ((7, "12", 11), "9"),
        ((5, "300", 10), "75"),
        ((1, "11111", 5), "10"),
        ((10, "4", 1), "1111"),
    ]

    return list(
        map(
            lambda test: ("%s\n%s\n%s\n" % (test[0][0], test[0][1], test[0][2]), test[1]),
            tests
        )
    )


def check(reply, clue):
    lines = reply.splitlines()

    if len(lines) == 0:
        return (False, "Your output is empty")

    answer = lines[-1].lower()

    if answer != clue:
        return (False, "Your answer is wrong: '%s'" % answer)

    return (True, "Good job! :)")


def solve(dataset):
    base, i, targetBase = dataset.splitlines()
    base, targetBase = int(base), int(targetBase)

    number = stringToInt(i, base)
    answer = intToString(number, targetBase)

    return answer
