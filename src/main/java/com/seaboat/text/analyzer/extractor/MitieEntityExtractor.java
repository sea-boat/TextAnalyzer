package com.seaboat.text.analyzer.extractor;

import java.util.List;

import org.apache.log4j.Logger;

import com.seaboat.text.analyzer.util.DataReader;
import com.seaboat.text.analyzer.util.WordSegmentUtil;

import edu.mit.ll.mitie.EntityMention;
import edu.mit.ll.mitie.EntityMentionVector;
import edu.mit.ll.mitie.NamedEntityExtractor;
import edu.mit.ll.mitie.NerTrainer;
import edu.mit.ll.mitie.NerTrainingInstance;
import edu.mit.ll.mitie.StringVector;

/**
 * 
 * @author seaboat
 * @date 2017-10-31
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>A named entity extractor by using mitie lib.</p>
 */
public class MitieEntityExtractor implements Extractor {

	protected static Logger logger = Logger.getLogger(MitieEntityExtractor.class);

	private static String TRAIN_FILE = "data/tag_test.txt";

	static {
		try {
			System.loadLibrary("javamitie");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("java.library.path=" + System.getProperty("java.library.path"));
		}
	}

	@Override
	public void train(String samplesFile) {
		if (samplesFile == null)
			samplesFile = TRAIN_FILE;
		NerTrainer nerTrainer = new NerTrainer("model/mitie_model/total_word_feature_extractor.dat");
		List<String> texts = DataReader.readContent(samplesFile);
		for (String text : texts) {
			String[] line = text.split("###");
			String[] words = line[0].split("/");
			StringVector stringVector = new StringVector();
			for (String word : words)
				stringVector.add(word);
			NerTrainingInstance nerTrainingInstance = new NerTrainingInstance(stringVector);
			String[] ss = line[1].split("/");
			for (String s : ss) {
				String[] info = s.substring(1, s.length() - 1).split(",");
				nerTrainingInstance.addEntity(Long.parseLong(info[0]), Long.parseLong(info[1]), info[2]);
			}
			nerTrainer.add(nerTrainingInstance);
		}
		nerTrainer.setThreadNum(4);
		nerTrainer.train("model/mitie_model/test_ner_model.dat");
	}

	@Override
	public List<String> predict(String text) {
		NamedEntityExtractor ner = new NamedEntityExtractor("model/mitie_model/test_ner_model.dat");
		StringVector possibleTags = ner.getPossibleNerTags();
		List<String> words = WordSegmentUtil.seg(text);
		StringVector sVector = new StringVector();
		for (String word : words)
			sVector.add(word);
		try {
			EntityMentionVector entities = ner.extractEntities(sVector);
			logger.debug("entities size : " + entities.size());
			for (int i = 0; i < entities.size(); ++i) {
				EntityMention entity = entities.get(i);
				String tag = possibleTags.get(entity.getTag());
				Double score = entity.getScore();
				String scoreStr = String.format("%1$,.3f", score);
				logger.debug("   Score: " + scoreStr + ": " + tag + ":");
				for (int j = entity.getStart(); j < entity.getEnd(); ++j) {
					logger.debug(words.get(j) + " ");
				}
				logger.debug("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
