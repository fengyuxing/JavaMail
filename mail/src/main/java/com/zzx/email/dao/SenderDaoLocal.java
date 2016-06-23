package com.zzx.email.dao;

import com.zzx.email.bean.Sender;
import com.zzx.email.util.FileUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: zzx
 *
 * Date: 2016/6/22 14:46
 */
public class SenderDaoLocal implements ISenderDao {
    private List<Sender> senders = new LinkedList<>();
    private List<Sender> inUsed = new LinkedList<>();
    public SenderDaoLocal(String pathOrFilename) {
        List<String> list=FileUtil.readStringList(pathOrFilename);
        for (String sender:list){
            Sender s=new Sender();
            String[] args=sender.trim().split(",");
            if (args.length==3){
                s.setEmailAddress(args[0]);
                s.setPassword(args[1]);
                s.setServerHost(args[2]);
                senders.add(s);
            }
        }
    }

    @Override
    public Sender getNextSender() {
        if (senders.size()==0)
            throw  new RuntimeException("No more senders");
        Sender sender=senders.get(0);
        senders.remove(0);
        inUsed.add(sender);
        return sender;
    }
}
