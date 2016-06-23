package com.zzx.email.bean;

/**
 * Author: zzx
 *
 * Date: 2016/6/22 15:34
 */
public class Mail {
    private String title;
    private String content;
    // 附件所在路径
    private String attachmentPath;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }
}
