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
public interface Handler {
	/**
	 * 邮件发送之前，对邮件的内容根据每个email关键字，进行特定的替换. 如在发送之前需要根据电子邮件获取当前接收方的名称，可以在这里估处理
	 * 
	 * @param email
	 *            电子邮件地址
	 * @param htmlContent
	 *            发送的Html网页内容
	 * @throws Exception
	 */
	public String dealContent(String email, String htmlContent) throws Exception;
}
