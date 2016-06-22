package com.zzx.email.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: zhangshupeng
 * Email: zhangshupeng@xywy.com
 * Date: 2016/6/22 14:59
 */
public class FileUtil {
    /**
     * 按行读取文件
     * @param file
     * @return
     */
    public static List<String> readFile(String file){
        FileReader read = null;
        BufferedReader br = null;
        List<String> lines = new ArrayList<String>();
        try {
            if (file.indexOf(":") < 0) {// 根据传入的路径中是否带":"确认传入的是相对路径还是绝对路径，相对路径则做路径补充，绝对路径直接使用
                file = new File("").getAbsolutePath() + File.separator + file;
            }
            read = new FileReader(file);
            br = new BufferedReader(read);
            String info = null;
            while ((info = br.readLine()) != null) {
                if (!info.trim().equals("")) {
                    lines.add(info);
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
        return lines;
    }
}
