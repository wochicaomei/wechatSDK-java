package com.bbeerr.wechat.subs.service;

import com.bbeerr.wechat.util.WxCardSignUtil;

/**微信卡券签名
 * @author Administrator
 *
 */
public class CardSignService {

	/**微信卡券签名                     是否必须
	 * @param api_ticket	是
	 * @param card_id		是
	 * @param timeStamp		是
	 * @param code			否
	 * @param opnid			否
	 * @param balance		否
	 * @return
	 */
	public static String WxCardSign(String api_ticket,String timeStamp,String openid,String code ,String card_id ){
		WxCardSignUtil signer = new WxCardSignUtil();
		signer.AddData(api_ticket);
		signer.AddData(timeStamp);
		signer.AddData(openid);
		signer.AddData(code);
		signer.AddData(card_id);
		return signer.GetSignature();
	}
	
}
