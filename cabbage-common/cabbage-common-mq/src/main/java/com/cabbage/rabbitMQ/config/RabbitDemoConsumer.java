//package com.cabbage.rabbitMQ.config;
//
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
////@RabbitListener(queues = {RabbitMQConfig.RABBITMQ_TOPIC})
//@Component
//public class RabbitDemoConsumer {
//
//    @RabbitHandler
//    public void process(Map map){
//        System.out.println("从生产者接受到消息"+map.toString());
//    }
//}
