package com.cabbage.core.service;

public interface RabbitMQService {
    String sendDirect(String msg);

    String sendFanout(String msg);

    String sendTopic1(String msg);

    String sendTopic2(String msg);

    String sendTopic3(String msg);

    String sendMsgByFanoutExchange(String msg);

    String sendDelay(String msg, String msg1, String msg2);

    String sendToRabbit(String msg);
}
