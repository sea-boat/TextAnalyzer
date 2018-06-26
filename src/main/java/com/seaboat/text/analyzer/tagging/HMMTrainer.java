package com.seaboat.text.analyzer.tagging;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.seaboat.text.analyzer.ml.hmm.HMMModel;

/**
 * 
 * @author seaboat
 * @date 2018-05-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a hmm model trainer.</p>
 */
public class HMMTrainer {
	private static String path="./model/hmm/hmm.model";

	public static void main(String[] args) {
		HMMModel model = new HMMModel();
		model.train();
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
			out.writeObject(model);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
