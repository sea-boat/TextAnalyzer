package com.seaboat.text.analyzer.distance;

import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2018-05-28
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the implement of standard edit distance. the cost is dependence by similariy.</p>
 */
public class StandardEditDistance {

	public static double getEditDistance(List<EditBlock> list1, List<EditBlock> list2) {
		if (list1 == null || list2 == null)
			throw new RuntimeException("list1 or list2 is null");
		double d[][];
		int n;
		int m;
		int i;
		int j;
		EditBlock s_i;
		EditBlock t_j;

		n = list1.size();
		m = list2.size();
		if (n == 0) {
			double distance = 0.0;
			for (int k = 0; k < m; k++) {
				distance += list2.get(k).getInsertionCost();
			}
			return distance;
		}
		if (m == 0) {
			double distance = 0.0;
			for (int k = 0; k < n; k++) {
				distance += list1.get(k).getDeletionCost();
			}
			return distance;
		}
		d = new double[n + 1][m + 1];
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}
		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}
		for (i = 1; i <= n; i++) {
			s_i = list1.get(i - 1);
			for (j = 1; j <= m; j++) {
				t_j = list2.get(j - 1);
				double cost = s_i.getSubstitutionCost(t_j);
				d[i][j] = Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1);
				d[i][j] = Math.min(d[i][j], d[i - 1][j - 1] + cost);
			}
		}
		return d[n][m];
	}

}
