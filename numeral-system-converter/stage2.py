# -*- coding: utf-8 -*-


def generate():
    tests = (11, 42) + tuple(range(0, 400, 21))
    return list(map(lambda n: (str(n) + "\n", n), tests))


def check(reply, clue):
    lines = reply.splitlines()

    if len(lines) == 0:
        return (False, "Your output is empty")

    answer = lines[-1]

    if answer != str(clue % 2):
        return (False, "Your answer is wrong: '%s'" % answer)

    return (True, "Good job! :)")


def solve(dataset):
    return str(int(dataset.splitlines()[0]) % 2) + "\n"
