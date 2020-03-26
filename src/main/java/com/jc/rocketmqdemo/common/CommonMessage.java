package com.jc.rocketmqdemo.common;

import java.io.Serializable;

/*
 *
 * @Copyright: 2020JCSoft
 * @author: Zjf
 * @since: 2020/3/26 13:35
 * @Version 1.0
 */
public class CommonMessage implements Serializable {
    private Integer id;
    private String bizId;
    private String bizType;
    private String bizData;
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
