package com.seaboat.text.analyzer.hownet;

import java.io.InputStreamReader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.log4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * @author seaboat
 * @date 2018-06-05
 * @version 1.0
 * <pre><b>email: </b>849586227@qq.com</pre>
 * <pre><b>blog: </b>http://blog.csdn.net/wangyangzhizhou</pre>
 * <p>the parser for hownet sememe.</p>
 */
public class SememeParser implements Parser {

	private static Logger logger = Logger.getLogger(SememeParser.class);

	public Multimap<String, Sememe> parse(String file) {
		logger.debug("loading hownet sememe...");
		long time = System.currentTimeMillis();
		Multimap<String, Sememe> map = HashMultimap.create();
		try {
			InputStreamReader read = new InputStreamReader(this.getClass().getResourceAsStream(file), "UTF-8");
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			XMLEventReader xmlEventReader = inputFactory.createXMLEventReader(read);
			while (xmlEventReader.hasNext()) {
				XMLEvent event = xmlEventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					if (startElement.getName().toString().equals("sememe")) {
						String en = startElement.getAttributeByName(QName.valueOf("en")).getValue();
						String cn = startElement.getAttributeByName(QName.valueOf("cn")).getValue();
						String id = startElement.getAttributeByName(QName.valueOf("id")).getValue();
						Attribute attr = startElement.getAttributeByName(QName.valueOf("define"));
						String define = (attr == null ? null : attr.getValue());

						Sememe sememe = new Sememe(id, en, cn, define);
						map.put(cn, sememe);
					}
				}
			}
			read.close();
		} catch (Exception e) {
			logger.error("error occurs when parsing hownet, ", e);
		}
		time = System.currentTimeMillis() - time;
		logger.debug("hownet is parsed completely!  time elapsed: " + time + "ms");
		return map;
	}

}
