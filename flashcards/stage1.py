# -*- coding: utf-8 -*-

FIRST_LINE = "Card:"
THIRD_LINE = "Definition:"
LINE_COUNT = 4


def generate():
    return [""]


def check(reply, clue):
    lines = reply.splitlines()

    if len(lines) != LINE_COUNT:
        return (
            False,
            "Your program prints %s lines: %s\n%s lines were expected." % (len(lines), lines, LINE_COUNT)
        )

    if lines[0] != FIRST_LINE:
        return (
            False,
            'Your first line is "%s" but "%s" was expected' % (lines[0], FIRST_LINE)
        )

    if lines[2] != THIRD_LINE:
        return (
            False,
            'Your third line is "%s" but "%s" was expected' % (lines[2], THIRD_LINE)
        )

    return (True, "Good job! :)")


def solve(dataset):
    return "%s\npurchase\n%s\nbuy\n" % (FIRST_LINE, THIRD_LINE)
