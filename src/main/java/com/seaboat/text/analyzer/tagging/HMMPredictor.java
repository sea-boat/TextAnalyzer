package com.seaboat.text.analyzer.tagging;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import com.seaboat.text.analyzer.util.StringUtil;

/**
 * 
 * @author seaboat
 * @date 2018-05-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a hmm model predictor.</p>
 */
public class HMMPredictor {

	private static String path = "./model/hmm/hmm.model";

	private HMMModel hmm;

	private static HMMPredictor instance = new HMMPredictor();

	private ViterbiDecoder decoder;

	private HMMPredictor() {
		synchronized (HMMPredictor.class) {
			if (hmm == null)
				try {
					ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
					hmm = (HMMModel) in.readObject();
					decoder = new ViterbiDecoder(hmm);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
					throw new RuntimeException("HMM fails to init... ");
				}
		}

	}

	public static HMMPredictor getIntance() {
		return instance;
	}

	public String[] predict(String s) {
		List<Term> termList = ToAnalysis.parse(s).getTerms();
		List<String> wordList = new ArrayList<String>();
		for (Term wordTerm : termList) {
			wordList.add(wordTerm.getName());
		}
		List<String> listF = new ArrayList<String>();
		for (int i = 0; i < wordList.size(); i++) {
			// if (StringUtil.hasDigit(wordList.get(i)))
			// continue;
			if (wordList.get(i).equals("br"))
				continue;
			listF.add(wordList.get(i));
		}
		String[] words = new String[listF.size()];
		listF.toArray(words);
		decoder.decode(words);
		return words;
	}

	public String[] predict(List<String> wordList) {
		List<String> listF = new ArrayList<String>();
		for (int i = 0; i < wordList.size(); i++) {
			if (wordList.get(i).equals("br"))
				continue;
			listF.add(wordList.get(i));
		}
		String[] words = new String[listF.size()];
		listF.toArray(words);
		decoder.decode(words);
		return words;
	}

}
