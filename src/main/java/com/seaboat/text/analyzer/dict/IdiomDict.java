package com.seaboat.text.analyzer.dict;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.data.structure.ACTrieTree;
import com.seaboat.text.analyzer.segment.DictSegment;

/**
 * 
 * @author seaboat
 * @date 2018-08-01
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>core words dictionary.</p>
 */
public class IdiomDict implements Dict {

	private static Logger logger = Logger.getLogger(IdiomDict.class);

	private static ACTrieTree tree = null;
	private static String DIC_FILE = "/idiom.dic";
	public static String[] dictionary = null;
	private static IdiomDict instance;

	private IdiomDict() {
		tree = new ACTrieTree();
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		List<String> words = loadDict(DIC_FILE);
		try {
			tree.build(words);
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("loading idiom dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
	}

	public IdiomDict(List<String> words) {
		tree = new ACTrieTree();
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		initDict(words);
		try {
			tree.build(words);
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("loading idiom dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
	}

	private void initDict(List<String> words) {
		Collections.sort(words);
		dictionary = new String[words.size()];
		for (int i = 0; i < dictionary.length; i++)
			dictionary[i] = words.get(i);
	}

	public static IdiomDict get() {
		if (instance != null)
			return instance;
		synchronized (IdiomDict.class) {
			if (instance == null)
				instance = new IdiomDict();
			return instance;
		}
	}

	public List<String> loadDict(String path) {
		List<String> words = null;
		InputStreamReader read;
		try {
			read = new InputStreamReader(DictSegment.class.getResourceAsStream(path), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			String line;
			line = bufferedReader.readLine();
			words = new ArrayList<String>(Integer.parseInt(line));
			while ((line = bufferedReader.readLine()) != null) {
				words.add(line);
			}
			Collections.sort(words);
			dictionary = new String[words.size()];
			for (int i = 0; i < dictionary.length; i++)
				dictionary[i] = words.get(i);
		} catch (FileNotFoundException e) {
			logger.error("File not found", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return words;
	}
	
	public List<String> searchIdiom(String s) {
		return tree.acSearch(s);
	}

	public List<Integer> prefixSearch(String text) {
		return null;
	}

	public String getStringByIndex(Integer i) {
		return null;
	}

}
