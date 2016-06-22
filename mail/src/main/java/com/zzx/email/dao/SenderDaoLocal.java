package com.zzx.email.dao;

import com.zzx.email.bean.Sender;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhangshupeng
 * Email: zhangshupeng@xywy.com
 * Date: 2016/6/22 14:46
 */
public class SenderDaoLocal implements ISenderDao {
    private List<String> senders = new ArrayList<String>();

    public SenderDaoLocal(String pathOrFilename) {
        FileReader read = null;
        BufferedReader br = null;
        try {

            if (pathOrFilename.indexOf(":") < 0) {// 根据传入的路径中是否带":"确认传入的是相对路径还是绝对路径，相对路径则做路径补充，绝对路径直接使用
                pathOrFilename = new File("").getAbsolutePath() + File.separator + pathOrFilename;
            }
            read = new FileReader(pathOrFilename);
            br = new BufferedReader(read);
            String info = null;
            while ((info = br.readLine()) != null) {
                if (!info.trim().equals("")) {
                    senders.add(info);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                br.close();
                read.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Sender getNextSender() {
        return null;
    }
}
