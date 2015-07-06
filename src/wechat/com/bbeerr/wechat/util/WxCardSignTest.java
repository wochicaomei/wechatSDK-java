package com.bbeerr.wechat.util;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 微信卡券签名测试
 * @author Administrator
 */
public class WxCardSignTest {
    public static void main(String[] args) throws Exception{
            WxCardSignUtil signer = new WxCardSignUtil();
            signer.AddData("23456");
            signer.AddData("141231233");
            signer.AddData("345667");
            signer.AddData("456789");
            System.out.println(signer.GetSignature());
    }

}