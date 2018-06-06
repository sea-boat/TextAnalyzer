package com.seaboat.text.analyzer.hownet;

/**
 * 
 * @author seaboat
 * @date 2018-06-06
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>hownet sememe.</p>
 */
public class Sememe {
	private String id;
	private String cnWord;
	private String enWord;
	private String define;

	public Sememe(String id, String en, String cn, String define) {
		this.id = id;
		this.cnWord = cn;
		this.enWord = en;
		this.define = define;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCnWord() {
		return cnWord;
	}

	public void setCnWord(String cnWord) {
		this.cnWord = cnWord;
	}

	public String getEnWord() {
		return enWord;
	}

	public void setEnWord(String enWord) {
		this.enWord = enWord;
	}

	public String getDefine() {
		return define;
	}

	public void setDefine(String define) {
		this.define = define;
	}

	public int getType() {
		char ch = id.charAt(0);
		switch (ch) {
		case '1':
			return SememeType.Event;
		case '2':
			return SememeType.Entity;
		case '3':
			return SememeType.Attribute;
		case '4':
			return SememeType.Quantity;
		case '5':
			return SememeType.AValue;
		case '6':
			return SememeType.QValue;
		case '7':
			return SememeType.SecondaryFeature;
		case '8':
			return SememeType.Syntax;
		case '9':
			return SememeType.EventRoleAndFeature;
		default:
			return 0;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=");
		sb.append(id);
		sb.append("; cnWord=");
		sb.append(cnWord);
		sb.append("; enWord=");
		sb.append(enWord);
		sb.append("; define=");
		sb.append(define);
		return sb.toString();
	}

}
