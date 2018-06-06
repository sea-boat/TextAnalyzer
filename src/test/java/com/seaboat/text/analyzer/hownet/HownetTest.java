package com.seaboat.text.analyzer.hownet;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>hownet test.</p>
 */
public class HownetTest {

	@Test
	public void test() {
		HownetGlossary glossary = HownetGlossary.getInstance();
		Collection<Term> coll = glossary.getTerms("人类");
		for (Term t : coll)
			System.out.println(t);
		coll = glossary.getTerms("中国");
		for (Term t : coll)
			System.out.println(t);
	}

	@Test
	public void test2() {
		HownetSememe sememe = HownetSememe.getInstance();
		Collection<String> coll = sememe.getDefine("用具");
		for (String t : coll)
			System.out.println(t);
		coll = sememe.getDefine("资金");
		for (String t : coll)
			System.out.println(t);
	}

}
