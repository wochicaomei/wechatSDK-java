package com.bbeerr.wechat.subs.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bbeerr.wechat.base.service.ICardService;
import com.bbeerr.wechat.base.service.ICardSignService;
import com.bbeerr.wechat.base.service.IMenuService;
import com.bbeerr.wechat.base.service.IMessageService;
import com.bbeerr.wechat.base.service.IOAuthService;
import com.bbeerr.wechat.base.service.ISignService;
import com.bbeerr.wechat.base.service.IUserService;
import com.bbeerr.wechat.base.service.impl.CardServiceImpl;
import com.bbeerr.wechat.base.util.HttpUtil;
import com.bbeerr.wechat.base.util.MessageUtil;
import com.bbeerr.wechat.base.util.SignUtil;
import com.bbeerr.wechat.base.util.WeixinUtil;
import com.bbeerr.wechat.constants.ConstantsUrl;
import com.bbeerr.wechat.constants.ConstantsWeChat;
import com.bbeerr.wechat.entity.message.resp.Article;
import com.bbeerr.wechat.entity.message.resp.NewsMessage;
import com.bbeerr.wechat.entity.message.resp.TextMessage;
import com.bbeerr.wechat.entity.user.UserWeiXin;
import com.bbeerr.wechat.subs.constants.ConstantsSubscribe;

/**
 * 添米微信订阅测试号Controller
 */
@Controller
public class WechatSubcribeController {
	
	@Autowired
	IMenuService menuService;
	@Autowired
	IUserService userService;
	@Autowired
	ICardService cardService;
	@Autowired
	ICardSignService cardSignService;
	@Autowired
	IOAuthService oauthService;
	@Autowired
	ISignService signService;
	@Autowired
	IMessageService messageService;
	
	/**
	 * 创建菜单
	 *       菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	@RequestMapping(value = "/subscribe/createMenu")
	public ModelAndView createMenu(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String menu_url = WechatSubcribeController.class.getClassLoader().getResource("wechat_draw_menu.json").toString().replace("file:", "");
		String jsonMenu = WeixinUtil.ReadFile(menu_url);
		System.out.println("menu:" + jsonMenu);
		int result = menuService.createMenu(jsonMenu, ConstantsSubscribe.getAccess_token());
		System.out.println(result == 0 ? "菜单创建成功" : "菜单创建失败");
		mv.addObject("menu_result", result == 0 ? "菜单创建成功" : "菜单创建失败");
		mv.setViewName("/wechat/wechat_result");
		return mv;
	}

	@RequestMapping(value = "/subscribe/getUserList")
	public ModelAndView getUersList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		
		List<String> userList = new ArrayList<String>();
		String accessToken = ConstantsSubscribe.getAccess_token();
		if(accessToken !=null && !accessToken.equals("")){
		for (UserWeiXin user : userService.getUserList(accessToken)) {
			userList.add("openid:" + user.getOpenid() + "  unionid:" + user.getUnionid() + "  nickname:" + user.getNickname());
		}
		}
		mv.addObject("userList", userList);
		mv.setViewName("/wechat/wechat_result");
		return mv;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/subscribe/createCard")
	public ModelAndView createCard(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		// 添加卡券
		List<String> urls = new ArrayList<String>();
		Map<Integer, String> cardList = new HashMap<Integer, String>();
		List<String> cardIdList = new ArrayList<String>();

		String card_10_url = CardServiceImpl.class.getClassLoader().getResource("wechat_card_cash_10.json").toString().replace("file:", "");
		String card_20_url = CardServiceImpl.class.getClassLoader().getResource("wechat_card_cash_20.json").toString().replace("file:", "");
		String card_50_url = CardServiceImpl.class.getClassLoader().getResource("wechat_card_cash_50.json").toString().replace("file:", "");
		String card_charge_url = CardServiceImpl.class.getClassLoader().getResource("wechat_card_charge.json").toString().replace("file:", "");
		String card_watch_url = CardServiceImpl.class.getClassLoader().getResource("wechat_card_watch.json").toString().replace("file:", "");

		urls.add(card_10_url);
		urls.add(card_20_url);
		urls.add(card_50_url);
		urls.add(card_charge_url);
		urls.add(card_watch_url);

		for (int i = 0; i < urls.size(); i++) {
			cardList.put(i, WeixinUtil.ReadFile(urls.get(i)));
			System.out.println(cardList.get(i));
		}
		for (int i = 0; i < cardList.size(); i++) {
			cardIdList.add(cardService.createCard(cardList.get(i), ConstantsSubscribe.getAccess_token()).toString());
		}

		mv.addObject("cardIdList", cardIdList);
		mv.setViewName("/wechat/wechat_result");
		return mv;
	}

	/**
	 * 微信菜单转发 传递url与openid
	 * 
	 * @param request
	 * @param out
	 * @throws IOException
	 */
	@RequestMapping(value = "/subcribe/menu_redirect", method = RequestMethod.GET)
	public ModelAndView redirect(@RequestParam String encrypt_code, HttpServletResponse response, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String code = request.getParameter("code");
		String openid = oauthService.getOAuthAccessToken(ConstantsSubscribe.APPID, ConstantsSubscribe.APPSECRET, code).getOpenid();
		System.out.println("openid" + openid);
		UserWeiXin userInfo = userService.getUserInfo(openid, ConstantsSubscribe.getAccess_token());
		System.out.println("userInfo" + userInfo.toString());
		String state = request.getParameter("state");
		// mv.addObject("openid", openid);
		mv.addObject("url", state);
		// 如果是618活动链接，则添加unionid
		if (state.equals("getwxuser")) {
			mv.addObject("unionid", userInfo.getUnionid());
		}
		mv.setViewName("redirect:" + ConstantsUrl.redirect_url);
		return mv;
	}

