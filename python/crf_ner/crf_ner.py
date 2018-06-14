"""
author: seaboat
date: 2018-06-14
version: 1.0
email: 849586227@qq.com
blog: http://blog.csdn.net/wangyangzhizhou
"""

from itertools import chain
import os
from sklearn.metrics import classification_report
from sklearn.preprocessing import LabelBinarizer
import pycrfsuite
import sys

model_path = '../model/crf.model'

train_folder = 'D:\jcy_data/train'
test_floder = 'D:\jcy_data/test'
valid_floder = 'D:\jcy_data/valid'

posts = []


def read_train_data():
    file_list = os.listdir(train_folder)
    for name in file_list:
        f = open(train_folder + '/' + name, 'r', encoding='utf-8', errors='ignore')
        sentences = []
        sentence = []
        while 1:
            line = f.readline()
            if not line:
                break
            if line != '\n':
                w_l = line.split('\t')
                sentence.append((w_l[0], w_l[1].strip()))
            else:
                sentences.append(sentence)
                sentence = []
        posts.append(sentences)
        f.close()


def read_valid_data():
    file_list = os.listdir(valid_floder)
    for name in file_list:
        f = open(valid_floder + '/' + name, 'r', encoding='utf-8', errors='ignore')
        sentences = []
        sentence = []
        while 1:
            line = f.readline()
            if not line:
                break
            if line != '\n':
                w_l = line.split('\t')
                sentence.append((w_l[0], w_l[1].strip()))
            else:
                sentences.append(sentence)
                sentence = []
        posts.append(sentences)
        f.close()


def read_test_data():
    file_list = os.listdir(test_floder)
    for name in file_list:
        f = open(test_floder + '/' + name, 'r', encoding='utf-8', errors='ignore')
        sentences = []
        while 1:
            line = f.readline()
            sentences.append(line)
            if not line:
                break
        posts.append(sentences)
        f.close()


def word2features(sent, i):
    word = sent[i][0]
    features = [
        'bias',
        'word.lower=' + word.lower(),
        'word[-3:]=' + word[-3:],
        'word[-2:]=' + word[-2:],
        'word.isupper=%s' % word.isupper(),
        'word.istitle=%s' % word.istitle(),
        'word.isdigit=%s' % word.isdigit()
    ]
    if i > 0:
        word1 = sent[i - 1][0]
        features.extend([
            '-1:word.lower=' + word1.lower(),
            '-1:word.istitle=%s' % word1.istitle(),
            '-1:word.isupper=%s' % word1.isupper(),
        ])
    else:
        features.append('BOS')
    if i < len(sent) - 1:
        word1 = sent[i + 1][0]
        features.extend([
            '+1:word.lower=' + word1.lower(),
            '+1:word.istitle=%s' % word1.istitle(),
            '+1:word.isupper=%s' % word1.isupper(),
        ])
    else:
        features.append('EOS')
    return features


def sent2features(sent):
    return [word2features(sent, i) for i in range(len(sent))]


def sent2labels(sent):
    return [label for token, label in sent]


def sent2tokens(sent):
    return [token for token, label in sent]


def train():
    read_train_data()
    sentences = []
    for post in posts:
        for s in post:
            sentences.append(s)
    X_train = [sent2features(s) for s in sentences]
    y_train = [sent2labels(s) for s in sentences]

    trainer = pycrfsuite.Trainer(verbose=False)
    trainer.set_params({
        'c1': 1.0,  # coefficient for L1 penalty
        'c2': 1e-3,  # coefficient for L2 penalty
        'max_iterations': 50,  # stop earlier
        # include transitions that are possible, but not observed
        'feature.possible_transitions': True
    })

    print(trainer.params())

    for xseq, yseq in zip(X_train, y_train):
        trainer.append(xseq, yseq)

    trainer.train(model_path)
    print(trainer.logparser.last_iteration)


def predict():
    read_test_data()
    sentences = []
    for post in posts:
        for s in post:
            sentences.append(list(s.strip()))
    tagger = pycrfsuite.Tagger()
    tagger.open(model_path)
    for text in sentences:
        # print(' '.join(sent2tokens(example_sent)), end='\n\n')
        print("Predicted:", ' '.join(tagger.tag(sent2features(text))))
        # print("Correct:  ", ' '.join(sent2labels(example_sent)))


def predict(text, _path=model_path):
    tagger = pycrfsuite.Tagger()
    tagger.open(_path)
    print(' '.join(tagger.tag(sent2features(text))))


def bio_classification_report(y_true, y_pred):
    lb = LabelBinarizer()
    y_true_combined = lb.fit_transform(list(chain.from_iterable(y_true)))
    y_pred_combined = lb.transform(list(chain.from_iterable(y_pred)))

    tagset = set(lb.classes_) - {'O'}
    tagset = sorted(tagset, key=lambda tag: tag.split('-', 1)[::-1])
    class_indices = {cls: idx for idx, cls in enumerate(lb.classes_)}

    return classification_report(
        y_true_combined,
        y_pred_combined,
        labels=[class_indices[cls] for cls in tagset],
        target_names=tagset,
    )


def evaluate():
    read_valid_data()
    sentences = []
    for post in posts:
        for s in post:
            sentences.append(s)
    tagger = pycrfsuite.Tagger()
    tagger.open(model_path)
    X_test = [sent2features(s) for s in sentences]
    y_test = [sent2labels(s) for s in sentences]
    y_pred = [tagger.tag(xseq) for xseq in X_test]
    print(bio_classification_report(y_test, y_pred))


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print('sys arg Error')
        raise RuntimeError('sys arg Error')
    if sys.argv[1] == 'predict':
        predict(sys.argv[2],sys.argv[3])

        # train()
        # evaluate()
