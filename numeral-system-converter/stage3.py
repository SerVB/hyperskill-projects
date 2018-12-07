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


PREFIXES = {2: "0b", 8: "0", 16: "0x"}


def generate():
    tests = [
        (11, 2),
        (8, 8),
        (0, 16),
    ]

    for i in range(101, 105):
        for base in (2, 8, 16):
            tests.append((i, base))

    return list(
        map(
            lambda data: (data, solve(data)),
            map(
                lambda test: "%s\n%s\n" % (test[0], test[1]),
                tests
            )
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
    i, base = map(int, dataset.splitlines())

    return PREFIXES[base] + intToString(i, base)
