package com.bbeerr.wechat.serv.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bbeerr.wechat.base.util.MessageUtil;
import com.bbeerr.wechat.base.util.WeixinUtil;
import com.bbeerr.wechat.constants.ConstantsUrl;
import com.bbeerr.wechat.constants.ConstantsWeChat;
import com.bbeerr.wechat.entity.message.resp.Article;
import com.bbeerr.wechat.entity.message.resp.NewsMessage;
import com.bbeerr.wechat.entity.message.resp.TextMessage;
import com.bbeerr.wechat.entity.user.UserWeiXin;
import com.bbeerr.wechat.serv.constants.ConstantsService;
import com.bbeerr.wechat.serv.service.MenuService;
import com.bbeerr.wechat.serv.service.MessageService;
import com.bbeerr.wechat.serv.service.OAuthService;
import com.bbeerr.wechat.serv.service.SignService;
import com.bbeerr.wechat.serv.service.UserService;
import com.bbeerr.wechat.subs.constants.ConstantsSubscribe;
import com.bbeerr.wechat.subs.controller.WechatSubcribeController;

/**
 * 添米微信服务测试号Controller
 */
@Controller
public class WechatServiceController {

	/**
	 * 微信菜单创建
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/service/createMenu")
	public ModelAndView createMenu(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String menu_url = WechatServiceController.class.getClassLoader().getResource("wechat_menu.json").toString().replace("file:", "");
		String jsonMenu = WeixinUtil.ReadFile(menu_url);
		System.out.println("menu:" + jsonMenu);
		int result = MenuService.createMenu(jsonMenu, ConstantsService.getAccess_token());
		System.out.println(result == 0 ? "菜单创建成功" : "菜单创建失败");
		mv.addObject("menu_result", result == 0 ? "菜单创建成功" : "菜单创建失败");
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
	@RequestMapping(value = "/menu_redirect", method = RequestMethod.GET)
	public ModelAndView redirect(HttpServletResponse response, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String cardcode = null, state1 = null;
		String code = request.getParameter("code");
		String openid = OAuthService.getOAuthAccessToken(ConstantsService.APPID, ConstantsService.APPSECRET, code).getOpenid();
		UserWeiXin userInfo = UserService.getUserInfo(openid, ConstantsService.getAccess_token());
		String state = request.getParameter("state");
		mv.addObject("openid", openid);
		if (state.contains("-")) {
			state1 = state.split("-")[0];
			cardcode = state.split("-")[1];
		}
		if (state1 != null) {
			state = state1;
		}
		mv.addObject("url", state);
		// 如果是618活动链接，则添加unionid
		if (state.equals("getwxuser")) {
			mv.addObject("unionid", userInfo.getUnionid());
		}
		// 卡券立即兑换跳转
		if (state.equals("/wx/exchangecode")) {
			mv.addObject("unionid", userInfo.getUnionid());
			mv.addObject("cardcode", cardcode);
		}
		mv.setViewName("redirect:" + ConstantsUrl.redirect_url);
		return mv;
	}

	@RequestMapping(value = "/check", method = { RequestMethod.POST, RequestMethod.GET })
	public void checkWechat(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信首页验证 采用GET方式
		// 随机字符串
		String echostr = request.getParameter("echostr");
		if (echostr != null) {
			PrintWriter out = null;
			try {
				out = response.getWriter();
				// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，否则接入失败
				if (SignService.checkSignature(ConstantsService.TOKEN, request)) {
					out.print(echostr);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				out.close();
				out = null;
			}
		} else {
			// 微信接收与发送消息 采用POST方式
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
	public static String processWebchatRequest(HttpServletRequest request) {
	    String respMessage = null;
	    try {
	        // xml请求解析
	        Map<String, String> requestMap = MessageUtil.parseXml(request);
	        // 消息类型
	        String msgType = requestMap.get("MsgType");
	        //用户openid
	        String formUserName = requestMap.get("FromUserName");

	        TextMessage textMessage = (TextMessage) MessageService
	                .bulidBaseMessage(requestMap,
	                        ConstantsWeChat.RESP_MESSAGE_TYPE_TEXT);
	        NewsMessage newsMessage = (NewsMessage) MessageService
	                .bulidBaseMessage(requestMap,
	                        ConstantsWeChat.RESP_MESSAGE_TYPE_NEWS);

	        String respContent = "";
	        // 文本消息
	        if (msgType.equals(ConstantsWeChat.REQ_MESSAGE_TYPE_TEXT)) {
	            // 接收用户发送的文本消息内容
	            String content = requestMap.get("Content");

	            List<Article> articleList = new ArrayList<Article>();
	           	            
	        } else if (msgType.equals(ConstantsWeChat.REQ_MESSAGE_TYPE_EVENT)) {
	            // 事件类型
	            String eventType = requestMap.get("Event");

	            if (eventType.equals(ConstantsWeChat.EVENT_TYPE_SUBSCRIBE)) {
	            	
	                // 关注,需要将关注者的信息录入到数据库
	            	
	                respContent = "亲，欢迎关注添米财富我们是一家专注于安全理财的互联网金融平台!\n虽然我们只获得两家知名风投的数百万天使投资;虽然我们为保障用户利益和汇付天下达成战略合作;虽然我们有着强大的金融背景和银行级风控措施;但是我们一直在努力 /:,@f注册/绑定账户，请点 <a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd3634a6e86554592&redirect_uri=http://test.91tianmi.com/tm_web/menu_redirect&response_type=code&scope=snsapi_base&state=/wxs/bind#wechat_redirect'>开通/绑定账户</a>查询红包，请点 <a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd3634a6e86554592&redirect_uri=http://test.91tianmi.com/tm_web/menu_redirect&response_type=code&scope=snsapi_base&state=/wx/profile#wechat_redirect'>账户总览</a>\n添米财富活动多多，快绑账户哦！了解我们请点击网站www.91tianmi.com";
	            } else if (eventType
	                    .equals(ConstantsWeChat.EVENT_TYPE_UNSUBSCRIBE)) {
	            	
	            	//需要将取消关注者的信息从数据库删除
	            	
	            } else if (eventType.equals(ConstantsWeChat.EVENT_TYPE_CLICK)) {
	                // 事件KEY值，与创建自定义菜单时指定的KEY值对应
	                String eventKey = requestMap.get("EventKey");
	                // 自定义菜单点击事件
	                if (eventKey.equals("choujiang")) {
	                } else if (eventKey.equals("lingqu")) {
	                }
	            }
	            textMessage.setContent(respContent);
	            respMessage = MessageService.bulidSendMessage(textMessage,
	                    ConstantsWeChat.RESP_MESSAGE_TYPE_TEXT);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return respMessage;
	}

	public String menuSetting(String filePath) {
		return WeixinUtil.ReadFile(filePath);
	}

}
