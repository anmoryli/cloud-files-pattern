package com.anmory.cloudfile.adapter;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-14 下午11:29
 */

public class Adapter implements AliPay, WeChatPay{
    private AliPay aliPay;
    private WeChatPay weChatPay;

    public void setAliPay(AliPay aliPay) {
        this.aliPay = aliPay;
    }

    public void setWeChatPay(WeChatPay weChatPay) {
        this.weChatPay = weChatPay;
    }

    public String aliPay() {
        return aliPay.aliPay();
    }

    public String weChatPay() {
        return weChatPay.weChatPay();
    }
}
