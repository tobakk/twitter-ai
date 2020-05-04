import random
import sys

import numpy as np
import tensorflow as tf

from lib import build_dataset, read_training_data, to_string

data_path = 'data/'
training_file = data_path + 'data.txt'
model_file = data_path + 'model.h5'
n_input = 10

model = tf.keras.models.load_model(
    data_path + 'model.h5', custom_objects=None, compile=True
)

training_data = read_training_data(training_file)

dictionary, reverse_dictionary = build_dataset(training_data)


def generate_tweet(model, word_id_arr, number_of_words=20):
    out = []
    words = list(word_id_arr.copy())
    for i in range(number_of_words):
        keys = np.reshape(np.array(words), [-1, n_input])

        onehot_pred = model(keys).numpy()[0]
        pred_index = onehot_pred.argmax(axis=1)
        pred = pred_index[-1]
        out.append(pred)

        words = words[1:]
        words.append(pred)
    sentence = to_string(out, reverse_dictionary)
    return sentence


words = random.choices(training_data, k=n_input)
symbols_in_keys = [dictionary[str(words[i])] for i in range(len(words))]
print(generate_tweet(model, symbols_in_keys))

sys.exit(0)
