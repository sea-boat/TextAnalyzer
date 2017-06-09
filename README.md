# TextAnalyzer

a text analizer that can analyze text. so far, it can extract hot words in a text segment by using tf-idf algorithm,at the same time using a score factor to optimize the final score.

also it provides machine learning to make a classification.

# features

***extracting hot words from a text.***
1. to gather statistics via frequence.
2. to gather statistics via by tf-idf algorithm
3. to gather statistics via a score factor additionally.

***synonym can be recognized***

***SVM Classificator***

this analyzer supports to classify text by svm. it involves vectoring the text. we can train the samples and then make a classification by the model.

for convenience,the model,tfidf and vector will be stored.

***kmeans clustering***

this analyzer supports to clustering text by kmeans.


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