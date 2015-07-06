package com.bbeerr.wechat.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *用于生成微信卡券签名
	 1、将 api_ticket、timestamp、card_id、code、openid、balance 的 value 值进行字符串
	的字典序排序。
	2．将所有参数字符串拼接成一个字符串进行 sha1 加密，得到 signature。
	3．signature 中的 timestamp 和 card_ext 中的 timestamp 必须保持一致。
	4．假如数据示例中 code=23456，timestamp=141231233，card_id=345667，
	api_ticket=456789 则
	signature=sha1(14123123323456345667456789)=4F76593A4245644FAE4E1BC940F
	6422A0C3EC03E。
 * @author Administrator
 */
public class WxCardSignUtil
{

    public WxCardSignUtil()
    {
        m_param_to_sign = new ArrayList<String>();
    }

    public void AddData(String value)
    {
        m_param_to_sign.add(value);
    }

    public void AddData(Integer value)
    {
        m_param_to_sign.add(value.toString());
    }

    public String GetSignature()
    {
        Collections.sort(m_param_to_sign);
        StringBuilder string_to_sign = new StringBuilder();
        for (String str : m_param_to_sign)
        {
            string_to_sign.append(str);
        }
        System.out.println("string_to_sign:" + string_to_sign);
        try
        {
            MessageDigest hasher = MessageDigest.getInstance("SHA-1");
            byte[] digest = hasher.digest(string_to_sign.toString().getBytes());
            return ByteToHexString(digest);
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return "";
        }
    }
    
    public String ByteToHexString(byte[] data)
    {
        StringBuilder str = new StringBuilder();
        for (byte b : data)
        {
            String hv = Integer.toHexString(b & 0xFF); 
            if( hv.length() < 2 )
                str.append("0");
            str.append(hv);
        }
        return str.toString();
    }
    private ArrayList<String> m_param_to_sign;
}
