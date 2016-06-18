/**
 * 
 */
package com.hzsuwang.email;

/**
 * @Site：http://www.hzsuwang.com
 * @Email：hzsuwang@163.com
 * @@blog:http://www.hzqvod.com
 * @author tony.yan
 * 
 */
public class MailInfo {
	// 邮件登录账号
	private String username = "";
	// 邮件登录密码
	private String password = "";
	// 邮件服务器地址
	private String hostName = "";
	// 发件人邮箱地址
	private String from = "";
	// 邮件标题
	private String title = "";
	// 要发送Html文本路径
	private String htmlPath = "";
	// 包含电子邮件的文本文件，每行一个电子邮件
	private String addressPath = null;
	// 已经发送了的电子邮件所在的文件
	private String sendedAddressPath = null;
	// 附件所在路径
	private String attachmentPath = null;
	// 发送邮件使用的编码
	private String charset = null;
	// 邮件发送间隔时间，不可以太短，如果发送速度过快，很容易被对方的邮件服务器认为是垃圾邮件并退回，建议最少10秒，此处以秒为单位
	private String sendInterval = null;
	// 保存发送成功邮件的文件，可以配置为绝对路径，也可以配置为相对路径，不配置不保存
	private String saveMailSendedSuccFile = null;
	// 包含已经发送了的电子邮件的地址.可以配置为绝对路径，也可以配置为相对路径，不配置不保存
	private String saveMailSendedFailFile = null;
	// 不发送的Email类型，如QQ邮箱就发不出去，在这里设置，不设置就全部发送，以竖线"|"进行分隔，如"qq.com|foxmail.com|sohu.com"
	private String donNotSendEmailType = null;
	/*
	 * 配置Handler.邮件发送之前，对邮件的内容根据每个email关键字，进行特定的替换.
	 * 如在发送之前需要根据电子邮件获取当前接收方的名称，可以在这里估处理.不配置不处理，
	 * 配置的Handler必须实现接口：com._6666_6666.email.handler.Handler
	 */
	private String handler = null;

	public MailInfo() {

	}

	public MailInfo(String username, String password, String hostName, String from, String title) {
		super();
		this.username = username;
		this.password = password;
		this.hostName = hostName;
		this.from = from;
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public String getAddressPath() {
		return addressPath;
	}

	public void setAddressPath(String addressPath) {
		this.addressPath = addressPath;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSendedAddressPath() {
		return sendedAddressPath;
	}

	public void setSendedAddressPath(String sendedAddressPath) {
		this.sendedAddressPath = sendedAddressPath;
	}

	public String getSendInterval() {
		return sendInterval;
	}

	public void setSendInterval(String sendInterval) {
		this.sendInterval = sendInterval;
	}

	public String getSaveMailSendedFailFile() {
		return saveMailSendedFailFile;
	}

	public void setSaveMailSendedFailFile(String saveMailSendedFailFile) {
		this.saveMailSendedFailFile = saveMailSendedFailFile;
	}

	public String getSaveMailSendedSuccFile() {
		return saveMailSendedSuccFile;
	}

	public void setSaveMailSendedSuccFile(String saveMailSendedSuccFile) {
		this.saveMailSendedSuccFile = saveMailSendedSuccFile;
	}

	public String getDonNotSendEmailType() {
		return donNotSendEmailType;
	}

	public void setDonNotSendEmailType(String donNotSendEmailType) {
		this.donNotSendEmailType = donNotSendEmailType;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}
}
