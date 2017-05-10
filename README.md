# TextAnalyzer
a text analizer that can analyze text. so far, it can extract hot words in a text segment by using tf-idf algorithm.

# Dependence

https://github.com/sea-boat/IKAnalyzer-Mirror.git

# TODO
optimize hot-word extract by tf-idf.


# how to use 

***just simple like this***

```
 HotWordExtractor extractor = new HotWordExtractor();
 List<Result> list = extractor.extract(0, 20, false);
 if (list != null) for (Result s : list)
    System.out.println(s.getTerm() + " : " + s.getFrequency() + " : " + s.getScore());
```

a result contains term,frequency and score.

```
一带一路 : 15 : 0.10496296
法律服务 : 12 : 0.08397037
北京 : 9 : 0.062977776
涉外 : 8 : 0.055980247
联合 : 8 : 0.055980247
法律 : 7 : 0.048982713
合作 : 6 : 0.041985184
大学 : 6 : 0.041985184
法治 : 5 : 0.034987655
相关 : 5 : 0.034987655
论坛 : 5 : 0.034987655
中国 : 4 : 0.027990123
研究 : 4 : 0.027990123
企业 : 3 : 0.020992592
千龙网 : 3 : 0.020992592
实践 : 3 : 0.020992592
法 : 3 : 0.020992592
研究中心 : 3 : 0.020992592
首都 : 3 : 0.020992592
5月 : 2 : 0.013995062
```