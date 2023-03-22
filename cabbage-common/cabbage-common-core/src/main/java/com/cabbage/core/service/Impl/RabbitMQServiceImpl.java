package com.cabbage.core.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.cabbage.constants.RabbitConstants;
import com.cabbage.core.service.RabbitMQService;
import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    RabbitTemplate rabbitTemplate;



    @Override
    public String sendDirect(String msg){
        try {
//            String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
//            String sendTime = sdf.format(new Date());
//            Map<String, Object> map = new HashMap<>();
//            map.put("msgId", msgId);
//            map.put("sendTime", sendTime);
//            map.put("msg", msg);
            rabbitTemplate.convertAndSend(RabbitConstants.DIRECT_MODE_QUEUE_ONE, (msg));
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String sendFanout(String msg){
        try {
            rabbitTemplate.convertAndSend(RabbitConstants.FANOUT_MODE_QUEUE,"", (msg));
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String sendTopic1(String msg){
        try {

            rabbitTemplate.convertAndSend(RabbitConstants.TOPIC_MODE_QUEUE, "queue.aaa.bbb",(msg));
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String sendTopic2(String msg){
        try {

            rabbitTemplate.convertAndSend(RabbitConstants.TOPIC_MODE_QUEUE,"ccc.queue", (msg));
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String sendTopic3(String msg){
        try {

            rabbitTemplate.convertAndSend(RabbitConstants.TOPIC_MODE_QUEUE,"3.queue", (msg));
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String sendMsgByFanoutExchange(String msg){
        Map<String, Object> message = getMessage(msg);
        try {
            rabbitTemplate.convertAndSend(RabbitConstants.DIRECT_MODE_QUEUE_ONE, "", message);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String sendDelay(String msg, String msg1, String msg2) {
        try {
        rabbitTemplate.convertAndSend(RabbitConstants.DELAY_MODE_QUEUE, RabbitConstants.DELAY_QUEUE, (msg + DateUtil.date()), message -> {
            message.getMessageProperties().setHeader("x-delay", 5000);
            return message;
        });
        rabbitTemplate.convertAndSend(RabbitConstants.DELAY_MODE_QUEUE, RabbitConstants.DELAY_QUEUE, (msg1 + DateUtil.date()), message -> {
            message.getMessageProperties().setHeader("x-delay", 2000);
            return message;
        });
        rabbitTemplate.convertAndSend(RabbitConstants.DELAY_MODE_QUEUE, RabbitConstants.DELAY_QUEUE, (msg2 + DateUtil.date()), message -> {
            message.getMessageProperties().setHeader("x-delay", 8000);
            return message;
        });
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    public String sendToRabbit(String msg){
//        Message message=new Message(msg.getBytes(StandardCharsets.UTF_8));
        try {
//            RabbitConstants.DIRECT_MODE_QUEUE_ONE,
            rabbitTemplate.convertAndSend(RabbitConstants.FANOUT_MODE_QUEUE, "",msg);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    //组装消息体
    private Map<String, Object> getMessage(String msg) {
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        String sendTime = sdf.format(new Date());
        Map<String, Object> map = new HashMap<>();
        map.put("msgId", msgId);
        map.put("sendTime", sendTime);
        map.put("msg", msg);
        return map;
    }
}
