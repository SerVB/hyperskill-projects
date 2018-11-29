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


BOOLEAN_PREFIX = "0b"


def generate():
    return [""]


def check(reply, clue):
    lines = reply.splitlines()

    if len(lines) != 1:
        return (
            False,
            "Your program prints more than one line: %s" % lines
        )

    words = set(lines[0].split())

    booleans = list(
        filter(
            lambda word: len(word) >= len(BOOLEAN_PREFIX) and
                         word[:len(BOOLEAN_PREFIX)] == BOOLEAN_PREFIX,
            words
        )
    )

    if len(booleans) != 1:
        return (
            False,
            "Your program prints more than one boolean: %s" % booleans
        )

    boolean = booleans[0][len(BOOLEAN_PREFIX):]

    numbers = list(filter(lambda word: len(word) > 0 and word.isdigit(), words))

    if len(numbers) != 1:
        return (
            False,
            "Your program prints more than one number: %s" % numbers
        )

    number = int(numbers[0])

    correctBoolean = intToString(number, 2)

    if boolean != correctBoolean:
        return (
            False,
            "%s == %s%s != %s%s" % (number,
                                    BOOLEAN_PREFIX, correctBoolean,
                                    BOOLEAN_PREFIX, boolean)
        )

    return (True, "Good job! :)")


def solve(dataset):
    return "2 is equal to %s10" % BOOLEAN_PREFIX
