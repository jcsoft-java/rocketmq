package com.jc.rocketmqdemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/*
 *
 * @Copyright: 2020JCSoft
 * @author: Zjf
 * @since: 2020/3/26 14:11
 * @Version 1.0
 */
@AllArgsConstructor
@Data
@ToString
public class ProductOrder {
    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 订单类型(订单创建、订单付款、订单完成）
     */
    private String type;
}
