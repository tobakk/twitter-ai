import collections

import matplotlib.pyplot as plt
import numpy as np
import tensorflow as tf

data_path = '/home/odin/git/twitter-ai/data/'
training_file = data_path + 'data.txt'
model_file = data_path + 'model'


def read_training_data(filename):
    with open(filename) as f:
        content = f.readlines()
    content = [x.strip() for x in content]
    content = [word for i in range(len(content)) for word in content[i].split()]
    content = np.array(content)
    return content


def build_dataset(words):
    count = collections.Counter(words).most_common()
    dictionary = {}
    for word, _ in count:
        dictionary[word] = len(dictionary)
    reverse_dictionary = dict(zip(dictionary.values(), dictionary.keys()))
    return dictionary, reverse_dictionary


def split_input_target(chunk):
    input_text = chunk[:-1]
    target_text = chunk[1:]

    return input_text, target_text


# helper for int-to-text conversion
def to_string(number_array, int_to_word_map):
    result = ''
    for index in number_array:
        result += int_to_word_map[index] + ' '
    return result


def plot_graph(history):
    fig, axs = plt.subplots(1, 2, figsize=(10, 5))
    axs[0].plot(history.epoch, history.history['loss'])
    if 'val_loss' in history.history:
        axs[0].plot(history.epoch, history.history['val_loss'])
    axs[0].legend(('training loss', 'validation loss'))
    axs[1].plot(history.epoch, history.history['accuracy'])
    if 'val_accuracy' in history.history:
        axs[1].plot(history.epoch, history.history['val_accuracy'])

    axs[1].legend(('training accuracy', 'validation accuracy'))
    plt.show()


def create_tf_dataset(int_array, number_of_inputs, number_of_shuffles, batch_size):
    # create tf.data.Dataset object, data encoded as integers
    word_dataset = tf.data.Dataset.from_tensor_slices(int_array)

    sequences = word_dataset.batch(number_of_inputs + 1, drop_remainder=True)
    dataset = sequences.map(split_input_target)
    return dataset.dataset.shuffle(number_of_shuffles).batch(batch_size, drop_remainder=True)
