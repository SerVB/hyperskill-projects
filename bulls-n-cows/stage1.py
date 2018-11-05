# -*- coding: utf-8 -*-

CODE_LENGTH = 4
MIN_TURN_COUNT = 2


def generate():
    return [""]


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
    lines = reply.splitlines()

    onlyDigits = "".join(map(lambda char: char if char.isdigit() else " ", lines[-1]))

    numbers = tuple(filter(lambda word: len(word) != 0, onlyDigits.split()))

    secrets = tuple(filter(lambda number: CODE_LENGTH == len(number) == len(set(number)), numbers))

    if len(secrets) != 1:
        return (False, 'Cannot find a single secret number in "%s"' % lines[-1])

    secret = secrets[0]

    turnCount = 0

    for idx, line in enumerate(lines):
        if line.isdigit() and idx + 1 < len(lines):
            if CODE_LENGTH == len(line) == len(set(line)):
                turnCount += 1

                bulls, cows = calcBullsAndCows(secret, line)
                hasBulls, hasCows = False, False

                nextLine = lines[idx + 1]

                words = nextLine.split()

                for wordIdx, word in enumerate(words):
                    if "bull" in word and "cow" in word:
                        return (False, "Wrong word: %s" % word)

                    if "bull" in word and wordIdx - 1 >= 0:
                        if not hasBulls:
                            bullCount = words[wordIdx - 1]

                            if bullCount.isdigit():
                                if int(bullCount) != bulls:
                                    return (False, "Wrong bull count in line: %s" % nextLine)
                            else:
                                return (False, "There is no bull count in line: %s" % nextLine)

                            hasBulls = True
                        else:
                            return (False, "Many bull words in line: %s" % nextLine)

                    if "cow" in word and wordIdx - 1 >= 0:
                        if not hasCows:
                            cowCount = words[wordIdx - 1]

                            if cowCount.isdigit():
                                if int(cowCount) != cows:
                                    return (False, "Wrong cow count in line: %s" % nextLine)
                            else:
                                return (False, "There is no cow count in line: %s" % nextLine)

                            hasCows = True
                        else:
                            return (False, "Many cows words in line: %s" % nextLine)

                if bulls != 0 and (not hasBulls):
                    return (False, "There is no info about bulls in line: %s" % nextLine)

                if cows != 0 and (not hasCows):
                    return (False, "There is no info about cows in line: %s" % nextLine)
            else:
                return (False, "Invalid answer: %s" % line)

    if turnCount < MIN_TURN_COUNT:
        return (False, "There are too few turns: %s. You should submit at least %s turns." % (turnCount, MIN_TURN_COUNT))

    return (True, "Good job! :)")


def solve(dataset):
    return """The secret is prepared: ****.

Turn 1. Answer:
1234
Grade: 1 cow.

Turn 2. Answer:
5678
Grade: 1 cow.

Turn 3. Answer:
9012
Grade: 1 bull and 1 cow.

Turn 4. Answer:
9087
Grade: 1 bull and 1 cow.

Turn 5. Answer:
1087
Grade: 1 cow.

Turn 6. Answer:
9205
Grade: 3 bulls.

Turn 7. Answer:
9305
Grade: 4 bulls.
Congrats! The secret number is 9305."""
