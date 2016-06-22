package com.zzx.email;

/**
 * Author: zhangshupeng
 * Email: zhangshupeng@xywy.com
 * Date: 2016/6/22 17:10
 */
public class TestString implements Runnable {
    private static String changeStr(String s) {
        String s1=s.replace("a","b");
        return s1;
    }

    @Override
    public void run() {
        String s="abc";
        System.out.println(s);
        String s1=changeStr(s);
        System.out.println(s);
        System.out.println("changed: "+s1);
    }
}
