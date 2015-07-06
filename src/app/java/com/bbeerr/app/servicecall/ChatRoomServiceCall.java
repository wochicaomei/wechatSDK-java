package com.bbeerr.app.servicecall;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbeerr.app.form.query.ChatRoomQueryForm;
import com.bbeerr.app.service.IChatRoomService;
import com.bbeerr.base.Page;
import com.bbeerr.core.service.ServiceCall;

@Controller
public class ChatRoomServiceCall {

	@Autowired
	IChatRoomService service;

	@RequestMapping(value = "data/chatRoom/list/{currentPage}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall listChatRoom(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data, @ModelAttribute("queryForm") ChatRoomQueryForm queryForm, @PathVariable("currentPage") int currentPage) {

		ServiceCall serviceCall = new ServiceCall(ref, data);
		JSONObject jsonData = JSONObject.fromObject(data);

		JSONObject $criteria = JSONObject.fromObject("queryForm");
		jsonData.put("$criteria", $criteria);

		if (currentPage != 0) {
			jsonData.put("$page", JSONObject.fromObject("{current:" + currentPage + "}"));
			Page page = service.countChatRoom(jsonData);
			serviceCall.setPage(page);
		}

		Object refVal = service.listChatRoom(jsonData);
		serviceCall.setData(refVal);

		serviceCall.setMessage("success");
		return serviceCall;
	}

	@RequestMapping(value = "/data/chatRoom/find/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall findVideo(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.findChatRoom(id);
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

}