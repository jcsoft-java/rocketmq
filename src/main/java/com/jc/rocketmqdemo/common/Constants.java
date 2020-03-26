package com.jc.rocketmqdemo.common;

/*
 *
 * @Copyright: 2020JCSoft
 * @author: Zjf
 * @since: 2020/3/26 13:37
 * @Version 1.0
 */
public class Constants {
    public static final class MessageStatus {
        //初始状态
        public static final int INIT = 0;
        //已发送
        public static final int SENT = 1;
        //处理完成
        public static final int END = 2;
    }

    public static final class BizType {
        public static final String ORDER_PRODUCT_STOCK_ADD = "order_product_stock_add";
        public static final String ORDER_PRODUCT_STOCK_DEC = "order_product_stock_dec";
        public static final String ORDER_CREATE = "order_create";
        public static final String ORDER_PAY = "order_pay";
        public static final String ORDER_END = "order_end";
    }

}
