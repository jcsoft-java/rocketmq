package com.jc.rocketmqdemo.controller;

import com.jc.rocketmqdemo.common.JmsConfig;
import com.jc.rocketmqdemo.common.ProductOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/*
 * 演示分区顺序消费--发送消息
 * @Copyright: 2020JCSoft
 * @author: Zjf
 * @since: 2020/3/26 14:10
 * @Version 1.0
 */

/**
  *1、顺序消息暂不支持广播模式。(集群消费是消息只能被一个消费端消费，广播模式是每个消费端都消费消息)
 * 2、顺序消息不支持异步发送方式，否则将无法严格保证顺序。
 * 3、建议同一个 Group ID 只对应一种类型的 Topic，即不同时用于顺序消息和无序消息的收发。
 * 4、对于全局顺序消息，建议创建实例个数 >=2。
 **/
@Slf4j
@RestController
public class OrderController {
    private static List<ProductOrder> orderList = null;
    private static String producerGroup = "order_producer";

    /**
     * 模拟数据
     */
    static {
        orderList = new ArrayList<>();
        orderList.add(new ProductOrder("XXX001", "订单创建"));
        orderList.add(new ProductOrder("XXX001", "订单付款"));
        orderList.add(new ProductOrder("XXX001", "订单完成"));
        orderList.add(new ProductOrder("XXX002", "订单创建"));
        orderList.add(new ProductOrder("XXX002", "订单付款"));
        orderList.add(new ProductOrder("XXX002", "订单完成"));
        orderList.add(new ProductOrder("XXX003", "订单创建"));
        orderList.add(new ProductOrder("XXX003", "订单付款"));
        orderList.add(new ProductOrder("XXX003", "订单完成"));
    }

    @GetMapping("/order/message")
    public void sendMessage() throws Exception {
        //示例生产者
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        //不开启vip通道 开通端口会减2
        producer.setVipChannelEnabled(false);
        //绑定name server
        producer.setNamesrvAddr(JmsConfig.NAME_SERVER);
        producer.start();
        for (ProductOrder order : orderList) {
            //1、生成消息
            Message message = new Message(JmsConfig.TOPIC+"order", "", order.getOrderId(), order.toString().getBytes());
            //2、发送消息是 针对每条消息选择对应的队列
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    //3、arg的值其实就是下面传入 orderId
                    String orderid = (String) arg;
                    //4、因为订单是String类型，所以通过hashCode转成int类型
                    int hashCode = orderid.hashCode();
                    //5、因为hashCode可能为负数 所以取绝对值
                    hashCode = Math.abs(hashCode);
                    //6、保证同一个订单号 一定分配在同一个queue上
                    long index = hashCode % mqs.size();
                    return mqs.get((int) index);
                }
            }, order.getOrderId(),50000);

            System.out.printf("Product：发送状态=%s, 存储queue=%s ,orderid=%s, type=%s\n", sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(), order.getOrderId(), order.getType());
        }

        producer.shutdown();
    }

}
