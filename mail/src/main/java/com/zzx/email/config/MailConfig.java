/**
 * 
 */
package com.zzx.email.config;

import com.zzx.email.dao.IReceiverDao;
import com.zzx.email.dao.ISenderDao;
import com.zzx.email.dao.ReceiverDaoLocal;
import com.zzx.email.dao.SenderDaoLocal;

import java.io.File;

/**
 * @Site：http://www.hzsuwang.com
 * @Email：hzsuwang@163.com
 * @@blog:http://www.hzqvod.com
 * @author tony.yan
 * 
 */
public class MailConfig {
	public static final String basePath = "data"+File.separator ;
	// 邮件发送间隔时间，不可以太短，如果发送速度过快，很容易被对方的邮件服务器认为是垃圾邮件并退回，建议最少10秒，此处以秒为单位
	public static final int sendInterval = 10;
	// 保存发送成功邮件的文件，可以配置为绝对路径，也可以配置为相对路径，不配置不保存
	public static final ISenderDao sender = new SenderDaoLocal(basePath+"sender.txt");
	// 包含已经发送了的电子邮件的地址.可以配置为绝对路径，也可以配置为相对路径，不配置不保存
	public static final IReceiverDao receiver = new ReceiverDaoLocal(basePath+"receiver.txt");
	// 发送邮件使用的编码
	public static final String charset="utf-8";
	// 发送邮件使用的编码
	public static final String mailContent=basePath+"mailContent.txt";
}
