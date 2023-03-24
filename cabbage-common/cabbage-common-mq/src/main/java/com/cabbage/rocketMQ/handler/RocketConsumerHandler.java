package com.cabbage.rocketMQ.handler;

import com.cabbage.constants.RocketConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = RocketConstants.TopicTest, consumerGroup = "defaultGroup")
public class RocketConsumerHandler {

    @Value("${rocketmq.consumer.group}")
    public  String consumerGroup;

    public void message(){

    }
}
