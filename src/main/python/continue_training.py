import tensorflow as tf

from lib import split_input_target, to_string, read_training_data, build_dataset

data_path = 'data/'
training_file = data_path + 'data.txt'
model_file = data_path + 'model'
n_input = 5

# Configuration
num_epochs = 3
batch_size = 40
num_shuffles = 100
# LSTM layers, the numbers are number of output, to the next layer but also to the next same type of layer (ordered).
n_hidden = [512, 256, 128]
# number of words sequence to predict the following words
n_input = 10
# number of words generated by the RNN
n_output = 10

model = tf.keras.models.load_model(
    data_path + 'model.h5', custom_objects=None, compile=True
)
training_data = read_training_data(training_file)

word_to_int_map, int_to_word_map = build_dataset(training_data)
words_as_int = [word_to_int_map[w] for w in training_data]
# create tf.data.Dataset object, data encoded as integers
word_dataset = tf.data.Dataset.from_tensor_slices(words_as_int)

# take method generates elements (to verify):
for i in word_dataset.take(5):
    print(int_to_word_map[i.numpy()])

# The `batch` method creates dataset, that generates sequences of elements:
# obtain batches of size number of input +1, therefore these words are linked so then we can shuffle them after
sequences = word_dataset.batch(n_input + 1,
                               drop_remainder=True)

for item in sequences.take(5):
    print(to_string(item.numpy(), int_to_word_map))

dataset = sequences.map(split_input_target)

# Finally we shuffle the items, and produce minibatches of 16 elements:
# 16 batches to save resources dataset
dataset = dataset.shuffle(num_shuffles).batch(batch_size, drop_remainder=True)

save_checkpoint = tf.keras.callbacks.ModelCheckpoint('checkpoints/checkpoint_retrain.{epoch:02d}.hdf5',
                                                     save_weights_only=False, save_best_only=False, save_freq=100000,
                                                     verbose=1)
# train model
history = model.fit(dataset, callbacks=[save_checkpoint], epochs=num_epochs, verbose=1)
# save model
model.save(model_file + '.h5')
