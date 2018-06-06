package com.seaboat.text.analyzer.hownet;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the glossary hownet.</p>
 */
public class HownetGlossary {

	private static Logger logger = Logger.getLogger(HownetGlossary.class);

	private static Multimap<String, Term> glossary = null;

	private static HownetGlossary instance;

	private static String FILE = "/hownet-glossary.xml";

	private HownetGlossary() {
		glossary = HashMultimap.create();
		new GlossaryParser().parse(FILE, glossary);
	}

	public static HownetGlossary getInstance() {
		if (instance == null) {
			synchronized (HownetGlossary.class) {
				if (instance == null)
					instance = new HownetGlossary();
			}
		}
		return instance;
	}

	public Collection<Term> getTerms(String key) {
		Collection<Term> concepts = glossary.get(key);
		// if (BlankUtils.isBlank(concepts)) {
		// concepts = autoCombineConcepts(key, null);
		// }
		return concepts;
	}

}
