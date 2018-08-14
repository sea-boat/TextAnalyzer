package com.seaboat.text.analyzer.dict;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.data.structure.DoubleArrayTrie;
import com.seaboat.text.analyzer.segment.DictSegment;

/**
 * 
 * @author seaboat
 * @date 2018-08-14
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>pinyin dictionary.</p>
 */
public class PinyinDict implements Dict {

	private static Logger logger = Logger.getLogger(PinyinDict.class);

	private static DoubleArrayTrie tree = null;
	private static String DIC_FILE = "/pinyin.dic";
	public static String[] dictionary = null;
	private static PinyinDict instance;

	private PinyinDict() {
		tree = new DoubleArrayTrie();
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		List<String> words = loadDict(DIC_FILE);
		try {
			tree.build(words);
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("loading dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
	}

	public PinyinDict(List<String> words) {
		tree = new DoubleArrayTrie();
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		initDict(words);
		try {
			tree.build(words);
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("loading dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
	}

	private void initDict(List<String> words) {
		Collections.sort(words);
		dictionary = new String[words.size()];
		for (int i = 0; i < dictionary.length; i++)
			dictionary[i] = words.get(i);
	}

	public static PinyinDict get() {
		if (instance != null)
			return instance;
		synchronized (PinyinDict.class) {
			if (instance == null)
				instance = new PinyinDict();
			return instance;
		}
	}

	public List<String> loadDict(String path) {
		List<String> words = null;
		List<KV> kvs = null;
		InputStreamReader read;
		try {
			read = new InputStreamReader(DictSegment.class.getResourceAsStream(path), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			String line;
			line = bufferedReader.readLine();
			words = new ArrayList<String>(Integer.parseInt(line));
			kvs = new ArrayList<KV>(Integer.parseInt(line));
			while ((line = bufferedReader.readLine()) != null) {
				String[] ss = line.split("=");
				if (ss.length == 2) {
					kvs.add(new KV(ss[0], ss[1]));
				}
			}
			Collections.sort(kvs, new Comparator<KV>() {
				@Override
				public int compare(KV o1, KV o2) {
					return o1.k.compareTo(o2.k);
				}
			});
			dictionary = new String[kvs.size()];
			for (int i = 0; i < dictionary.length; i++)
				dictionary[i] = kvs.get(i).v;
			for (KV kv : kvs)
				words.add(kv.k);
		} catch (FileNotFoundException e) {
			logger.error("File not found", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return words;
	}

	public List<Integer> prefixSearch(String text) {
		return tree.commonPrefixSearch(text);
	}

	public int exactlySearch(String text) {
		return tree.exactMatchSearch(text);
	}

	public String getStringByIndex(Integer i) {
		return dictionary[i];
	}

	class KV {
		public KV(String k, String v) {
			this.k = k;
			this.v = v;
		}

		String k;
		String v;
	}
}
