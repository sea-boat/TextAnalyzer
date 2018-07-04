package com.seaboat.text.analyzer.word2vec;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author seaboat
 * @date 2017-12-02
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>word2vec implement.</p>
 */
public class Word2Vec {
	static {
		String _path = System.getProperty("word2vec.path");
		if (_path != null)
			path = _path;
		else
			throw new RuntimeException("word2vec path is not set");
	}

	private HashMap<String, float[]> wordMap = new HashMap<String, float[]>();
	private static final int MAX_SIZE = 50;
	private int words;
	private int size;
	private int topNSize = 40;
	private static Word2Vec instance = null;
	private static String path;
	private boolean isGoogleModel = true;

	private Word2Vec() {
	}

	private void init() {
		try {
			if (isGoogleModel)
				instance.loadGoogleModel(path);
			else
				instance.loadJavaModel(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Word2Vec getInstance(boolean isGoogleModel) {
		if (instance == null)
			synchronized (Word2Vec.class) {
				if (instance != null)
					return instance;
				instance = new Word2Vec();
				instance.isGoogleModel = isGoogleModel;
				instance.init();
			}
		return instance;
	}

	public void loadJavaModel(String path) throws IOException {
		try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
			words = dis.readInt();
			size = dis.readInt();
			float vector = 0;
			String key = null;
			float[] value = null;
			for (int i = 0; i < words; i++) {
				double len = 0;
				key = dis.readUTF();
				value = new float[size];
				for (int j = 0; j < size; j++) {
					vector = dis.readFloat();
					len += vector * vector;
					value[j] = vector;
				}

				len = Math.sqrt(len);
				for (int j = 0; j < size; j++) {
					value[j] /= len;
				}
				wordMap.put(key, value);
			}

		}
	}

	public void loadGoogleModel(String path) throws IOException {
		DataInputStream dis = null;
		BufferedInputStream bis = null;
		double len = 0;
		float vector = 0;
		try {
			bis = new BufferedInputStream(new FileInputStream(path));
			dis = new DataInputStream(bis);
			words = Integer.parseInt(readString(dis));
			size = Integer.parseInt(readString(dis));
			String word;
			float[] vectors = null;
			for (int i = 0; i < words; i++) {
				word = readString(dis);
				vectors = new float[size];
				len = 0;
				for (int j = 0; j < size; j++) {
					vector = readFloat(dis);
					len += vector * vector;
					vectors[j] = (float) vector;
				}
				len = Math.sqrt(len);
				for (int j = 0; j < size; j++) {
					vectors[j] /= len;
				}
				wordMap.put(word, vectors);
				dis.read();
			}
		} finally {
			bis.close();
			dis.close();
		}
	}

	public float[] getWordVector(String word) {
		return wordMap.get(word);
	}

	private float calDist(float[] vec1, float[] vec2) {
		float dist = 0;
		for (int i = 0; i < vec1.length; i++) {
			dist += vec1[i] * vec2[i];
		}
		return dist;
	}

	public float wordSimilarity(String word1, String word2) {
		float[] word1Vec = getWordVector(word1);
		float[] word2Vec = getWordVector(word2);
		if (word1Vec == null || word2Vec == null) {
			return 0;
		}
		return calDist(word1Vec, word2Vec);
	}

	public Set<WordEntry> getSimilarWords(String word, int maxReturnNum) {
		float[] center = getWordVector(word);
		if (center == null) {
			return Collections.emptySet();
		}
		int resultSize = getWords() < maxReturnNum ? getWords() : maxReturnNum;
		TreeSet<WordEntry> result = new TreeSet<WordEntry>();
		double min = Double.MIN_VALUE;
		for (Map.Entry<String, float[]> entry : getWordMap().entrySet()) {
			float[] vector = entry.getValue();
			float dist = calDist(center, vector);
			if (result.size() <= resultSize) {
				result.add(new WordEntry(entry.getKey(), dist));
				min = result.last().score;
			} else {
				if (dist > min) {
					result.add(new WordEntry(entry.getKey(), dist));
					result.pollLast();
					min = result.last().score;
				}
			}
		}
		result.pollFirst();
		return result;
	}

	private float calMaxSimilarity(String centerWord, List<String> wordList) {
		float max = -1;
		if (wordList.contains(centerWord)) {
			return 1;
		} else {
			for (String word : wordList) {
				float temp = wordSimilarity(centerWord, word);
				if (temp == 0)
					continue;
				if (temp > max) {
					max = temp;
				}
			}
		}
		if (max == -1)
			return 0;
		return max;
	}

	public float sentenceSimilarity(List<String> sentence1Words, List<String> sentence2Words) {
		if (sentence1Words.isEmpty() || sentence2Words.isEmpty()) {
			return 0;
		}
		float[] vector1 = new float[sentence1Words.size()];
		float[] vector2 = new float[sentence2Words.size()];
		for (int i = 0; i < vector1.length; i++) {
			vector1[i] = calMaxSimilarity(sentence1Words.get(i), sentence2Words);
		}
		for (int i = 0; i < vector2.length; i++) {
			vector2[i] = calMaxSimilarity(sentence2Words.get(i), sentence1Words);
		}
		float sum1 = 0;
		for (int i = 0; i < vector1.length; i++) {
			sum1 += vector1[i];
		}
		float sum2 = 0;
		for (int i = 0; i < vector2.length; i++) {
			sum2 += vector2[i];
		}
		return (sum1 + sum2) / (sentence1Words.size() + sentence2Words.size());
	}

	private static String readString(DataInputStream dis) throws IOException {
		byte[] bytes = new byte[MAX_SIZE];
		byte b = dis.readByte();
		int i = -1;
		StringBuilder sb = new StringBuilder();
		while (b != 32 && b != 10) {
			i++;
			bytes[i] = b;
			b = dis.readByte();
			if (i == 49) {
				sb.append(new String(bytes));
				i = -1;
				bytes = new byte[MAX_SIZE];
			}
		}
		sb.append(new String(bytes, 0, i + 1));
		return sb.toString();
	}

	public static float readFloat(InputStream is) throws IOException {
		byte[] bytes = new byte[4];
		is.read(bytes);
		return getFloat(bytes);
	}

	public static float getFloat(byte[] b) {
		int accum = 0;
		accum = accum | (b[0] & 0xff) << 0;
		accum = accum | (b[1] & 0xff) << 8;
		accum = accum | (b[2] & 0xff) << 16;
		accum = accum | (b[3] & 0xff) << 24;
		return Float.intBitsToFloat(accum);
	}

	public int getTopNSize() {
		return topNSize;
	}

	public void setTopNSize(int topNSize) {
		this.topNSize = topNSize;
	}

	public HashMap<String, float[]> getWordMap() {
		return wordMap;
	}

	public int getWords() {
		return words;
	}

	public int getSize() {
		return size;
	}

}
