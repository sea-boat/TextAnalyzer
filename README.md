# TextAnalyzer

A text analyzer which is based on machine learning that can analyze text.

So far, it supports hot word extracting, text classification, part of speech tagging, named entity recognition, chinese word segment, extracting address, synonym, text clustering, word2vec model, edit distance, chinese word segment, sentence similarity, extracting name, extracting ID, word sentiment tendency. 

# Features

***extracting hot words from text.***
1. to gather statistics via frequence.
2. to gather statistics via by tf-idf algorithm
3. to gather statistics via a score factor additionally.

***extracting address from text.***

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

***google word2vec model***

This analyzer supports to use word2vec model.

***chinese word segment***

This analyzer supports to do chinese word segment.

***edit distance***

This analyzer supports calculating edit distance on char level or word level.

***sentence similarity***

This analyzer supports calculating similarity between two sentences.


# How To Use

***just simple like this***

## Extracting Hot Words

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

## Extracting Address

```
String str ="xxxx";
AddressExtractor extractor = new AddressExtractor();
List<String> list = extractor.extract(str);
```

## SVM Classificator

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

## Kmeans Clustering && Xmeans Clustering

```
List<String> list = DataReader.readContent(KMeansCluster.DATA_FILE);
int[] labels = new KMeansCluster().learn(list);
```

## VSM Clustering

```
List<String> list = DataReader.readContent(VSMCluster.DATA_FILE);
List<String> labels = new VSMCluster().learn(list);
```

## Part Of Speech Tagging
```
HMMModel model = new HMMModel();
model.train();
ViterbiDecoder decoder = new ViterbiDecoder(model);
decoder.decode(words);
```

## Define Your Own Named Entity

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


## Word2vec
we must set the word2vec's path system parameter when startup,just like this `-Dword2vec.path=D:\Google_word2vec_zhwiki1710_300d.bin`.

using google model.

```
Word2Vec vec = Word2Vec.getInstance(true);
System.out.println("狗|猫: " + vec.wordSimilarity("狗", "猫"));
```

using java model

```
Word2Vec vec = Word2Vec.getInstance(false);
System.out.println("狗|猫: " + vec.wordSimilarity("狗", "猫"));
```


## Segment
```
DictSegment segment = new DictSegment();
System.out.println(segment.seg("我是中国人"));

```

## Edit Distance
char level,

```
CharEditDistance cdd = new CharEditDistance();
cdd.getEditDistance("what", "where");
cdd.getEditDistance("我们是中国人", "他们是日本人吖，四贵子");
cdd.getEditDistance("是我", "我是");
```

word level,

```
List list1 = new ArrayList<String>();
list1.add(new EditBlock("计算机",""));
list1.add(new EditBlock("多少",""));
list1.add(new EditBlock("钱",""));
List list2 = new ArrayList<String>();
list2.add(new EditBlock("电脑",""));
list2.add(new EditBlock("多少",""));
list2.add(new EditBlock("钱",""));
ed.getEditDistance(list1, list2);
```

## Sentence Similarity

```
String s1 = "我们是中国人";
String s2 = "他们是日本人，四贵子";
SentenceSimilarity ss = new SentenceSimilarity();
System.out.println(ss.getSimilarity(s1, s2));
s1 = "我们是中国人";
s2 = "我们是中国人";
System.out.println(ss.getSimilarity(s1, s2));
```

## Get Synonym via Cilin Dictionary

```
CilinDictionary dict = CilinDictionary.getInstance();
Set<String> code = dict.getCilinCoding("人类");
System.out.println(dict.getCilinWords(code.iterator().next()));
[全人类, 生人, 人类]
```

## Words' Similarity by Cilin
```
String s1 = "中国人";
String s2 = "炎黄子孙";
CilinSimilarity cs = new CilinSimilarity();
System.out.println(cs.getSimilarity(s1, s2));
s1 = "汽车";
s2 = "摩托";
System.out.println(cs.getSimilarity(s1, s2));
```

## Get Hownet Glossary
```
HownetGlossary glossary = HownetGlossary.getInstance();
Collection<Term> coll = glossary.getTerms("人类");
for (Term t : coll)
  System.out.println(t);
```

## Get Hownet Sememe
```
HownetSememe sememe = HownetSememe.getInstance();
Collection<String> coll = sememe.getDefine("用具");
for (String t : coll)
  System.out.println(t);
```

## Hownet Words Similarity
```
HownetSimilarity hownetSimilarity = new HownetSimilarity();
System.out.println("hownet similarity : " + hownetSimilarity.getSimilarity("中国", "美国"));
```

## Get Pinyin 
```
System.out.println(PinyinUtil.getInstance().getPinyin("哈哈"));
System.out.println(PinyinUtil.getInstance().getPinyin("中"));
System.out.println(PinyinUtil.getInstance().getPinyin("中国"));
```

## Pinyin Similarity
```
String s1 = "今天";
String s2 = "明天";
PinyinSimilarity cs = new PinyinSimilarity();
System.out.println(cs.getSimilarity(s1, s2));
```

## Information Extractor
### usage
We have provided Python and Java APIs for extractor,choose one of them.

### python
do a predict by this below,
```
python crf_ner.py predict "测试文本" "../model/crf.model"
```

### java 

```
List list = JCYExtractor.getIDs(text);

list = JCYExtractor.getNames(text);

JCYExtractor.getAddrs(text);
```

### train a model 
1. To collect corpus.
2. Tagging corpus,we support those labels below,

```
# IB : ID beginning
# IE : ID ending
# IM : ID middle
# U : unlabeled
# PB : person beginning
# PE : person ending
# PM : person middle
# BB : birthday beginning
# BM : birthday middle
# BE : birthday ending
# LB : location beginning
# LM : location middle
# LE : location endings
```

for example,

```
被	U
不	U
起	U
诉	U
人	U
伍	PB
某	PM
某	PE
，	U
```

3. Put all samples to the directory of `data/jcy_data/train`.
4. Call `train` function in the `crf_ner.py` script，the model will produce in the directory of `model` which name is `crf.model`.


## Word Tendency

```
WordSentimentTendency tendency = new WordSentimentTendency();
System.out.println(tendency.getTendency("高兴"));
System.out.println(tendency.getTendency("伤心"));
```
