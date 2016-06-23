/**
 * 
 */
package com.zzx.email.bussiness;

import com.zzx.email.config.MailConfig;
import com.zzx.email.bean.Mail;
import com.zzx.email.bean.Receiver;
import com.zzx.email.bean.Sender;
import com.zzx.email.util.FileUtil;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;


public class SendEmail implements Runnable {
	protected Logger logger = Logger.getLogger(SendEmail.class);
	private Sender sender;
	private  Receiver receiver;
	private  Mail mail;
	public SendEmail() {
	}

	public void run() {
		getSender();
		while (true){
			getReceiver();
			getMail();
			sendMail();
			try {
				Thread.sleep(MailConfig.sendInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean sendMail() {
		Date sendDate = new Date();
		logger.debug(new Date().toString() + ": 开始向" + receiver.getEmailAddress() + "邮箱发送邮件....");
		boolean flag = false;
		HtmlEmail email = new HtmlEmail();
		email.setAuthentication(sender.getEmailAddress(), sender.getPassword());
		email.setHostName(sender.getServerHost());
		email.setCharset(MailConfig.charset);
		// 设置邮件标题
		email.setSubject(mail.getTitle());
		try {
			addAttachment(mail, email);
		} catch (EmailException e) {
			e.printStackTrace();
		}
		try {
			// 设置发件人
			email.setFrom(sender.getEmailAddress());
			// 设置收件人
			email.addTo(receiver.getEmailAddress());
			// Html邮件上下文，邮件内容
			email.setHtmlMsg(mail.getContent());
			// 设置邮件发送时间
			email.setSentDate(sendDate);
			email.send();
			flag = true;
			logger.info(new Date().toString() + ":成功发送邮件");
		} catch (Exception ex) {
			logger.error("发送邮件出现异常！");
		}
		return flag;
	}

	private Receiver getReceiver() {
		receiver= MailConfig.receiver.getNextReceiver();
		return receiver;
	}

	private Mail getMail() {
		if (mail==null){
			String mailContent=FileUtil.readString(MailConfig.mailContent);
			mail=new Mail();
			mail.setContent(mailContent);
			mail.setTitle("zsp");
		}
		return mail;
	}

	private Sender getSender() {
		sender= MailConfig.sender.getNextSender();
		return sender;
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
