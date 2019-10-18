package com.sltas.example.spring.rabbit.unofficial_13.rpc.recv;

import java.util.concurrent.TimeUnit;

public class SendSMSHandler {

	/**
	 * 
	 * <p>
	 * Title: handleMessage
	 * </p>
	 * <p>
	 * Description: 客户端步骤
	 * 
	 * 使用sendAndReceive方法发送消息，该方法返回一个Message对象，该对象就是server返回的结果
	 * sendAndReceive如果超过5s还没有收到结果，则返回null，这个超时时间可以通过rabbitTemplate.setReplyTimeout()来进行设置
	 * server端返回的结果一定要注意，和MessageConverter有关，默认的org.springframework.amqp.support.converter.SimpleMessageConverter会把基本的数据类型转换成Serializable对象，这样的话，client端接收的也是序列化的java对象，所以，需要合理设置MessageConverter。
	 * 示列代码中服务端返回给客户端的是Boolean类型,
	 * 
	 * </p>
	 * @param @param body
	 * @param @return 
	 * @return boolean
	 * @throws
	 * @author 周顺宇 
	 * @date 2018年10月12日 下午2:59:42
	 */
	
//    public boolean handleMessage(byte[] body){
//        String _body = new String(body);
//        System.out.println(_body);
//        String[] sms = _body.split(":");
//        String phone = sms[0];
//        String content = sms[1];
//
//        boolean is = SendSMSTool.sendSMS(phone,content);
//
//        try {
//            TimeUnit.SECONDS.sleep(6);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return is;
//    }
    
    
    public String handleMessage(byte[] body){
        String _body = new String(body);
        System.out.println(_body);
        String[] sms = _body.split(":");
        String phone = sms[0];
        String content = sms[1];

        boolean is = SendSMSTool.sendSMS(phone,content);

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return is ? "success":"false";
    }
}
