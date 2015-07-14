package com.bbeerr.wechat.base.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.bbeerr.wechat.base.service.IMessageService;
import com.bbeerr.wechat.base.util.MessageUtil;
import com.bbeerr.wechat.constants.ConstantsWeChat;
import com.bbeerr.wechat.entity.message.resp.BaseMessage;
import com.bbeerr.wechat.entity.message.resp.MediaMessage;
import com.bbeerr.wechat.entity.message.resp.MusicMessage;
import com.bbeerr.wechat.entity.message.resp.NewsMessage;
import com.bbeerr.wechat.entity.message.resp.TextMessage;
import com.bbeerr.wechat.entity.message.resp.TransferMessage;
import com.bbeerr.wechat.entity.message.resp.VideoMessage;

/**
 * 消息处理
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
@Service
public class MessageServiceImpl implements IMessageService {

	public static Map<String, BaseMessage> bulidMessageMap = new HashMap<String, BaseMessage>();

	static {
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_TEXT, new TextMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_NEWS, new NewsMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_IMAGE, new MediaMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_VOICE, new MediaMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_VIDEO, new VideoMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_MUSIC, new MusicMessage());
		bulidMessageMap.put(ConstantsWeChat.RESP_MESSAGE_TYPE_TRANSFER_CUSTOMER, new TransferMessage());
	}

	/**
	 * 构建基本消息
	 * 
	 * @param requestMap
	 *            xml请求解析后的map
	 * @param msgType
	 *            消息类型
	 * @return BaseMessage 基本消息对象
	 */
	public Object bulidBaseMessage(Map<String, String> requestMap, String msgType) {
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");

		BaseMessage message = bulidMessageMap.get(msgType);
		message.setToUserName(fromUserName);
		message.setFromUserName(toUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType(msgType);
		message.setFuncFlag(0);
		return message;
	}

	/**
	 * 发送消息接口
	 * 
	 * @param obj
	 *            消息对象
	 * @param msgType
	 *            消息类型
	 * @return 返回xml格式数据给微信
	 */
	public String bulidSendMessage(Object obj, String msgType) {
		String responseMessage = "";

		// 图文消息处理
		if (msgType == ConstantsWeChat.RESP_MESSAGE_TYPE_NEWS) {
			responseMessage = MessageUtil.newsMessageToXml((NewsMessage) obj);
		} else {// 其他消息处理
			responseMessage = MessageUtil.messageToXml(obj);
		}
		return responseMessage;
	}

	/*
	 * 处理微信请求，包括消息，事件等
	 * 
	 * @superman
	 */
	@Override
	public String processWechatRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = (TextMessage) this.bulidBaseMessage(requestMap, ConstantsWeChat.RESP_MESSAGE_TYPE_TEXT);
			NewsMessage newsMessage = (NewsMessage) this.bulidBaseMessage(requestMap, ConstantsWeChat.RESP_MESSAGE_TYPE_NEWS);
			TransferMessage transferMessage = (TransferMessage) this.bulidBaseMessage(requestMap, ConstantsWeChat.RESP_MESSAGE_TYPE_TRANSFER_CUSTOMER);
			String respContent = "";
			// 文本消息
			if (msgType.equals(ConstantsWeChat.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				// 消息转发多客服系统示例
				// respMessage =
				// messageService.bulidSendMessage(transferMessage,
				// ConstantsWeChat.RESP_MESSAGE_TYPE_TRANSFER_CUSTOMER);

				/*
				 * 图文消息发送示例 List<Article> articleList = new
				 * ArrayList<Article>(); Article article = new Article();
				 * article.setTitle("快乐大转盘 好礼送不停");
				 * article.setDescription("转发朋友圈可获1次抽奖机会 每天最多转发3次");
				 * article.setPicUrl
				 * ("http://test.91tianmi.com/static/css/topper/weixin_head.png"
				 * ); article.setUrl(ConstantsUrl.wechat_event20150618_url);
				 * articleList.add(article); // 设置图文消息个数
				 * newsMessage.setArticleCount(articleList.size()); //
				 * 设置图文消息包含的图文集合 newsMessage.setArticles(articleList); //
				 * 将图文消息对象转换成xml字符串 respMessage =
				 * messageService.bulidSendMessage(newsMessage,
				 * ConstantsWeChat.RESP_MESSAGE_TYPE_NEWS);
				 */

			} else if (msgType.equals(ConstantsWeChat.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");

				if (eventType.equals(ConstantsWeChat.EVENT_TYPE_SUBSCRIBE)) {

					respContent = "亲，欢迎关注添米财富我们是一家专注于安全理财的互联网金融平台!\n虽然我们只获得两家知名风投的数百万天使投资;虽然我们为保障用户利益和汇付天下达成战略合作;虽然我们有着强大的金融背景和银行级风控措施;但是我们一直在努力 /:,@f注册/绑定账户，请点 <a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd3634a6e86554592&redirect_uri=http://test.91tianmi.com/tm_web/menu_redirect&response_type=code&scope=snsapi_base&state=/wxs/bind#wechat_redirect'>开通/绑定账户</a>查询红包，请点 <a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxd3634a6e86554592&redirect_uri=http://test.91tianmi.com/tm_web/menu_redirect&response_type=code&scope=snsapi_base&state=/wx/profile#wechat_redirect'>账户总览</a>\n添米财富活动多多，快绑账户哦！了解我们请点击网站www.91tianmi.com";
				} else if (eventType.equals(ConstantsWeChat.EVENT_TYPE_UNSUBSCRIBE)) {

					// 需要将取消关注者的信息从数据库删除

				} else if (eventType.equals(ConstantsWeChat.EVENT_TYPE_CLICK)) {
					// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					String eventKey = requestMap.get("EventKey");
					// 自定义菜单点击事件
					if (eventKey.equals("choujiang")) {
					} else if (eventKey.equals("lingqu")) {
					}
				}
				textMessage.setContent(respContent);
				respMessage = this.bulidSendMessage(textMessage, ConstantsWeChat.RESP_MESSAGE_TYPE_TEXT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

}
