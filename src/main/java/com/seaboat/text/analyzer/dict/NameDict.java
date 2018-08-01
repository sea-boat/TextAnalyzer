package com.seaboat.text.analyzer.dict;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.data.structure.ACTrieTree;
import com.seaboat.text.analyzer.segment.DictSegment;

/**
 * 
 * @author seaboat
 * @date 2018-07-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>name dictionary about some kinds of names.</p>
 */
public class NameDict implements Dict {

	private static Logger logger = Logger.getLogger(NameDict.class);

	private static ACTrieTree tree = null;
	private static String DIC_FILE = "/chinese-names.dic";
	private static NameDict instance;

	private NameDict() {
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
		logger.debug("loading name dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
	}

	public NameDict(List<String> words) {
		tree = new ACTrieTree();
		long st = 0;
		if (logger.isDebugEnabled())
			st = System.nanoTime();
		try {
			tree.build(words);
		} catch (Exception e) {
			logger.error(e);
		}
		logger.debug("loading name dictionary elapsed time : " + (System.nanoTime() - st) / (1000 * 1000) + "ms");
	}

	public static NameDict get() {
		if (instance != null)
			return instance;
		synchronized (NameDict.class) {
			if (instance == null)
				instance = new NameDict();
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
			while ((line = bufferedReader.readLine()) != null)
				words.add(line.trim());
		} catch (FileNotFoundException e) {
			logger.error("File not found", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
		return words;
	}

	public List<String> searchName(String s) {
		return tree.acSearch(s);
	}

	@Override
	public List<Integer> prefixSearch(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStringByIndex(Integer i) {
		// TODO Auto-generated method stub
		return null;
	}

}
