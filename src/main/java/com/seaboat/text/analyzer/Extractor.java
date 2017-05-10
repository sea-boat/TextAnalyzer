package com.seaboat.text.analyzer;

import java.util.List;

import com.seaboat.text.analyzer.hotword.Result;

/**
 * 
 * @author seaboat
 * @date 2017-05-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>Extractor interface for all extractors.</p>
 */
public interface Extractor {

  public List<Result> extract(int id, int topN,boolean useScore);

  public List<Result> extract(int id, int topN);
  
  public List<Result> extract(int id);

}
