package com.seaboat.text.analyzer.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author seaboat
 * @date 2018-06-10
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a util for pinyin.</p>
 */
public class PinyinUtil {
	private static Map<Character, Set<String>> pinyinDict = new HashMap<Character, Set<String>>();

	private static PinyinUtil instance = null;

	private PinyinUtil() throws IOException {
		InputStream input = this.getClass().getResourceAsStream("/pinyin.txt");
		BufferedReader in = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		String line = null;
		while ((line = in.readLine()) != null) {
			char hanzi = line.charAt(0);
			String pinyin = line.substring(2, line.length());
			Set<String> set = pinyinDict.get(hanzi);
			if (set == null) {
				set = new HashSet<String>();
			}
			set.add(pinyin);

			pinyinDict.put(hanzi, set);
		}
		input.close();
		in.close();
	}

	public static PinyinUtil getInstance() {
		if (instance == null) {
			synchronized (PinyinUtil.class) {
				if (instance == null)
					try {
						instance = new PinyinUtil();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}

		return instance;
	}

	public Set<String> getPinyin(Character hanzi) {
		Set<String> set = pinyinDict.get(hanzi);
		if (set == null || set.size() == 0) {
			set = new HashSet<String>();
			set.add(hanzi.toString());
		}
		return set;
	}

	public Set<String> getPinyin(String word) {
		Set<String> word_set = new HashSet<String>();
		for (int i = 0; i < word.length(); i++) {
			Set<String> hanzi_set = getPinyin(word.charAt(i));
			if (word_set == null || word_set.size() == 0) {
				word_set.addAll(hanzi_set);
				continue;
			}

			Set<String> tmp_set = new HashSet<String>();
			for (String w : word_set) {
				for (String h : hanzi_set) {
					tmp_set.add(w + h);
				}
			}

			word_set = tmp_set;
		}

		return word_set;
	}

	public String getPinyinSingle(String word) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < word.length(); i++) {
			sb.append(getPinyin(word.charAt(i)).iterator().next());
		}
		return sb.toString();
	}

	public String getPinyinString(String word) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < word.length(); i++) {
			Set<String> pinyin = getPinyin(word.charAt(i));
			sb.append(pinyin.toString());
		}
		return sb.toString();
	}

	public String getPinyinHead(String word) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < word.length(); i++) {
			sb.append(getPinyin(word.charAt(i)).iterator().next().charAt(0));
		}
		return sb.toString();
	}

	private static class MyTraverseEvent {
		/** 一个汉字对应多个拼音, 多个拼音放到集合中 */
		private Map<Character, Set<String>> pinyins = null;

		public MyTraverseEvent() {
			this.pinyins = new HashMap<Character, Set<String>>();
		}

		public Map<Character, Set<String>> getPinyins() {
			return pinyins;
		}

		public boolean visit(String item) {
			if (item.startsWith("//")) {
				return true;
			}

			return true;
		}
	}

}
