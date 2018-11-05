# -*- coding: utf-8 -*-

WRONG = "wrong"
RIGHT = "right"


def generate():
    return [
        ("a purring animal\ncat\ncat\n", RIGHT),
        ("a purring animal\ncat\n????\n", WRONG),
        ("a barking animal\ndog\ncat\n", WRONG),
        ("a barking animal\ndog\ndog\n", RIGHT)
    ]


def check(reply, clue):
    reply = reply.strip().lower()

    onlyLetters = "".join(map(lambda char: char if char.isalpha() else " ", reply))

    words = tuple(filter(lambda word: len(word) != 0, onlyLetters.split()))

    hasRight = RIGHT.lower() in words
    hasWrong = WRONG.lower() in words

    if (not hasRight) and (not hasWrong):
        return (False, 'Your output has not "%s" or "%s" words...' % (RIGHT, WRONG))

    if hasRight and hasWrong:
        return (False, 'Your output has both "%s" and "%s" words. Are you a cheater?' % (RIGHT, WRONG))

    if hasRight and clue == WRONG:
        return (False, 'Expected %s.' % WRONG)

    if hasWrong and clue == RIGHT:
        return (False, 'Expected %s.' % RIGHT)

    return (True, "Good job! :)")


def solve(dataset):
    _, definition, answer = dataset.splitlines()

    if definition == answer:
        return "Your answer is %s!\n" % RIGHT

    return "Your answer is %s...\n" % WRONG
