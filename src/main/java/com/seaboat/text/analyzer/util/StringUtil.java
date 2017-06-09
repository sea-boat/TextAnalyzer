package com.seaboat.text.analyzer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;

import com.seaboat.text.analyzer.IDF;

/**
 * 
 * @author seaboat
 * @date 2017-06-09
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>string util.</p>
 */
public class StringUtil {

  protected static Logger logger = Logger.getLogger(StringUtil.class);

  public static boolean isNumericAndLetter(String str) {
    if (str.matches("^[a-zA-Z0-9]+$")) return true;
    return false;
  }

  public static boolean isContainNumber(String str) {
    Pattern p = Pattern.compile(".*\\d+.*");
    Matcher m = p.matcher(str);
    if (m.matches()) return true;
    return false;
  }

  public static boolean isMobile(String str) {
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$");
    m = p.matcher(str);
    b = m.matches();
    return b;
  }

  public static boolean isPhone(String str) {
    Pattern p1 = null, p2 = null;
    Matcher m = null;
    boolean b = false;
    p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
    p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
    if (str.length() > 9) {
      m = p1.matcher(str);
      b = m.matches();
    } else {
      m = p2.matcher(str);
      b = m.matches();
    }
    return b;
  }

  public static String[] FILTER_1 = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二",
      "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六",
      "二十七", "二十八", "二十九", "三十", "三十一"};;
  public static String[] FILTER_2 = {"年", "月", "日"};

  public static boolean isDate(String str) {
    for (String s1 : FILTER_1) {
      if (str.indexOf(s1) != -1) {
        for (String s2 : FILTER_2) {
          if (str.indexOf(s2) != -1) return true;
        }
      }
    }
    return false;
  }
}
