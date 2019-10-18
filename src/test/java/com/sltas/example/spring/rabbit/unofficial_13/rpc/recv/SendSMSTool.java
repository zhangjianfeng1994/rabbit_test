package com.sltas.example.spring.rabbit.unofficial_13.rpc.recv;

public class SendSMSTool {

    public static boolean sendSMS(String phone,String content){
        System.out.println("发送短信内容：【"+content+"】到手机号："+phone);
        return phone.length() > 6;
    }
}

