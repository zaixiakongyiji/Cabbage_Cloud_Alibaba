package com.cabbage.rabbitMQ.handler;

import com.rabbitmq.client.ReturnCallback;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ConfirmRabbitMessageHandler implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("消息确认成功"+b+"|返回错误消息"+s+"|附带数据"+correlationData);
        log.info("消息确认成功"+b+"|返回错误消息"+s+"|附带数据"+correlationData);
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        System.out.println("消息发送失败|"+returned.getMessage().getBody());
        log.error("消息发送失败|"+returned.getMessage().getBody());

    }
}
