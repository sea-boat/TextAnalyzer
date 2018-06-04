package com.seaboat.text.analyzer.cilin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 
 * @author seaboat
 * @date 2018-06-04
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>cilin dictionary.</p>
 */
public class CilinDictionary {

	protected static Logger logger = Logger.getLogger(CilinDictionary.class);
	private static Map<String, Set<String>> wordIndex = new HashMap<String, Set<String>>();
	private static Map<String, Set<String>> codeIndex = new HashMap<String, Set<String>>();
	private static CilinDictionary instance;
	private static String path = "/cilin.txt";

	private CilinDictionary() {
		try {
			InputStreamReader read = new InputStreamReader(this.getClass().getResourceAsStream(path), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] items = line.split(" ");
				Set<String> set = new HashSet<String>();
				for (int i = 1; i < items.length; i++) {
					String code = items[i].trim();
					if (!code.equals("")) {
						set.add(code);
						Set<String> codeWords = codeIndex.get(code);
						if (codeWords == null) {
							codeWords = new HashSet<String>();
						}
						codeWords.add(items[0]);
						codeIndex.put(code, codeWords);
					}
				}
				wordIndex.put(items[0], set);
			}
		} catch (Exception e) {
			logger.error("error occurs when loading cilin ....", e);
		}
	}

	public Set<String> getCilinCoding(String word) {
		return codeIndex.get(word);
	}

	public Set<String> getCilinWords(String code) {
		return wordIndex.get(code);
	}

	public static CilinDictionary getInstance() {
		if (instance == null) {
			synchronized (CilinDictionary.class) {
				instance = new CilinDictionary();
			}
		}
		return instance;
	}
}
