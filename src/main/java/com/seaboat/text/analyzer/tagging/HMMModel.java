package com.seaboat.text.analyzer.tagging;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2017-09-22
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a hmm model which is for part of speech. it provides training method.</p>
 */
public class HMMModel implements Serializable {
	private static final long serialVersionUID = -8307826997830463783L;
	private String[] text;
	private String[] phrase;
	private String[] pos;
	private List<String> contents = new ArrayList<String>();
	private List<String[]> phrasesInContents = new ArrayList<String[]>();
	private List<String[]> charactersInContents = new ArrayList<String[]>();
	Hashtable<String, Integer> posHash = new Hashtable<String, Integer>();
	Hashtable<String, Integer> phraseHash = new Hashtable<String, Integer>();
	Hashtable<String, Integer> posTransformFreqHash = new Hashtable<String, Integer>();
	Hashtable<String, Integer> phraseAndPOSFreqHash = new Hashtable<String, Integer>();
	int posNum = 0;
	int phraseNum = 0;
	String[] diffPOS;
	String[] diffPhras;
	Hashtable<String, Integer> phrasePosition = new Hashtable<String, Integer>();
	double[] prioriProbability;
	double[][] posTransformProbability;
	double[][] phraseInPOSProbability;
	private String file = "./data/corpus_data/train.txt";

	public HMMModel() {

	}

	public HMMModel(String file) {
		this.file = file;
	}

	public void train() {
		readCorpus(file);
		initPOS();
		initPhrase();
		initPOSTransformFreq();
		initPOSPrioriProbability();
		initPOSTransformProbability();
		initPhraseAndPOSFreq();
		initPhraseInPOSProbability();
	}

	public void readCorpus(String fileName) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null)
				contents.add(line);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					System.out.println("close file error");
				}
			}
		}
		String temp = "";
		for (String content : contents) {
			content = content.replaceAll("\\[", "");
			content = content.replaceAll("\\]/nz", "");
			content = content.replaceAll("\\]/mq", "");
			content = content.replaceAll("\\]/nt", "");
			String[] terms = content.split("(/[a-z]*\\s{0,})");

			phrasesInContents.add(terms);
			terms = content.split("\\s{1,}[^a-z]*");
			terms[0] = terms[0].substring(terms[0].indexOf("/") + 1);
			charactersInContents.add(terms);
			temp += content;
		}
		text = temp.split("\\s{1,}");
		phrase = temp.split("(/[a-z]*\\s{0,})");
		List<String> tempList = new ArrayList<String>();
		for (String[] character : charactersInContents)
			for (String c : character)
				tempList.add(c);
		pos = new String[tempList.size()];
		tempList.toArray(pos);
	}

	public void initPOS() {
		for (int i = 1; i < pos.length; i++) {
			if (posHash.containsKey(pos[i])) {
				posHash.put(pos[i], posHash.get(pos[i]) + 1);
			} else {
				posHash.put(pos[i], 1);
			}
		}
		posNum = posHash.size();
		diffPOS = new String[posNum];
		Enumeration<String> key = posHash.keys();
		for (int i = 0; i < posHash.size(); i++) {
			diffPOS[i] = (String) key.nextElement();
		}
	}

	public void initPhrase() {
		for (int i = 0; i < phrase.length; i++) {
			if (phraseHash.containsKey(phrase[i])) {
				phraseHash.put(phrase[i], phraseHash.get(phrase[i]) + 1);
			} else {
				phraseHash.put(phrase[i], 1);
			}
		}
		phraseNum = phraseHash.size();
		diffPhras = new String[phraseNum];
		Enumeration<String> key = phraseHash.keys();
		for (int i = 0; i < phraseHash.size(); i++) {
			String str = (String) key.nextElement();
			diffPhras[i] = str;
			phrasePosition.put(str, i);
		}
	}

	public void initPOSTransformFreq() {
		for (int i = 0; i < pos.length - 1; i++) {
			String temp = pos[i] + "," + pos[i + 1];
			if (posTransformFreqHash.containsKey(temp)) {
				posTransformFreqHash.put(temp, posTransformFreqHash.get(temp) + 1);
			} else {
				posTransformFreqHash.put(temp, 1);
			}
		}
	}

	public void initPhraseAndPOSFreq() {
		for (int i = 0; i < text.length; i++) {
			if (phraseAndPOSFreqHash.containsKey(text[i])) {
				phraseAndPOSFreqHash.put(text[i], phraseAndPOSFreqHash.get(text[i]) + 1);
			} else {
				phraseAndPOSFreqHash.put(text[i], 1);
			}
		}
	}

	public void initPOSPrioriProbability() {
		prioriProbability = new double[posNum];
		int allPOSCount = 0;
		for (int i = 0; i < posNum; i++) {
			allPOSCount += posHash.get(diffPOS[i]);
		}
		for (int i = 0; i < posNum; i++) {
			prioriProbability[i] = posHash.get(diffPOS[i]) * 1.0 / allPOSCount;
		}
	}

	public void initPOSTransformProbability() {
		posTransformProbability = new double[posNum][posNum];
		for (int i = 0; i < posNum; i++) {
			for (int j = 0; j < posNum; j++) {
				String front = diffPOS[i];
				String last = diffPOS[j];
				if (posTransformFreqHash.containsKey(front + "," + last)) {
					int numerator = posTransformFreqHash.get(front + "," + last);
					int denominator = posHash.get(front);
					posTransformProbability[i][j] = (double) numerator / denominator;
				}
			}
		}
	}

	public void initPhraseInPOSProbability() {
		phraseInPOSProbability = new double[phraseNum][posNum];
		for (int i = 0; i < phraseNum; i++) {
			String phras = diffPhras[i];
			int total = 0;
			for (int j = 0; j < posNum; j++) {
				String chars = diffPOS[j];
				if (phraseAndPOSFreqHash.containsKey(phras + "/" + chars)) {
					int num = phraseAndPOSFreqHash.get(phras + "/" + chars);
					total += num;
				}
			}
			for (int j = 0; j < posNum; j++) {
				String chars = diffPOS[j];
				if (phraseAndPOSFreqHash.containsKey(phras + "/" + chars)) {
					int numerator = phraseAndPOSFreqHash.get(phras + "/" + chars);
					phraseInPOSProbability[i][j] = (double) numerator / total;
				} else {
					phraseInPOSProbability[i][j] = 0.00001;// avoid zero multiply
				}
			}
		}
	}
}
