package com.bbeerr.wechat.card;


/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jackylian
 */
public class WxCardGeneralCoupon extends WxCard
{
    public WxCardGeneralCoupon()
    {
        init("GENERAL_COUPON");
    }
    
    public void setDefaultDetail(String detail)
    {
        m_data.put("default_detail", detail);
    }
    
    public String getDefaultDetail()
    {
        return m_data.optString("default_detail");
    }
}
