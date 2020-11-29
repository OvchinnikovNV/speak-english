import torch
from neural_network import NeuralNetwork
from text_formatter import bag_of_words
import random


tags = ''
all_words = ''
model = ''
device = ''


def init():
    global tags
    global all_words
    global model
    global device

    device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

    FILE = "model.pth"
    data = torch.load(FILE)

    input_size = data["input_size"]
    hidden_size = data["hidden_size"]
    output_size = data["output_size"]
    all_words = data['all_words']
    tags = data['tags']
    model_state = data["model_state"]

    model = NeuralNetwork(input_size, hidden_size, output_size).to(device)
    model.load_state_dict(model_state)
    model.eval()


def getTag():
    return random.choice(tags)


def getAccuracy(word, chosen_tag):
    word = word.lower()
    m_x = bag_of_words(word, all_words)
    m_x = m_x.reshape(1, m_x.shape[0])
    m_x = torch.from_numpy(m_x).to(device)

    output = model(m_x)
    _, prediction = torch.max(output, dim=1)

    tag = tags[prediction.item()]

    if tag == chosen_tag:
        return '1'
    else:
        return '0'
