package com.seaboat.text.analyzer.hownet;

import java.io.InputStreamReader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;

import com.google.common.collect.Multimap;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the parser for hownet glossary.</p>
 */
public class GlossaryParser implements Parser {

	private static Logger logger = Logger.getLogger(GlossaryParser.class);

	public void parse(String file, Multimap<String, Term> map) {
		logger.debug("loading hownet glossary...");
		long time = System.currentTimeMillis();
		try {
			InputStreamReader read = new InputStreamReader(this.getClass().getResourceAsStream(file), "UTF-8");
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(read);
			while (xmlEventReader.hasNext()) {
				XMLEvent event = xmlEventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().toString().equals("c")) {
						String word = startElement.getAttributeByName(QName.valueOf("w")).getValue();
						String define = startElement.getAttributeByName(QName.valueOf("d")).getValue();
						String pos = startElement.getAttributeByName(QName.valueOf("p")).getValue();
						map.put(word, new Term(word, pos, define));
					}
				}
			}
			read.close();
		} catch (Exception e) {
			logger.error("error occurs when parsing hownet, ", e);
		}
		time = System.currentTimeMillis() - time;
		logger.debug("hownet is parsed completely!  time elapsed: " + time + "ms");
	}

}
