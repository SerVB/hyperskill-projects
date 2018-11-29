# -*- coding: utf-8 -*-

FRACTION_SYMBOLS = 5

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


def fracToFloat(string, base):
    assert 2 <= base <= 36

    string = string.lower()

    answer = 0.0
    invertedDegree = base

    while len(string) != 0:
        answer += float(SYMBOLS.find(string[0])) / float(invertedDegree)
        string = string[1:]
        invertedDegree *= base

    return answer


def floatToString(fl, base):
    answer = ""

    while len(answer) < FRACTION_SYMBOLS:
        fl *= base
        answer += SYMBOLS[int(fl)]
        fl -= int(fl)

    return answer

def generate():
    tests = [
        ((10, "0.234", 7), "0.14315"),
        ((10, "10.234", 7), "13.14315"),
        ((6, "2.555", 1), "11"),
        ((35, "af.xy", 17), "148.g88a8"),
        ((10, "11", 2), "1011"),
        ((16, "aaaaa.0", 24), "22df2.00000"),
        ((16, "0.cdefb", 24), "0.j78da"),
        ((16, "aaaaa.cdefb", 24), "22df2.j78da"),
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

    answer = lines[-1]

    if answer != clue:
        return (False, "Your answer is wrong: '%s'" % answer)

    return (True, "Good job! :)")


def solve(dataset):
    base, i, targetBase = dataset.splitlines()
    base, targetBase = int(base), int(targetBase)

    if targetBase == 1 and "." in i:
        i = i[:i.find(".")]

    if "." not in i:
        number = stringToInt(i, base)
        answer = intToString(number, targetBase)

        return answer
    else:
        dotPos = i.find(".")
        intNumber = stringToInt(i[:dotPos], base)
        intAnswer = intToString(intNumber, targetBase)

        fracNumber = fracToFloat(i[dotPos + 1:], base)
        fracAnswer = floatToString(fracNumber, targetBase)

        return intAnswer + "." + fracAnswer
