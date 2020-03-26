package com.jc.rocketmqdemo.controller;

import com.jc.rocketmqdemo.message.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 演示三种发送方式
 * @Copyright: 2020JCSoft
 * @author: Zjf
 * @since: 2020/3/26 13:52
 * @Version 1.0
 */
@Slf4j
@RestController
public class ProducerController {
    @Autowired
    private Producer producer;

    @GetMapping("/message")
    public  void  message() throws Exception {
        //1、同步
        sync();
        //2、异步
        async();
        //3、单项发送
        oneWay();
    }
    /**
     * 1、同步发送消息
     */
    private  void sync() throws Exception {
        //创建消息
        Message message = new Message("topic_family", ("  同步发送  ").getBytes());
        //同步发送消息
        SendResult sendResult = producer.getProducer().send(message);
        log.info("Product-同步发送-Product信息={}", sendResult);
    }
    /**
     * 2、异步发送消息
     */
    private  void async() throws Exception {
        //创建消息
        Message message = new Message("topic_family", ("  异步发送  ").getBytes());
        //异步发送消息
        producer.getProducer().send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("Product-异步发送-输出信息={}", sendResult);
            }
            @Override
            public void onException(Throwable e) {
                e.printStackTrace();
                //补偿机制，根据业务情况进行使用，看是否进行重试
            }
        });
    }
    /**
     * 3、单项发送消息
     */
    private  void oneWay() throws Exception {
        //创建消息
        Message message = new Message("topic_family", (" 单项发送 ").getBytes());
        //同步发送消息
        producer.getProducer().sendOneway(message);
    }
}
