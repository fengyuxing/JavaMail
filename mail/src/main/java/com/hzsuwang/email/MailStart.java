/**
 * 
 */
package com.hzsuwang.email;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;
import org.jdom.JDOMException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Site：http://www.hzsuwang.com
 * @Email：hzsuwang@163.com
 * @@blog:http://www.hzqvod.com
 * @author tony.yan
 * 
 */
public class MailStart implements Runnable {
	protected Logger logger = Logger.getLogger(MailStart.class);

	private MailInfo mailInfo = null;
	private final String tipInfoFormat = "一共需要发送{0}封邮件,成功发送{1}封,失败发送{2}封。\n\r失败邮件列表地址：{3}";

	public MailStart() {
		try {
			mailInfo = MailUtil.getMailInfo();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Runnable run = new MailStart();
		run.run();
	}

	public void run() {
		List<String> list = MailUtil.getMailToList(mailInfo.getAddressPath());
		try {
			this.sendHtmlEmail(mailInfo, list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getResultInfo(int sum, int success, int fail, List<String> failList) {
		return MessageFormat.format(this.tipInfoFormat, sum, success, fail, failList.toString());
	}

	/**
	 * 发送邮件
	 * 
	 * @param info
	 *            配置文件信息
	 * @param emailAddresslist
	 *            已经加载到内存的电邮List
	 * @throws Exception
	 */
	private void sendHtmlEmail(MailInfo info, List<String> emailAddresslist) throws Exception {
		List<String> fialAddressList = new ArrayList<String>();
		// 读取已经发送的邮件
		ArrayList<String> sendedEmail = MailUtil.getSendedEmail(info.getSendedAddressPath());
		// 获取不需要发送的邮件服务器
		List donNotSendEmailType = MailUtil.getDonNotSendEmailType(info.getDonNotSendEmailType());
		String saveSuccEmailFile = info.getSaveMailSendedSuccFile();
		String saveFailEmailFile = info.getSaveMailSendedFailFile();
		String htmlContentDealHandler = info.getHandler();
		htmlContentDealHandler = htmlContentDealHandler == null ? "" : htmlContentDealHandler.trim();
		if (saveSuccEmailFile == null) {
			saveSuccEmailFile = "";
		}
		saveSuccEmailFile = saveSuccEmailFile.trim();
		if (saveFailEmailFile == null) {
			saveFailEmailFile = "";
		}
		saveFailEmailFile = saveFailEmailFile.trim();
		// 收件人邮件地址list集合
		int failCount = 0;
		int successCount = 0;
		// 每封邮件发送的间隔时间
		int sendInterval = 0;
		try {
			sendInterval = Integer.parseInt(info.getSendInterval()) * Constants.second;
		} catch (NumberFormatException f) {
			sendInterval = Constants.defaultSendInterval * Constants.second;
		}
		try {
			System.out.println(info.getHtmlPath());
			String htmlPath = info.getHtmlPath();
			String urlPath = "";
			if (!htmlPath.startsWith("http")) {
				if (htmlPath.indexOf(":") < 0) {// 根据传入的路径中是否带":"确认传入的是相对路径还是绝对路径，相对路径则做路径补充，绝对路径直接使用
					htmlPath = new File("").getAbsolutePath() + File.separator + htmlPath;
				}
				if (!htmlPath.startsWith("file:///")) {
					urlPath = "file:///" + htmlPath;
				}
			} else {
				urlPath = htmlPath;
			}
			String context = UrlUtil.getContentByURL(urlPath);
			String email = "";
			Handler handler = null;
			if (!htmlContentDealHandler.equals("")) {
				handler = (Handler) Class.forName(htmlContentDealHandler).newInstance();
			}
			for (int i = 0; i < emailAddresslist.size(); i++) {
				boolean flag = false;

				try {
					email = emailAddresslist.get(i);
					if (email.indexOf("@") <= 0 || email.endsWith("@") || email.endsWith(".") || email.indexOf(".") <= 0) {
						System.out.println("email地址:" + email + " 无效");
						continue;
					}
					// 未发送过的邮件才允许发送
					if (sendedEmail.contains(email)) {
						System.out.println("email " + email + "已经发送过了");
						continue;
					}
					if (!donNotSendEmailType.contains(MailUtil.getEmailHost(email))) {
						sendedEmail.add(email);
						String sendContext = context;
						if (handler != null) {
							sendContext = handler.dealContent(email, sendContext);
						}
						flag = sendSingleHtmlEmail(info, email, sendContext);
					} else {
						continue;
					}
				} catch (Exception e) {
					System.out.println("发送邮件到email地址:" + email + " 时发生异常:");
					e.printStackTrace();
				}
				if (flag) {
					if (!saveSuccEmailFile.equals("")) {
						MailUtil.saveSuccEmail(email, saveSuccEmailFile);
					}
					System.out.println("邮件" + email + "发送成功!");
					successCount++;
				} else {
					if (!saveFailEmailFile.equals("")) {
						MailUtil.saveSuccEmail(email, saveFailEmailFile);
					}
					System.out.println("邮件" + email + "发送失败!");
					fialAddressList.add(email);
					failCount++;
				}
				// 发送一封邮件休息指定的时间再发下一封
				Thread.sleep(sendInterval);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info(getResultInfo(emailAddresslist.size(), successCount, failCount, fialAddressList));

	}

	/**
	 * 发送单个Html文本邮件
	 * 
	 * @param info
	 * @param to
	 * @param context
	 * @return
	 * @throws Exception
	 */
	private boolean sendSingleHtmlEmail(MailInfo info, String to, String context) throws Exception {
		String username = info.getUsername();
		String password = info.getPassword();
		String host = info.getHostName();
		String from = info.getFrom();
		String title = info.getTitle();
		Date sendDate = new Date();
		System.out.println(new Date().toString() + ": 开始向" + to + "邮箱发送邮件....");
		boolean flag = false;
		HtmlEmail email = new HtmlEmail();
		email.setAuthentication(username, password);
		email.setHostName(host);
		email.setCharset(info.getCharset());
		// 设置邮件标题
		email.setSubject(title);
		context = linkPicture2HTML(info, email, context);
		addAttachment(info, email);
		try {
			// 设置发件人
			email.setFrom(from);
			// 设置收件人
			email.addTo(to);
			// Html邮件上下文，邮件内容
			email.setHtmlMsg(context);
			// 设置邮件发送时间
			email.setSentDate(sendDate);
			email.send();
			flag = true;
			logger.info(new Date().toString() + ":成功发送邮件");
		} catch (Exception ex) {
			logger.error("发送邮件出现异常！");
			throw ex;
		}
		return flag;
	}

	/**
	 * 将图片附加于发送的邮件中
	 * 
	 * @param info
	 * @param email
	 * @param context
	 * @return
	 * @throws EmailException
	 * @throws IOException
	 * @throws ParserException
	 */
	private static String linkPicture2HTML(MailInfo info, HtmlEmail email, String context) throws EmailException, IOException, ParserException {
		String htmlPath = info.getHtmlPath();// 发送的HTML的地址
		String htmlFile = "";// 网络HTML的地址
		boolean localFile = true;// 是否本地文件
		if (htmlPath.startsWith("http")) {
			localFile = false;
		}
		if (localFile) {
			File file = new File(htmlPath);
			String name = file.getName();
			/* 获取到发送的文件所在路径 */
			htmlPath = htmlPath.replace(name, "");
		} else {
			// 如htmlPath为"http://www.2345.com/a/b.htm"
			URL u = new URL(htmlPath);
			/* 带"/"的路径，如"/a/b.htm" */
			htmlFile = u.getFile();
			/* 网站的地址"http://www.2345.com" */
			htmlPath = htmlPath.replace(htmlFile, "");
		}
		ArrayList<ImageElement> imageElements = ImageParser.imageParser(context);
		for (ImageElement image : imageElements) {
			String imagePath = image.getSrc();
			String cid = "";
			/************************* 获取本地图片及来自网络的图片都附加于发送的邮件中，但是附加网络图片时有问题（开始） ****************************/
			// if(localFile){
			// if(imagePath.startsWith("http")){//本地文件从网站服务器获取图片
			// URL u = new URL(imagePath);
			// cid = email.embed(u, u.getFile());
			// cid = "cid:"+cid;
			// }else if(imagePath.startsWith("file:///")){//本地文件中的图片采用的是绝对地址
			// File file = new File(imagePath.replace("file:///", ""));
			// cid = email.embed(file);
			// cid = "cid:"+cid;
			// }else{//本地文件的图片采用的相对地址
			// File file = new File(htmlPath+imagePath);
			// cid = email.embed(file);
			// cid = "cid:"+cid;
			// }
			// }else{
			// if(imagePath.startsWith("http")){//网络文件从其它网站服务器获取图片
			// URL u = new URL(imagePath);
			// cid = email.embed(u, u.getFile());
			// cid = "cid:"+cid;
			//
			// }else{//网络文件的图片采用的相对地址
			// URL u = new URL(htmlPath+imagePath);
			// cid = email.embed(htmlPath+imagePath,u.getFile());
			// cid = "cid:"+cid;
			// }
			// }
			// context = context.replace("\""+imagePath, "\""+cid);
			// context = context.replace("="+imagePath, "="+cid);
			/************************* 获取本地图片及来自网络的图片都附加于发送的邮件中，但是附加网络图片时有问题（结束） ****************************/
			/************* 仅对本地的图片于发送的邮件中，对来自网络文件的中的图片采用相对地址图片进行地址处理，但不附加于发送的邮件中（开始） **************/
			if (localFile) {
				if (!imagePath.startsWith("http")) {
					if (imagePath.startsWith("file:///")) {// 本地文件中的图片采用的是绝对地址
						File file = new File(imagePath.replace("file:///", ""));
						cid = email.embed(file);
						cid = "cid:" + cid;
					} else {// 本地文件的图片采用的相对地址
						File file = new File(htmlPath + imagePath);
						cid = email.embed(file);
						cid = "cid:" + cid;
					}
					/**
					 * 这里不直接采用:context = context.replace(imagePath,cid);
					 * 是因为有可能图片名称的后几位相同，如邮件中包括male.jpg,及female.jpg时，在如上替换时就会出错；
					 * 考虑到img的src的写法可能有这样两种：src=male.jpg、src="male.jpg"两种，
					 * 因此采用这样的替换方式可以保证替换掉，并且不会影响到其它的图片
					 */
					context = context.replace("\"" + imagePath, "\"" + cid);
					context = context.replace("=" + imagePath, "=" + cid);
				}
			} else {
				if (!imagePath.startsWith("http")) {// 网络文件的图片采用的相对地址
					String imageUrlPath = "";
					/* 将来自于网络中的图片地址，处理为绝对地址 */
					if (!imagePath.startsWith("/")) {
						imageUrlPath = htmlPath + "/" + imagePath;
					} else {
						imageUrlPath = htmlPath + imagePath;
					}

					context = context.replace("\"" + imagePath, "\"" + imageUrlPath);
					context = context.replace("=" + imagePath, "=" + imageUrlPath);
				}
			}
			/************* 仅对本地的图片于发送的邮件中，对来自网络文件的中的图片采用相对地址图片进行地址处理，但不附加于发送的邮件中（结束） **************/
		}
		return context;
	}

	/**
	 * 增加附件到邮件中，包括指定目录下面的所有子目录
	 * 
	 * @param info
	 * @param email
	 * @throws EmailException
	 */
	private static void addAttachment(MailInfo info, HtmlEmail email) throws EmailException {
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
