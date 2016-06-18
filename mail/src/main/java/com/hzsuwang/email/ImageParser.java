/**
 * 
 */
package com.hzsuwang.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * @Site：http://www.hzsuwang.com
 * @Email：hzsuwang@163.com
 * @@blog:http://www.hzqvod.com
 * @author tony.yan
 * 
 */
public class ImageParser {
	/**
	 * 获取传入网页内容中所有图片的地址，以ImageElement的List返回
	 * 
	 * @param content
	 *            根据指定URL获取的内容
	 * @return 所有图片组成的ImageElement的对象List
	 * @throws ParserException
	 */
	public static ArrayList<ImageElement> imageParser(String content) throws ParserException {
		ArrayList<ImageElement> ret = new ArrayList<ImageElement>();
		Parser myParser = null;
		NodeList nodeList = null;
		myParser = Parser.createParser(content, "GBK");
		NodeFilter imageFilter = new NodeClassFilter(ImageTag.class);
		NodeFilter backgroundFiler = new BackgroundFilter();
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { imageFilter, backgroundFiler });
		Map<String, String> imageSrc = new HashMap<String, String>();

		nodeList = myParser.parse(lastFilter);
		Node[] nodes = nodeList.toNodeArray();
		for (int i = 0; i < nodes.length; i++) {
			Node anode = (Node) nodes[i];
			ImageElement fe = new ImageElement();
			if (anode instanceof ImageTag) {// 除开背景图片的所有其它的图片
				ImageTag imageNode = (ImageTag) anode;
				if (imageSrc.get(imageNode.getAttribute("src")) == null) {
					fe.setSrc(imageNode.getAttribute("src"));
					ret.add(fe);
					imageSrc.put(imageNode.getAttribute("src"), imageNode.getAttribute("src"));
				}
			} else {// 获取背景图片的URL
				String bg = anode.getText();
				int start = bg.indexOf("background=") + "background=".length();
				int end = 0;
				bg = bg.substring(start);
				if (bg.startsWith("\"")) {// 背景图片如<body
											// background="pic/123.gif"> 或 <body
											// background="pic/123.gif"
											// width="100">
					bg = bg.substring(1);
					end = bg.indexOf("\"");
				} else { // 背景图片如<body background=pic/123.gif width="100">
					end = bg.indexOf(" ");
				}
				if (end == -1) // 背景图片如<body background=pic/123.gif>
					end = bg.indexOf(">");
				bg = bg.substring(0, end - 1);
				if (!bg.equals("")) {
					if (imageSrc.get(bg) == null) {
						fe.setSrc(bg);
						ret.add(fe);
						imageSrc.put(bg, bg);
					}
				}
			}
		}
		return ret;
	}
}

/* 背景图片Filter */
class BackgroundFilter implements NodeFilter {
	public boolean accept(Node node) {
		/* 表示当前节点中的内容如果包括指定的内容，则返回true，这一行即用户需要的数据 */
		if (node.getText().indexOf("background=") > 0) {
			return true;
		} else {
			return false;
		}
	}
}
