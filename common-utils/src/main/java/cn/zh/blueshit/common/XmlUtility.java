package cn.zh.blueshit.common;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class XmlUtility {
	public static HashMap<String, Object> xmlParse(String subject) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Document document = DocumentHelper.parseText(subject);
		Element root = document.getRootElement();
		for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
			Element subjectkey = (Element) it.next();
			String keyname = subjectkey.getName();
			if (subjectkey.elementIterator().hasNext()) {
				HashMap<?, ?> subresult = xmlParse(subjectkey.asXML());
				putValue(result, keyname, subresult);
			} else {
				String value = subjectkey.getText();
				putValue(result, keyname, value);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static void putValue(HashMap<String, Object> hashmap, String key, Object value) {
		// System.out.println("key:" + key + "   value:" + value);
		Object existvalue = hashmap.get(key);
		if (existvalue == null)
			hashmap.put(key, value);
		else {
			if (existvalue instanceof List) {
				((ArrayList) existvalue).add(value);
			} else {
				ArrayList<Object> list = new ArrayList<Object>();
				list.add(existvalue);
				list.add(value);
				hashmap.put(key, list);
			}
		}
	}

	public static HashMap<String, Object> xmlParse(File file) throws Exception {
		SAXReader reader = new SAXReader();
		
		Document document = reader.read(file);
		HashMap<String, Object> result = new HashMap<String, Object>();
		Element root = document.getRootElement();
		for (Iterator<?> it = root.elementIterator(); it.hasNext();) {
			Element subjectkey = (Element) it.next();
			String keyname = subjectkey.getName();
			if (subjectkey.elementIterator().hasNext()) {
				HashMap<?, ?> subresult = xmlParse(subjectkey.asXML());
				putValue(result, keyname, subresult);
			} else {
				String value = subjectkey.getText();
				putValue(result, keyname, value);
			}
		}
		return result;
	}

	public static void main(String[] args) {
		// File file=new
		// File(Thread.currentThread().getContextClassLoader().getResource("")+"/xml/unlockTemplate.xml");
		try {
			String path = Thread.currentThread().getContextClassLoader().getResource("").toString() + "xml/unlockTemplate.xml";
			System.out.println("path=" + path);
			File file = new File(path); 
			BufferedReader br = null;
			SAXReader reader = new SAXReader();
			//Document document = reader.read(br);
			System.out.println(file.getAbsolutePath() + "=========");
			
			StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<OrderPay><orderType>70</orderType><orderId>170805254</orderId><amount>0.01</amount><payEnum>122</payEnum><payTime>20130626113911</payTime><bankCode>273</bankCode><from>50</from></OrderPay> ");
			HashMap<String, Object> hm = XmlUtility.xmlParse(sb.toString());
		System.out.println(	hm.get("amount"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
	}
}
