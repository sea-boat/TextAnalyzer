package com.seaboat.text.analyzer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

/**
 * 
 * @author seaboat
 * @date 2017-06-01
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a util to store object and get object.</p>
 */
public class ObjectUtil {

  protected static Logger logger = Logger.getLogger(ObjectUtil.class);
  
  public static Object readObjectFromFile(String fileName) {
    File file = new File(fileName);
    FileInputStream in;
    Object temp = null;
    try {
      in = new FileInputStream(file);
      ObjectInputStream objIn = new ObjectInputStream(in);
      temp = objIn.readObject();
      objIn.close();
    } catch (IOException e) {
      logger.error("IOException happens ", e);
    } catch (ClassNotFoundException e) {
      logger.error("ClassNotFoundException happens ", e);
    }
    return temp;
  }
  

  public static void saveObjectToFile(Object o, String file) {
    FileOutputStream out;
    try {
      out = new FileOutputStream(new File(file));
      ObjectOutputStream objOut = new ObjectOutputStream(out);
      objOut.writeObject(o);
      objOut.flush();
      objOut.close();
    } catch (IOException e) {
      logger.error("IOException happens ", e);
    }
  }
  
}
