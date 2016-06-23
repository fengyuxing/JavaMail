package com.zzx.email.dao;

import com.zzx.email.bean.Sender;

/**
 * Author: zzx
 *
 * Date: 2016/6/22 14:44
 */
public interface ISenderDao {
    //获取可用的发件人账号
    public Sender getNextSender();
}
