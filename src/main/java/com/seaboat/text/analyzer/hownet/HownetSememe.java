package com.seaboat.text.analyzer.hownet;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * 
 * @author seaboat
 * @date 2018-06-06
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>hownet's sememe.</p>
 */
public class HownetSememe {

	private static Logger logger = Logger.getLogger(HownetSememe.class);

	private static Multimap<String, Sememe> sememes = null;

	private static HownetSememe instance;

	private static String FILE = "/hownet-sememe.xml";

	private HownetSememe() {
		sememes = new SememeParser().parse(FILE);
	}

	public static HownetSememe getInstance() {
		if (instance == null) {
			synchronized (HownetSememe.class) {
				if (instance == null)
					instance = new HownetSememe();
			}
		}
		return instance;
	}

	public List<String> getDefine(String word) {
		Collection<Sememe> coll = sememes.get(word);
		List<String> list = Lists.newArrayList();
		for (Sememe s : coll)
			list.add(s.getDefine());
		return list;
	}

}
