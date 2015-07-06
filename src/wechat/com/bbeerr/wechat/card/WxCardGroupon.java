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
public class WxCardGroupon extends WxCard
{

    public WxCardGroupon()
    {
        init("GROUPON");
    }
    
    public void setDealDetail(String detail)
    {
        m_data.put("deal_detail", detail);
    }
    
}
