package com.bbeerr.wechat.serv.service;

import com.bbeerr.wechat.base.util.WxCardSignUtil;

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
	
	public static void main(String args[]){
		String s1 = "1434348131754404408475E0o2-at6NcC2OsJiQTlwlEr94UavE-4wTglmdLJKcZ04PkPPfu0f51baJERNgBlp3nlcuQkVIJLVNt28k0sk3AoPAaJuEvwy0Wjdum7iT7a4h5hpT0pPAaJuCP80axAlpgOnXKedv21HJk";
		String s2 ="1434348131754404408475E0o2-at6NcC2OsJiQTlwlOeC7N1ZHnKBN--LrPgDq3hnP4rGHAkvZFvFj9xEoZskZMlgdISGx-5dmeos-5UDNQoPAaJuEvwy0Wjdum7iT7a4h5hpT0pPAaJuCP80axAlpgOnXKedv21HJk";
		System.out.println(s1.equals(s2));
	}
	
}
