import numpy as np
import pymorphy2

morph = pymorphy2.MorphAnalyzer()


def normal_word(word):
    return morph.parse(word)[0].normal_form


def bag_of_words(tokenized_sentence, words):
    # создаем "сумку слов" из нулей
    bag = np.zeros(len(words), dtype=np.float32)
    for id, w in enumerate(words):
        if w in tokenized_sentence:
            bag[id] = 1

    return bag
