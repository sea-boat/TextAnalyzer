package com.seaboat.text.analyzer.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
 * @date 2017-06-08
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>a util for text vector.</p>
 */
public class VectorUtil {

  protected static Logger logger = Logger.getLogger(VectorUtil.class);

  public static List<String> getVectorDimension(List<String> texts) {
    int docNum = 0;
    IndexWriter indexWriter = null;
    try {
      indexWriter = MemoryIndexUtil.getIndexWriter();
    } catch (IOException e) {
      logger.error("IOException when getting index writer. ", e);
    }
    for (String line : texts) {
      FieldType type = new FieldType();
      type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
      type.setStored(true);
      type.setStoreTermVectors(true);
      type.setTokenized(true);
      Document doc = new Document();
      Field field = new Field("content", line, type);
      doc.add(field);
      try {
        docNum++;
        indexWriter.addDocument(doc);
        indexWriter.commit();
      } catch (IOException e) {
        logger.error("IOException when adding document. ", e);
      }
    }
    // get a whole term vector
    IndexReader reader = null;
    Set<String> vector = null;
    try {
      reader = MemoryIndexUtil.getIndexReader();
      vector = new HashSet<String>();
      for (int docId = 0; docId < docNum; docId++) {
        Terms terms = reader.getTermVector(docId, "content");
        TermsEnum termsEnum = terms.iterator();
        BytesRef thisTerm = null;
        while ((thisTerm = termsEnum.next()) != null) {
          String term = thisTerm.utf8ToString();
          if ((term.length() > 1) && (!StringUtil.isNumericAndLetter(term))
              && (!StringUtil.isMobile(term)) && (!StringUtil.isPhone(term))
              && (!StringUtil.isContainNumber(term)) && (!StringUtil.isDate(term)))
            vector.add(term);
        }
      }
    } catch (IOException e) {
      logger.error("IOException happens ", e);
    }
    List<String> vectorList = new ArrayList<String>(vector);
    return vectorList;
  }

  public static double[][] getVector(int row, List<String> vectorList, IDF idf) {
    Map<String, Double> tfidf = new HashMap<String, Double>();
    // calculating the vector of samples
    double[][] samples = new double[row][vectorList.size()];
    IndexReader reader = null;
    try {
      reader = MemoryIndexUtil.getIndexReader();
      for (int i = 0; i < row; i++) {
        try {
          int j = 0;
          for (int m = 0; m < vectorList.size(); m++) {
            String vTerm = vectorList.get(m);
            Terms terms = reader.getTermVector(i, "content");
            TermsEnum termsEnum = terms.iterator();
            BytesRef thisTerm = null;
            boolean isContain = false;
            while ((thisTerm = termsEnum.next()) != null) {
              String term = thisTerm.utf8ToString();
              if (term.equals(vTerm)) {
                float idfn = idf.getIDF(term);
                int a = (int) termsEnum.totalTermFreq();
                long b = terms.size();
                float tf = (float) a / b;
                samples[i][j] = idfn * tf;
                tfidf.put(term, samples[i][j]);
                isContain = true;
                break;
              }
            }
            // not contain means this vector value is 0
            if (!isContain) samples[i][j] = 0.0;
            j++;
          }
        } catch (IOException e) {
          logger.error("IOException happens ", e);
        }
      }
    } catch (IOException e) {
      logger.error("IOException happens ", e);
    }
    return samples;
  }

}
