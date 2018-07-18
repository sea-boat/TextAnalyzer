package com.seaboat.text.analyzer.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author seaboat
 * @date 2018-07-18
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>character util.</p>
 */
public class ClearDataUtil {

	public static void main(String[] args) {
		System.out.println("Start... ");
		String pathname = "D:\\TextAnalyzer\\src\\main\\resources\\synonym.dic";
		String newPath = "D:\\TextAnalyzer\\src\\main\\resources\\1.dic";
		try {
			File file = new File(pathname);
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
			BufferedReader buffer = new BufferedReader(new InputStreamReader(fis, "utf-8"), 20 * 1024 * 1024);
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File(newPath)), "utf-8");

			Set<String> set = new HashSet<String>();
			String temp = "";
			int x = 0;
			while ((temp = buffer.readLine()) != null) {
				set.add(temp);
				if (x % 30000 == 0) {
					System.out.print("..");
				}
				x++;
			}
			fis.close();
			buffer.close();
			for (String xxser : set) {
				out.write(xxser + "\r\n");

			}
			System.out.println("");
			out.close();
			System.out.println("size = " + set.size());
			System.out.println("End...");
		} catch (Exception e) {
			System.out.println("文件太大了,建议先100MB大小..");
		}
	}

}
