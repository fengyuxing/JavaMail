package com.zzx.email.dao;

import com.zzx.email.bean.Sender;
import com.zzx.email.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhangshupeng
 * Email: zhangshupeng@xywy.com
 * Date: 2016/6/22 14:46
 */
public class ReceiverDaoLocal implements IReceiverDao {
    private List<String> receivers = new ArrayList<String>();

    public ReceiverDaoLocal(String pathOrFilename) {
        receivers= FileUtil.readFile(pathOrFilename);
    }

    @Override
    public Sender getNextReceiver() {
        return null;
    }
}
