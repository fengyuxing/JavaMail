package com.zzx.email.dao;

import com.zzx.email.bean.Receiver;
import com.zzx.email.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: zzx
 *
 * Date: 2016/6/22 14:46
 */
public class ReceiverDaoLocal implements IReceiverDao {
    private List<Receiver> receivers = new ArrayList<Receiver>();

    public ReceiverDaoLocal(String pathOrFilename) {
        List<String> list=FileUtil.readStringList(pathOrFilename);
        for (String sender:list){
            Receiver s=new Receiver();
            String[] args=sender.trim().split(",");
            if (args.length==2){
                s.setName(args[0]);
                s.setEmailAddress(args[1]);
                receivers.add(s);
            }
        }
    }

    @Override
    public Receiver getNextReceiver() {
        if (receivers.size()==0)
            throw  new RuntimeException("No more receivers");
        Receiver receiver=receivers.get(0);
        receivers.remove(0);
        return receiver;
    }
}
