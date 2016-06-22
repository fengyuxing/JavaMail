/**
 * 
 */
package com.zzx.email.bussiness;

import com.zzx.email.bean.Mail;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import java.io.File;


public class SendEmail implements Runnable {
	protected Logger logger = Logger.getLogger(SendEmail.class);

	private final String tipInfoFormat = "一共需要发送{0}封邮件,成功发送{1}封,失败发送{2}封。\n\r失败邮件列表地址：{3}";

	public SendEmail() {
	}

	public void run() {

	}




	/**
	 * 增加附件到邮件中，包括指定目录下面的所有子目录
	 * 
	 * @param info
	 * @param email
	 * @throws EmailException
	 */
	private static void addAttachment(Mail info, HtmlEmail email) throws EmailException {
		String attachmentPath = info.getAttachmentPath();
		if (attachmentPath != null && !attachmentPath.equals("")) {
			File path = new File(attachmentPath);
			if (path.exists()) {
				if (path.isFile()) {/* 文件就直接增加 */
					EmailAttachment oneAttachment = new EmailAttachment();
					oneAttachment.setPath(attachmentPath);
					email.attach(oneAttachment);
				} else if (path.isDirectory()) {
					File[] files = path.listFiles();
					for (int i = 0; i < files.length; i++) {
						if (files[i].isDirectory()) {
							info.setAttachmentPath(files[i].getAbsolutePath());
							addAttachment(info, email);
						} else {
							EmailAttachment oneAttachment = new EmailAttachment();
							oneAttachment.setPath(files[i].getAbsolutePath());
							email.attach(oneAttachment);
						}

					}
				}
			}
		}
	}
	
	
	
}