	/**
	 * 微信卡券 立即兑换按钮跳转地址 /**
	 * 
	 * @param card_id
	 *            卡券ID
	 * @param encrype_code
	 *            卡券加密后的code，需调用code解码接口进行解码
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/code_redirect", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView code_redirect(@RequestParam String card_id, @RequestParam String encrypt_code, HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		String code = cardService.decryptCode(request, encrypt_code, ConstantsSubscribe.getAccess_token());
		mv.setViewName("redirect:"
				+ "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd3634a6e86554592&redirect_uri=http://test.91tianmi.com/tm_web/menu_redirect&response_type=code&scope=snsapi_base&state=/wx/exchangecode-"
				+ code + "#wechat_redirect");
		return mv;
	}

	/**
	 * 添加卡券页面跳转
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/subcribe/addCard", method = RequestMethod.GET)
	public ModelAndView addCard(@RequestParam("code") String code, @RequestParam("card_id") String card_id, @RequestParam("openid") String openid, @RequestParam("prizeid") String prizeid,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();

		/* 微信签名 */
		// String url = request.getContextPath()+"/addCard";
		String url = "http://test.91tianmi.com/tm_web/subcribe/addCard?card_id=" + card_id + "&openid=" + openid + "&code=" + code + "&prizeid=" + prizeid;
		// code是用户的卡券号码
		// String code = "754404408475";
		Map<String, String> weixin = SignUtil.sign(ConstantsSubscribe.getJsapi_ticket(), url);
		JSONObject wx = new JSONObject();
		for (Map.Entry entry : weixin.entrySet()) {
			wx.put(entry.getKey(), entry.getValue());
		}
		mv.addObject("weixin", wx);
		mv.addObject("timestamp", wx.get("timestamp"));
		mv.addObject("nonceStr", wx.get("nonceStr"));
		mv.addObject("signature", wx.get("signature"));

