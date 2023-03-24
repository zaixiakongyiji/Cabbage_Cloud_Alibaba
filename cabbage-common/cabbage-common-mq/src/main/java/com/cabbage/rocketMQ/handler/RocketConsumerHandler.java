package com.cabbage.rocketMQ.handler;

import com.alibaba.fastjson.JSONObject;
import com.cabbage.constants.RocketConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = RocketConstants.TopicTest, consumerGroup = "defaultGroup")
public class RocketConsumerHandler implements RocketMQListener<JSONObject> {

//    @Value("${rocketmq.consumer.group}")
//    public  String consumerGroup;

    @Override
    public void onMessage(JSONObject message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
