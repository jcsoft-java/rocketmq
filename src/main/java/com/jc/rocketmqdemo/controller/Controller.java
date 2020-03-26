package com.jc.rocketmqdemo.controller;

import com.jc.rocketmqdemo.common.Constants;
import com.jc.rocketmqdemo.common.JmsConfig;
import com.jc.rocketmqdemo.message.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * @Copyright: 2020JCSoft
 * @author: Zjf
 * @since: 2020/3/26 11:16
 * @Version 1.0
 */
@Slf4j
@RestController
public class Controller {
    @Autowired
    private Producer producer;

    private List<String> mesList;

    /**
     * 初始化消息
     */
    public Controller() {
        mesList = new ArrayList<>();
        mesList.add("小小");
        mesList.add("爸爸");
        mesList.add("妈妈");
        mesList.add("爷爷");
        mesList.add("奶奶");

    }

    @RequestMapping("/text/rocketmq")
    public Object callback() throws Exception {
        //总共发送五次消息
        for (String s : mesList) {
            //创建生产信息
            Message message = new Message(Constants.BizType.ORDER_PRODUCT_STOCK_ADD, "testtag", ("小小一家人的称谓:" + s).getBytes());
            //发送
            SendResult sendResult = producer.getProducer().send(message);
            log.info("输出生产者信息={}", sendResult);
        }
        return "成功";
    }
}
