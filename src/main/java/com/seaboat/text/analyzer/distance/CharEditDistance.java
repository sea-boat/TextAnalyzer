package com.seaboat.text.analyzer.distance;

/**
 * 
 * @author seaboat
 * @date 2018-05-28
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the implement of standard edit distance. the cost is simply 0 or 1.</p>
 */
public class CharEditDistance {

	public static int getEditDistance(String s, String t) {
		int d[][];
		int n;
		int m;
		int i;
		int j;
		char s_i;
		char t_j;
		int cost;

		n = s.length();
		m = t.length();
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}
		for (i = 1; i <= n; i++) {
			s_i = s.charAt(i - 1);
			for (j = 1; j <= m; j++) {
				t_j = t.charAt(j - 1);
				cost = (s_i == t_j) ? 0 : 1;
				d[i][j] = Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1);
				d[i][j] = Math.min(d[i][j], d[i - 1][j - 1] + cost);
			}
		}
		return d[n][m];
	}

}