		mv.addObject("prizeid", prizeid);
		/* 微信签名 end */
		// 卡券签名
		JSONObject cardSign = new JSONObject();
		String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
		// card_id = "pBaiotxpmGTLGN37qNH029GvxkqY";
		// openid ="oBaiot6DDKEhDCcl_8s0CivSBOJs";
		String signature = cardSignService.WxCardSign(ConstantsSubscribe.getApi_ticket(), timeStamp, openid, code, card_id);
		cardSign.put("code", code);
		cardSign.put("openid", openid);
		cardSign.put("timestamp", timeStamp);
		cardSign.put("signature", signature);
		mv.addObject("card_id", card_id);
		mv.addObject("cardSign", cardSign.toString().replace("\"", "\\\""));
		mv.setViewName("/wechat/event/20150618_addCard");
		return mv;
	}

	/**
	 * 核销卡券
	 * 
	 * @param request
	 *            resutl 为0 表示 核销成功，result为1表示核销失败
	 */
	@RequestMapping(value = "/subcribe/destroyCard", method = { RequestMethod.POST, RequestMethod.GET })
	public  void destroyCard(@RequestParam("code") String code, @RequestParam("card_id") String card_id, HttpServletRequest request) {
		int result = cardService.destroyCode(request, code, card_id, ConstantsSubscribe.getAccess_token());
		System.out.println(result == 0 ? "核销卡券成功" : "核销卡券失败");
	}

	/**
	 * 微信签名验证
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/subcribe/check", method = { RequestMethod.POST, RequestMethod.GET })
	public void checkWechat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("begin check");

		// 随机字符串
		String echostr = request.getParameter("echostr");

		System.out.println(echostr);

		// 微信首次验证
		if (echostr != null) {
			PrintWriter out = null;
			try {
				out = response.getWriter();
				// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
				if (signService.checkSignature(ConstantsSubscribe.TOKEN, request)) {
					out.print(echostr);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				out.close();
				out = null;
			}
		} else {
			// 微信消息接收与发送
			try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setCharacterEncoding("UTF-8");

			// 调用核心业务类接收消息、处理消息
			String respMessage = processWebchatRequest(request);

			// 响应消息
			PrintWriter out = null;
			try {
				out = response.getWriter();
				out.print(respMessage);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				out.close();
				out = null;
			}
		}
	}

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return String
	 */
	public  String processWebchatRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 用户openid
			String fromUserName = requestMap.get("FromUserName");

			TextMessage textMessage = (TextMessage) messageService.bulidBaseMessage(requestMap, ConstantsWeChat.RESP_MESSAGE_TYPE_TEXT);
			NewsMessage newsMessage = (NewsMessage) messageService.bulidBaseMessage(requestMap, ConstantsWeChat.RESP_MESSAGE_TYPE_NEWS);

			String respContent = "";
			// 文本消息
			if (msgType.equals(ConstantsWeChat.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				// 单图文消息

			} else if (msgType.equals(ConstantsWeChat.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				List<Article> articleList = new ArrayList<Article>();
				if (eventType.equals(ConstantsWeChat.EVENT_TYPE_SUBSCRIBE)) {

					// 关注,需要将关注者的信息录入到数据库
					UserWeiXin user = userService.getUserInfo(fromUserName, ConstantsSubscribe.getAccess_token());
					Map<String, String> params = sendUserInfo(user.getUnionid(), user.getOpenid(), user.getNickname(), ConstantsUrl.operation_subcribe, ConstantsUrl.wxh_tmtt);
					HttpUtil.http(ConstantsUrl.test_user_url, "POST", params);
					// respContent = "欢迎关注添米微信订阅测试号!";
					Article article = new Article();
					article.setTitle("快乐大转盘 好礼送不停");
					article.setDescription("转发朋友圈可获1次抽奖机会 每天最多转发3次");
					article.setPicUrl("http://test.91tianmi.com/static/css/topper/weixin_head.png");
					article.setUrl(ConstantsUrl.wechat_event20150618_url);
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = messageService.bulidSendMessage(newsMessage, ConstantsWeChat.RESP_MESSAGE_TYPE_NEWS);
				} else if (eventType.equals(ConstantsWeChat.EVENT_TYPE_UNSUBSCRIBE)) {

					// 需要将取消关注者的信息从数据库删除
					UserWeiXin user = userService.getUnsubCribeUserInfo(fromUserName, ConstantsSubscribe.getAccess_token());
					Map<String, String> params = sendUserInfo(user.getUnionid(), user.getOpenid(), user.getNickname(), ConstantsUrl.operation_unsubcribe, ConstantsUrl.wxh_tmtt);
					HttpUtil.http(ConstantsUrl.test_user_url, "POST", params);

				} else if (eventType.equals(ConstantsWeChat.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					// 自定义菜单点击事件
					if (eventKey.equals("lingqu")) {
						respContent = "请进入微信->卡包中，进入卡券，点击立即兑换，即可兑换奖品！";
					} else if (eventKey.equals("lingqu")) {
					}
					textMessage.setContent(respContent);
					respMessage = messageService.bulidSendMessage(textMessage, ConstantsWeChat.RESP_MESSAGE_TYPE_TEXT);
				}

				// 用户领取卡券事件
				else if (eventType.equals(ConstantsWeChat.EVENT_TYPE_USER_GET_CARD)) {
					// 卡券ID
					String cardid = requestMap.get("CardId");
					// 每个用户卡券的code
					String code = requestMap.get("UserCardCode");
					UserWeiXin user = userService.getUserInfo(fromUserName, ConstantsSubscribe.getAccess_token());
					Map<String, String> params = sendCardInfo(ConstantsUrl.wxh_tmtt, user.getOpenid(), ConstantsUrl.operation_subcribe, code, cardid);
					HttpUtil.http(ConstantsUrl.test_card_url, "POST", params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	public String menuSetting(String filePath) {
		return WeixinUtil.ReadFile(filePath);
	}

	/***
	 * 用户关注、取消关注事件推送
	 * 
	 * @param unionid
	 * @param openid
	 * @param nickname
	 * @param operation
	 *            0:取消关注 1 关注
	 * @param wxh
	 *            微信号 tmtt:添米头条 tmcf：添米财富
	 * @return
	 */
	public  Map<String, String> sendUserInfo(String unionid, String openid, String nickname, String operation, String wxh) {
		Map<String, String> map = new HashMap();
		map.put("wxh", wxh);
		map.put("operation", operation);
		map.put("openid", openid);
		map.put("nickname", nickname);
		map.put("unionid", unionid);
		return map;
	}

	/**
	 * 用户领取卡券事件推送
	 * 
	 * @param wxh
	 * @param openid
	 * @param operation
	 * @param code
	 * @param codeid
	 * @return
	 */
	public  Map<String, String> sendCardInfo(String wxh, String openid, String operation, String code, String cardid) {
		Map<String, String> map = new HashMap();
		map.put("wxh", wxh);
		map.put("operation", operation);
		map.put("openid", openid);
		map.put("cardid", cardid);
		map.put("cardcode", code);
		return map;
	}
}
