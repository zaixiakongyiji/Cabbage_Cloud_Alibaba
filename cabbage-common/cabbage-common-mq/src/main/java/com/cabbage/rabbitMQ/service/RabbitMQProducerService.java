package com.cabbage.rabbitMQ.service;

import cn.hutool.core.date.DateUtil;
import com.cabbage.constants.RabbitConstants;
import com.cabbage.rabbitMQ.handler.ConfirmRabbitMessageHandler;
import jakarta.annotation.Resource;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class RabbitMQProducerService {


    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Resource
    RabbitTemplate rabbitTemplate;

    @Autowired
    ConfirmRabbitMessageHandler confirmRabbitMessageHandler;

    @PostConstruct
    public void init() {
        //消息成功到达指定队列的回调方法
        rabbitTemplate.setConfirmCallback(confirmRabbitMessageHandler);
        //消息如果未成功到达队列的
        rabbitTemplate.setReturnsCallback(confirmRabbitMessageHandler);
    }

    public JSONObject toJson(String msg) {
        JSONObject jsonObject = new JSONObject();
        String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        String sendTime = sdf.format(new Date());
        jsonObject.put("msgId", msgId);
        jsonObject.put("sendTime", sendTime);
        jsonObject.put("msg", msg);
        return jsonObject;
    }

    public boolean sendDirect(String msg) {
        try {
            rabbitTemplate.convertAndSend(RabbitConstants.DIRECT_MODE_QUEUE_ONE, toJson(msg));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean sendFanout(String msg) {
        try {
            rabbitTemplate.convertAndSend(RabbitConstants.FANOUT_MODE_QUEUE, "", toJson(msg));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean sendTopic1(String msg) {
        try {

            rabbitTemplate.convertAndSend(RabbitConstants.TOPIC_MODE_QUEUE, "queue.aaa.bbb", toJson(msg));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean sendTopic2(String msg) {
        try {

            rabbitTemplate.convertAndSend(RabbitConstants.TOPIC_MODE_QUEUE, "ccc.queue", toJson(msg));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean sendTopic3(String msg) {
        try {

            rabbitTemplate.convertAndSend(RabbitConstants.TOPIC_MODE_QUEUE, "3.queue", toJson(msg));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean sendMsgByFanoutExchange(String msg) {
        try {
            rabbitTemplate.convertAndSend(RabbitConstants.DIRECT_MODE_QUEUE_ONE, "", toJson(msg));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean sendDelay(String msg, String msg1, String msg2) {
        try {
            rabbitTemplate.convertAndSend(RabbitConstants.DELAY_MODE_QUEUE, RabbitConstants.DELAY_QUEUE, toJson(msg + DateUtil.date()), message -> {
                message.getMessageProperties().setHeader("x-delay", 5000);
                return message;
            });
            rabbitTemplate.convertAndSend(RabbitConstants.DELAY_MODE_QUEUE, RabbitConstants.DELAY_QUEUE, toJson(msg1 + DateUtil.date()), message -> {
                message.getMessageProperties().setHeader("x-delay", 2000);
                return message;
            });
            rabbitTemplate.convertAndSend(RabbitConstants.DELAY_MODE_QUEUE, RabbitConstants.DELAY_QUEUE, toJson(msg2 + DateUtil.date()), message -> {
                message.getMessageProperties().setHeader("x-delay", 8000);
                return message;
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }


    public boolean sendToRabbit(String msg) {
        try {
            rabbitTemplate.convertAndSend(RabbitConstants.FANOUT_MODE_QUEUE, "", toJson(msg));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

}
