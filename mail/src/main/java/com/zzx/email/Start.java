package com.zzx.email;

import com.zzx.email.bussiness.SendEmail;

import org.apache.log4j.PropertyConfigurator;

/**
 * Author: zzx
 *  
 * Date: 2016/6/22 14:27
 */
public class Start {
    static{
        PropertyConfigurator.configure(".\\log4j.properties");
    }
    public static void main(String[] args){
        Runnable run = new SendEmail();
        run.run();
    }
}
