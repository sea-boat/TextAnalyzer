package com.seaboat.text.analyzer.extractor;


import java.util.List;


/**
 * 
 * @author seaboat
 * @date 2017-10-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>An extractor interface for information extract.</p>
 */
public interface Extractor {

  public void train(String samplesDir);

  public List<String> predict(String text);

}
