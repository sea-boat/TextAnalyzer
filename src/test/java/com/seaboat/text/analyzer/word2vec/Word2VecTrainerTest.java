package com.seaboat.text.analyzer.word2vec;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-06-11
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word2vec trainer tester.</p>
 */
public class Word2VecTrainerTest {

	@Test
	public void test() {
		Word2VecTrainer trainer = new Word2VecTrainer();
		long start = System.currentTimeMillis();
		try {
			trainer.learnFile(new File("data/word2vec.txt"));
			System.out.println("use time " + (System.currentTimeMillis() - start));
			trainer.saveModel(new File("model/word2vec.model"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
