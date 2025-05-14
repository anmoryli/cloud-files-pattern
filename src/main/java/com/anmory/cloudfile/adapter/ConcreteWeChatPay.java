package com.anmory.cloudfile.adapter;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-14 下午11:26
 */

public class ConcreteWeChatPay implements WeChatPay{
    @Override
    public String weChatPay() {
        return "微信支付";
    }
}
