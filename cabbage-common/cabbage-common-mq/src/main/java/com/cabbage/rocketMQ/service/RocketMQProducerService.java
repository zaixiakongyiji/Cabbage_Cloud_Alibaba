package com.cabbage.rocketMQ.service;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

@Service
@Slf4j
public class RocketMQProducerService {


    @Autowired
    RocketMQTemplate rocketMQTemplate;


    public Message getMessage(String s) throws UnsupportedEncodingException {
        Message message = new Message();
        message.setBody(s.getBytes(RemotingHelper.DEFAULT_CHARSET));
        return message;
    }

    public SendCallback getSendCallback() {
        return new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.printf("%-10d OK %s %n",
                        sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable e) {
                System.out.printf("%-10d Exception %s %n", e);
                e.printStackTrace();
            }

        };
    }


    public boolean syncProducer(String msg, String topicName) {

        try {
            SendResult sendResult = rocketMQTemplate.syncSend(topicName, getMessage(msg));
            Assert.isTrue(SendStatus.SEND_OK.equals(sendResult.getSendStatus()), "消息发送失败,原因为：" + sendResult.getSendStatus(), sendResult);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }

    }

    public boolean asyncProducer(String msg, String topicName){
            try {
                rocketMQTemplate.asyncSend(topicName, getMessage(msg), getSendCallback());
                return true;
            } catch(Exception e) {
                return false;
            }
    }

    public void onewayProducer(String msg, String topicName) throws UnsupportedEncodingException {
        rocketMQTemplate.sendOneWay(topicName, getMessage(msg));
    }







}
