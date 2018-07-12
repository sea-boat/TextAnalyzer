package com.seaboat.text.analyzer.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * @author seaboat
 * @date 2017-05-17
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>synonym util for operating synonym.dic.</p>
 */
public class SynonymUtil {
	protected static Logger logger = Logger.getLogger(SynonymUtil.class);
	private Multimap<String, String> multimap = ArrayListMultimap.create();
	private static SynonymUtil instance = null;

	private SynonymUtil(String path) {
		if (path == null)
			path = System.getProperty("user.dir") + "/resources/synonym.dic";
		FileInputStream in;
		try {
			in = new FileInputStream(path);
			BufferedReader bufferedReader = new BufferedReader(new UnicodeReader(in, "utf-8"));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] ss = line.split(";");
				multimap.put(ss[0], ss[1]);
				multimap.put(ss[1], ss[0]);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			logger.error("File not found", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
	}

	public static SynonymUtil get(String path) {
		if (instance != null)
			return instance;
		synchronized (SynonymUtil.class) {
			if (instance == null)
				instance = new SynonymUtil(path);
			return instance;
		}
	}

	public Collection<String> getSynonym(String term) {
		return multimap.get(term);
	}

}
