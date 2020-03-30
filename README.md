# rocketmq
-----------------配置-------------------

JmsConfig 全局配置

CommonMessage 具体的业务消息

Constants 消息状态和业务理性

ProductOrder 请忽略



----------------消息生产者--------------

Controller 一般的消息生产者

OrderController 顺序消费的消息生产者

ProducerController 分别演示同步发送消息（send（）），异步发送消息（需重写SendCallback（）方法），单向发送消息（sendOneway（））

Producer 消息生产中心





----------------消息消费者---------------

Consumer 一般的消息消费者（使用MessageListenerConcurrently作为消息监听）

ConsumerOrderly 顺序的消息消费者（使用MessageListenerOrderly作为消息监听）
