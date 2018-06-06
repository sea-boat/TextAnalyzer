package com.seaboat.text.analyzer.hownet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>term of hownet.</p>
 */
public class Term {

	private static String[][] Type = { { "=", "事件" }, { "aValue|属性值", "属性值" }, { "qValue|数量值", "数量值" },
			{ "attribute|属性", "属性" }, { "quantity|数量", "数量" }, { "unit|", "单位" }, { "%", "部件" } };

	protected String word;
	protected String pos;
	protected String define;
	protected boolean bSubstantive;
	protected String mainSememe;
	protected String[] secondSememes;
	protected String[] relationSememes;
	protected String[] symbolSememes;

	public Term(String word, String pos, String def) {
		this.word = word;
		this.pos = pos;
		this.define = (def == null) ? "" : def.trim();
		if (define.length() > 0 && define.charAt(0) == '{' && define.charAt(define.length() - 1) == '}') {
			this.bSubstantive = false;
		} else {
			this.bSubstantive = true;
		}

		parseDefine(def);
	}

	private void parseDefine(String tokenString) {
		List<String> secondList = new ArrayList<String>();
		List<String> relationList = new ArrayList<String>();
		List<String> symbolList = new ArrayList<String>();
		if (!this.bSubstantive) {
			tokenString = define.substring(1, define.length() - 1);
		}
		StringTokenizer token = new StringTokenizer(tokenString, ",", false);
		if (token.hasMoreTokens()) {
			this.mainSememe = token.nextToken();
		}

		main: while (token.hasMoreTokens()) {
			String item = token.nextToken();
			if (item.equals(""))
				continue;
			String symbol = item.substring(0, 1);
			for (int i = 0; i < Constants.Symbol_Descriptions.length; i++) {
				if (symbol.equals(Constants.Symbol_Descriptions[i][0])) {
					symbolList.add(item);
					continue main;
				}
			}
			if (item.indexOf('=') > 0) {
				relationList.add(item);
			} else {
				secondList.add(item);
			}
		}
		this.secondSememes = secondList.toArray(new String[secondList.size()]);
		this.relationSememes = relationList.toArray(new String[relationList.size()]);
		this.symbolSememes = symbolList.toArray(new String[symbolList.size()]);

	}

	public String getType() {
		for (int i = 0; i < Type.length; i++) {
			if (define.toUpperCase().indexOf(Type[i][0].toUpperCase()) >= 0) {
				return Type[i][1];
			}
		}
		return "普通概念";
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public boolean isbSubstantive() {
		return bSubstantive;
	}

	public String getMainSememe() {
		return mainSememe;
	}

	public String[] getSecondSememes() {
		return secondSememes;
	}

	public String[] getRelationSememes() {
		return relationSememes;
	}

	public String[] getSymbolSememes() {
		return symbolSememes;
	}

	@Override
	public boolean equals(Object anObject) {
		if (anObject instanceof Term) {
			Term c = (Term) anObject;
			return word.equals(c.word) && define.equals(c.define);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return define == null ? word.hashCode() : define.hashCode();
	}

	public Set<String> getAllSememeNames() {
		Set<String> names = new HashSet<String>();
		names.add(getMainSememe());
		for (String item : getRelationSememes()) {
			names.add(item.substring(item.indexOf("=") + 1));
		}
		for (String item : getSymbolSememes()) {
			names.add(item.substring(1));
		}
		for (String item : getSecondSememes()) {
			names.add(item);
		}
		return names;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=");
		sb.append(this.word);
		sb.append("; pos=");
		sb.append(this.pos);
		sb.append("; define=");
		sb.append(this.define);
		sb.append("; 第一基本义元:[" + mainSememe);

		sb.append("]; 其他基本义元描述:[");
		for (String sem : secondSememes) {
			sb.append(sem);
			sb.append(";");
		}

		sb.append("]; [关系义元描述:");
		for (String sem : relationSememes) {
			sb.append(sem);
			sb.append(";");
		}

		sb.append("]; [关系符号描述:");
		for (String sem : symbolSememes) {
			sb.append(sem);
			sb.append(";");
		}
		sb.append("]");
		return sb.toString();
	}
}
