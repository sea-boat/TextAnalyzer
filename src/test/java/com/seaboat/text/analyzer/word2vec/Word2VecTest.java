package com.seaboat.text.analyzer.word2vec;

import java.util.List;

import org.junit.Test;

import com.seaboat.text.analyzer.util.Segment;

/**
 * 
 * @author seaboat
 * @date 2017-12-02
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word2vec tester.</p>
 */
public class Word2VecTest {

	@Test
	public void test() {

		Word2Vec vec = Word2Vec.getInstance(false);
		System.out.println("狗|猫: " + vec.wordSimilarity("狗", "猫"));
		System.out.println("电脑|计算机: " + vec.wordSimilarity("电脑", "计算机"));
		System.out.println("中国|美国: " + vec.wordSimilarity("中国", "美国"));
		String s1 = "我们是中国人";
		String s2 = "他们是日本人，四贵子";
		List list1 = Segment.getWords(s1);
		List list2 = Segment.getWords(s2);
		System.out.println(vec.sentenceSimilarity(list1, list2));
		s1 = "我们是中国人";
		s2 = "我们是中国人";
		list1 = Segment.getWords(s1);
		list2 = Segment.getWords(s2);
		System.out.println(vec.sentenceSimilarity(list1, list2));
		s1 = "电脑多少钱";
		s2 = "计算机多少钱";
		list1 = Segment.getWords(s1);
		list2 = Segment.getWords(s2);
		System.out.println(vec.sentenceSimilarity(list1, list2));
		s1 = "手机能修好吗";
		s2 = "计算机多少钱";
		list1 = Segment.getWords(s1);
		list2 = Segment.getWords(s2);
		System.out.println(vec.sentenceSimilarity(list1, list2));
		s1 = "请问套餐1多少钱";
		s2 = "有什么好套餐推荐吗";
		list1 = Segment.getWords(s1);
		list2 = Segment.getWords(s2);
		System.out.println(vec.sentenceSimilarity(list1, list2));
	}
}
