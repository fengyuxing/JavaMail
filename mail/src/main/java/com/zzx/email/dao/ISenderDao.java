package com.zzx.email.dao;

import com.zzx.email.bean.Sender;

/**
 * Author: zhangshupeng
 * Email: zhangshupeng@xywy.com
 * Date: 2016/6/22 14:44
 */
public interface ISenderDao {
    public Sender getNextSender();
}
