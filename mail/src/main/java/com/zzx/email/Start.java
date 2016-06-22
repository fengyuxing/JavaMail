package com.zzx.email;

import com.zzx.email.bussiness.SendEmail;

/**
 * Author: zhangshupeng
 * Email: zhangshupeng@xywy.com
 * Date: 2016/6/22 14:27
 */
public class Start {
    public static void main(String[] args){
        Runnable run = new SendEmail();
        run.run();
    }
}
