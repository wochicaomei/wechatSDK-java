package com.bbeerr.admin.servicecall;

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
@RequestMapping(value = "/admin")
public class AdminChatRoomServiceCall {

	@Autowired
	IChatRoomService service;

	@RequestMapping(value = "/data/chatroom/list/{currentPage}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall listChatRoom(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data, @ModelAttribute("queryForm") ChatRoomQueryForm queryForm, @PathVariable("currentPage") int currentPage) {
		ServiceCall serviceCall = new ServiceCall(ref, data);
		JSONObject jsonData = JSONObject.fromObject(data);

		JSONObject $criteria = JSONObject.fromObject(queryForm);
		jsonData.put("$criteria", $criteria);

		if (currentPage != 0) {
			jsonData.put("$page", JSONObject.fromObject("{current:" + currentPage + "}"));
			Page page = service.countChatRoom(jsonData);
			serviceCall.setPage(page);
		}

		Object retVal = service.listChatRoom(jsonData);
		serviceCall.setData(retVal);

		serviceCall.setMessage("success");
		return serviceCall;
	}

	@RequestMapping(value = "/data/chatroom/create", method = RequestMethod.POST)
	public @ResponseBody ServiceCall createChatRoom(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object refVal = service.createChatRoom(JSONObject.fromObject(data));
			serviceCall.setData(refVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/chatroom/update/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall updateVideo(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object refVal = service.updateChatRoom(id, JSONObject.fromObject(data));
			serviceCall.setData(refVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/chatroom/find/{id}", method = RequestMethod.POST)
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

	@RequestMapping(value = "/data/chatroom/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall deleteVideo(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.deleteChatRoom(id, JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/chatroom/change", method = RequestMethod.POST)
	public @ResponseBody ServiceCall changeChatRoom(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.changeChatRoom(JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

}