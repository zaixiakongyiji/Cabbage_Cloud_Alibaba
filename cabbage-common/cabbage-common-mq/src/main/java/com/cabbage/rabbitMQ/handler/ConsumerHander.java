package com.cabbage.rabbitMQ.handler;

import com.alibaba.fastjson2.JSON;
import com.cabbage.constants.RabbitConstants;
import com.rabbitmq.client.Channel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ConsumerHander {



    /**
     * 延迟队列处理器
     * @param messageStruct
     * @param message
     * @param channel
     */
    @RabbitListener(queues=RabbitConstants.DELAY_QUEUE)
    @RabbitHandler
    public void directQueueHandlerManualAck(String messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("延迟队列，手动ACK，接收消息：{}", JSON.toJSONString(messageStruct));
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 直接队列1 处理器
     * @param messageStruct
     * @param message
     * @param channel
     */
    @RabbitListener(queuesToDeclare  = @Queue(RabbitConstants.DIRECT_MODE_QUEUE_ONE))
    @RabbitHandler
    public void QueueDirect1HandlerManualAck(String messageStruct, Message message, Channel channel) {
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("直接队列1，手动ACK，接收消息：{}", JSON.toJSONString(messageStruct));
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            try {
                // 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
                System.out.println("nope");
            }
        }
    }



}
