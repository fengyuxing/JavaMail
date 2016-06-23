package com.zzx.email.dao;

import com.zzx.email.bean.Receiver;

/**
 * Author: zzx
 *
 * Date: 2016/6/22 14:44
 */
public interface IReceiverDao {
    public Receiver getNextReceiver();
}
