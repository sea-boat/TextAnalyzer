# TextAnalyzer

A text analizer that can analyze text.

So far, it can extract hot words in a text segment by using tf-idf algorithm, at the same time using a score factor to optimize the final score.

It provides machine learning to make a classification.

Part of speech tagging is also be supported.

A custom named entity is supported by using MITIE. 


### Change Log:

***2017-11-01:***
TextAnalyzer supports custom named entity.

***2017-09-22:***
TextAnalyzer supports part of speech tagging.

***2017-09-19:***
TextAnalyzer supports to extract address.

***2017-06-09:***
TextAnalyzer supports Clusterring(kmeans\xmeans\vsm).

***2017-05-24:***
TextAnalyzer supports SVMTrainer.

***2017-05-09:***
TextAnalyzer supports to extract hotwords.


# features

***extracting hot words from a text.***
1. to gather statistics via frequence.
2. to gather statistics via by tf-idf algorithm
3. to gather statistics via a score factor additionally.

***extracting address from a text.***

***synonym can be recognized***

***SVM Classificator***

This analyzer supports to classify text by svm. it involves vectoring the text. We can train the samples and then make a classification by the model.

For convenience,the model,tfidf and vector will be stored.

***kmeans clustering && xmeans clustering***

This analyzer supports to clustering text by kmeans and xmeans.

***vsm clustering***

This analyzer supports to clustering text by vsm.

***part of speech tagging***
It's implemented by HMM model and decoder by viterbi algorithm.


# Dependence

https://github.com/sea-boat/IKAnalyzer-Mirror.git


# TODO
* other ml algorithms.
* emotion analization.


# how to use

***just simple like this***

## extracting hot words

1. indexing a document and get a docId.

```
long docId = TextIndexer.index(text);
```

2. extracting by docId.

```
 HotWordExtractor extractor = new HotWordExtractor();
 List<Result> list = extractor.extract(0, 20, false);
 if (list != null) for (Result s : list)
    System.out.println(s.getTerm() + " : " + s.getFrequency() + " : " + s.getScore());
```

a result contains term,frequency and score.

```
失业证 : 1 : 0.31436604
户口 : 1 : 0.30099702
单位 : 1 : 0.29152703
提取 : 1 : 0.27927202
领取 : 1 : 0.27581802
职工 : 1 : 0.27381304
劳动 : 1 : 0.27370203
关系 : 1 : 0.27080503
本市 : 1 : 0.27080503
终止 : 1 : 0.27080503
```

## extracting address

```
String str ="xxxx";
AddressExtractor extractor = new AddressExtractor();
List<String> list = extractor.extract(str);
```

## SVM classificator

1. training the samples.

```
SVMTrainer trainer = new SVMTrainer();
trainer.train();
```

2. predicting text classification.

```
double[] data = trainer.getWordVector(text);
trainer.predict(data);
```

## kmeans clustering && xmeans clustering

```
List<String> list = DataReader.readContent(KMeansCluster.DATA_FILE);
int[] labels = new KMeansCluster().learn(list);
```

## vsm clustering

```
List<String> list = DataReader.readContent(VSMCluster.DATA_FILE);
List<String> labels = new VSMCluster().learn(list);
```

## part of speech tagging
```
HMMModel model = new HMMModel();
model.train();
ViterbiDecoder decoder = new ViterbiDecoder(model);
decoder.decode(words);
```

## define your own named entity

MITIE is an information extractor library comes up with MIT NLP term , which github is https://github.com/mit-nlp/MITIE .

***train total\_word\_feature\_extractor***

Prepare your word set, you can put them into a txt file in the directory of 'data'.

And then do things below:

```
git clone https://github.com/mit-nlp/MITIE.git
cd tools
cd wordrep
mkdir build
cd build
cmake ..
cmake --build . --config Release
wordrep -e data
```

Finally you get the total\_word\_feature\_extractor model.


***train ner\_model***

We can use Java\C++\Python to train the ner model, anyway we must use the total\_word\_feature\_extractor model to train it.

if Java,

```
NerTrainer nerTrainer = new NerTrainer("model/mitie_model/total_word_feature_extractor.dat");
```

if C++,

```
ner_trainer trainer("model/mitie_model/total_word_feature_extractor.dat");
```

if Python,

```
trainer = ner_trainer("model/mitie_model/total_word_feature_extractor.dat")
```


***build shared library***

Do commands below:

```
cd mitielib
D:\MITIE\mitielib>mkdir build
D:\MITIE\mitielib>cd build
D:\MITIE\mitielib\build>cmake ..
D:\MITIE\mitielib\build>cmake --build . --config Release --target install
```

Then we get these below:

```
-- Install configuration: "Release"
-- Installing: D:/MITIE/mitielib/java/../javamitie.dll
-- Installing: D:/MITIE/mitielib/java/../javamitie.jar
-- Up-to-date: D:/MITIE/mitielib/java/../msvcp140.dll
-- Up-to-date: D:/MITIE/mitielib/java/../vcruntime140.dll
-- Up-to-date: D:/MITIE/mitielib/java/../concrt140.dll
```