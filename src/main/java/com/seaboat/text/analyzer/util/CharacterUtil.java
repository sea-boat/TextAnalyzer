package com.seaboat.text.analyzer.util;

/**
 * 
 * @author seaboat
 * @date 2018-07-15
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>character util.</p>
 */
public class CharacterUtil {

	private static final char[] connectors = new char[] { '+', '#', '&', '.', '_', '-' };

	public static char full2Half(char c) {
		if (c == 12288) {
			c = ' ';
		} else if (c >= ' ' && c <= 65374) {
			c = (char) (c - 65248);
		} else {
		}
		return c;
	}

	public static boolean isChinese(char ch) {
		if (ch >= 0x4E00 && ch <= 0x9FA5)
			return true;
		return false;
	}

	public static boolean isEnglish(char ch) {
		if ((ch >= 0x0041 && ch <= 0x005A) || (ch >= 0x0061 && ch <= 0x007A))
			return true;
		return false;
	}

	public static boolean isDigit(char ch) {
		if (ch >= 0x0030 && ch <= 0x0039)
			return true;
		return false;
	}

	public static boolean isConnector(char ch) {
		for (char connector : connectors)
			if (ch == connector)
				return true;
		return false;
	}

	public static StringBuilder preprocess(String s) {
		StringBuilder sb = new StringBuilder();
		char[] charArray = s.toCharArray();
		for (char c : charArray) {
			c = full2Half(c);
			if (isChinese(c) || isEnglish(c) || isDigit(c) || isConnector(c))
				sb.append(c);
		}
		return sb;
	}

	public static int bytesToInt(byte[] src, int offset) {
		int value;
		if (src.length == 1)
			value = (int) ((src[offset] & 0xFF) << 24);
		else if (src.length == 2)
			value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16));
		else if (src.length == 3)
			value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16)
					| ((src[offset + 2] & 0xFF) << 8));
		else
			value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16)
					| ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3] & 0xFF));
		return value;
	}
}
