# -*- coding: utf-8 -*-

CODE_LENGTH = 4


def generate():
    return [
        ("1234\n", "1234"),
        ("9087\n", "9087"),
        ("8067\n", "8067"),
        ("4561\n", "4561")
    ]


def calcBullsAndCows(secret, code):
    bulls = 0
    cows = 0

    for idx, symbol in enumerate(code):
        if secret[idx] == symbol:
            bulls += 1
        elif symbol in secret:
            cows += 1

    return (bulls, cows)


def check(reply, clue):
    line = reply.splitlines()[0]

    onlyDigits = "".join(map(lambda char: char if char.isdigit() else " ", line))

    numbers = tuple(filter(lambda word: len(word) != 0, onlyDigits.split()))

    secrets = tuple(filter(lambda number: CODE_LENGTH == len(number) == len(set(number)), numbers))

    if len(secrets) != 1:
        return (False, 'Cannot find a single secret number in "%s"' % line)

    secret = secrets[0]

    bulls, cows = calcBullsAndCows(secret, clue)
    hasBulls, hasCows = False, False

    words = line.split()

    for wordIdx, word in enumerate(words):
        if "bull" in word and "cow" in word:
            return (False, "Wrong word: %s" % word)

        if "bull" in word and wordIdx - 1 >= 0:
            if not hasBulls:
                bullCount = words[wordIdx - 1]

                if bullCount.isdigit():
                    if int(bullCount) != bulls:
                        return (False, "Wrong bull count in line: %s" % line)
                else:
                    return (False, "There is no bull count in line: %s" % line)

                hasBulls = True
            else:
                return (False, "Many bull words in line: %s" % line)

        if "cow" in word and wordIdx - 1 >= 0:
            if not hasCows:
                cowCount = words[wordIdx - 1]

                if cowCount.isdigit():
                    if int(cowCount) != cows:
                        return (False, "Wrong cow count in line: %s" % line)
                else:
                    return (False, "There is no cow count in line: %s" % line)

                hasCows = True
            else:
                return (False, "Many cows words in line: %s" % line)

    if bulls != 0 and (not hasBulls):
        return (False, "There is no info about bulls in line: %s" % line)

    if cows != 0 and (not hasCows):
        return (False, "There is no info about cows in line: %s" % line)

    return (True, "Good job! :)")


def solve(dataset):
    code = dataset.splitlines()[0]
    secret = "1234"
    return "%s bull(s) and %s cow(s)" % calcBullsAndCows(secret, code) + " secret is " + secret
