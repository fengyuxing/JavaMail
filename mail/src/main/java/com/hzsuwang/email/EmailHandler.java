/**
 * 
 */
package com.hzsuwang.email;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Site：http://www.hzsuwang.com
 * @Email：hzsuwang@163.com
 * @@blog:http://www.hzqvod.com
 * @author tony.yan
 * 
 */
public class EmailHandler implements Handler {
	private static Map<String, String> emailName;
	static {
		String email_name = "email_name.txt";
		try {
			if (emailName == null) {
				emailName = getSendedEmail(email_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String dealContent(String email, String htmlContent) throws Exception {
		String name = emailName.get(email);
		//htmlContent = htmlContent.replace("{1}", name);
		return htmlContent;
	}

	/**
	 * 将邮件、名称放入HashMap中
	 * 
	 * @param email_name
	 *            存放email与名称对应关系的文件
	 * @return 全部对应的HashMap
	 * @throws Exception
	 */
	private static Map<String, String> getSendedEmail(String email_name) throws Exception {
		Map<String, String> sendedEmail = new HashMap<String, String>();
		FileReader read = null;
		BufferedReader br = null;
		try {
			if (email_name.indexOf(":") < 0) {// 根据传入的路径中是否带":"确认传入的是相对路径还是绝对路径，相对路径则做路径补充，绝对路径直接使用
				email_name = new File("").getAbsolutePath() + File.separator + email_name;
			}
			read = new FileReader(email_name);
			br = new BufferedReader(read);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.trim().equals("")) {
					if (line.indexOf(",") > 0) {
						String[] info = line.split(",");
						if (info.length > 1) {
							String email = info[0];
							// 只加载有效的email地址
							if (!(email.indexOf("@") <= 0 || email.endsWith("@") || email.endsWith(".") || email.indexOf(".") <= 0)) {
								String name = info[1];
								sendedEmail.put(email, name);
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		} finally {
			try {
				br.close();
				read.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return sendedEmail;
	}
}
