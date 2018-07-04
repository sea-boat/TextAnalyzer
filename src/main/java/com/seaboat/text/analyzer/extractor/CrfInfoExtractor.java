package com.seaboat.text.analyzer.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author seaboat
 * @date 2018-06-14
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>An extractor that can extract addresses,IDs and names.It will call a python script.</p>
 */
public class CrfInfoExtractor implements InfoExtractor {

	public static String dir = new File("").getAbsolutePath();

	private static String script_path = dir + "/python/crf_ner/crf_ner.py";

	private static String model_path = dir + "/model/crf/crf.model";

	@Override
	public List<String> getIDs(String text) {
		String line;
		String string = "";
		try {
			String[] args = new String[] { "python", script_path, "predict", text, model_path };
			Process pr = Runtime.getRuntime().exec(args);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			while ((line = in.readLine()) != null) {
				string += line;
			}
			in.close();
			pr.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> IDs = new ArrayList<String>();
		if (string.length() > 0) {
			String[] labels = string.split(" ");
			List<Integer> idBegins = new ArrayList<Integer>();
			List<Integer> idEnds = new ArrayList<Integer>();
			for (int i = 0; i < labels.length; i++) {
				if (labels[i].equals("IB")) {
					idBegins.add(i);
				}
				if (labels[i].equals("IE")) {
					idEnds.add(i);
				}
			}
			for (int j = 0; j < idBegins.size(); j++)
				IDs.add(text.substring(idBegins.get(j), idEnds.get(j) + 1));
		}
		return IDs;
	}

	@Override
	public List<String> getNames(String text) {
		String line;
		String string = "";
		try {
			String[] args = new String[] { "python", script_path, "predict", text, model_path };
			Process pr = Runtime.getRuntime().exec(args);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			while ((line = in.readLine()) != null) {
				string += line;
			}
			in.close();
			pr.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> IDs = new ArrayList<String>();
		if (string.length() > 0) {
			String[] labels = string.split(" ");
			List<Integer> idBegins = new ArrayList<Integer>();
			List<Integer> idEnds = new ArrayList<Integer>();
			for (int i = 0; i < labels.length; i++) {
				if (labels[i].equals("PB")) {
					idBegins.add(i);
				}
				if (labels[i].equals("PE")) {
					idEnds.add(i);
				}
			}
			for (int j = 0; j < idBegins.size(); j++)
				IDs.add(text.substring(idBegins.get(j), idEnds.get(j) + 1));
		}
		return IDs;
	}

	@Override
	public List<String> getAddrs(String text) {
		String line;
		String string = "";
		try {
			String[] args = new String[] { "python", script_path, "predict", text, model_path };
			Process pr = Runtime.getRuntime().exec(args);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			while ((line = in.readLine()) != null) {
				string += line;
			}
			in.close();
			pr.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<String> IDs = new ArrayList<String>();
		if (string.length() > 0) {
			String[] labels = string.split(" ");
			List<Integer> idBegins = new ArrayList<Integer>();
			List<Integer> idEnds = new ArrayList<Integer>();
			for (int i = 0; i < labels.length; i++) {
				if (labels[i].equals("LB")) {
					idBegins.add(i);
				}
				if (labels[i].equals("LE")) {
					idEnds.add(i);
				}
			}
			for (int j = 0; j < idBegins.size(); j++)
				IDs.add(text.substring(idBegins.get(j), idEnds.get(j) + 1));
		}
		return IDs;
	}

}
