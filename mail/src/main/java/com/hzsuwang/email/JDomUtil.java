package com.hzsuwang.email;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.File;
import java.io.IOException;

/**
 * @Site：http://www.hzsuwang.com
 * @Email：hzsuwang@163.com
 * @@blog:http://www.hzqvod.com
 * @author tony.yan
 * 
 */
public class JDomUtil {
	/**
	 * 根据XML文件，建立JDom的Document对象
	 * 
	 * @param file
	 *            XML文件
	 * @return Document 返回建立的JDom的Document对象，建立不成功将抛出异常。
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static Document getDocument(File file) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document anotherDocument = builder.build(file);

		return anotherDocument;
	}
}
