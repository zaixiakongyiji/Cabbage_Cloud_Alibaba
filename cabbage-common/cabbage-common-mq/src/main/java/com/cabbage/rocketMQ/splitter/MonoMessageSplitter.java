package com.cabbage.rocketMQ.splitter;

import java.util.Iterator;

public class MonoMessageSplitter implements Iterator<byte[]> {
    // 切割大小
    public final int SPLIT_SIZE = 1024 * 1024 * 4 - 80;
    public final byte[] message;
    private int cursor = 0;

    public MonoMessageSplitter(byte[] message) {
        this.message = message;
    }

    @Override
    public boolean hasNext() {
        return cursor < size();
    }

    @Override
    public byte[] next() {
        byte[] r;
        int len;
        if (cursor < size() - 1) {
            len = SPLIT_SIZE;
        } else {
            len = message.length - cursor * SPLIT_SIZE;
        }
        r = new byte[len];
        for (int i = 0; i < len; i++) {
            r[i] = message[i + cursor * SPLIT_SIZE];
        }
        cursor++;
        return r;
    }

    public int size() {
        int s = message.length / SPLIT_SIZE;
        int y = message.length % SPLIT_SIZE;
        if (y != 0) {
            s++;
        }
        return s;
    }

}

//    SplitMessage sm = new SplitMessage(m);
//    int len = sm.size();
//    int i = 0;
//    while (sm.hasNext()){
//            byte [] now = sm.next();
//            // 消息头设置总长度与本消息位置，为了 costumer 端拼接
//            Message msg = MessageBuilder.withPayload(now).setHeader(MessageConst.PROPERTY_KEYS, id + "-" + i + "-" + len).build();
//            // 异步发送
//            rocketMQTemplate.asyncSend("testTopic:tag", msg, new SendCallback(){
//@Override
//public void onSuccess(SendResult sendResult){
//        // 成功处理
//        }
//@Override
//public void onException(Throwable throwable){
//        // 错误处理
//        }
//        });
//        i++;
//        }
//        return "done";

