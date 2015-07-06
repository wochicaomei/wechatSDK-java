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

import com.bbeerr.app.form.query.VideoQueryForm;
import com.bbeerr.app.service.IVideoService;
import com.bbeerr.base.Page;
import com.bbeerr.core.service.ServiceCall;

@Controller
@RequestMapping(value = "/admin")
public class AdminVideoServiceCall {

	@Autowired
	IVideoService service;

	@RequestMapping(value = "/data/video/list/{currentPage}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall listVideo(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data, @ModelAttribute("queryForm") VideoQueryForm queryForm, @PathVariable("currentPage") int currentPage) {
		ServiceCall serviceCall = new ServiceCall(ref, data);
		JSONObject jsonData = JSONObject.fromObject(data);

		JSONObject $criteria = JSONObject.fromObject(queryForm);
		jsonData.put("$criteria", $criteria);

		if (currentPage != 0) {
			jsonData.put("$page", JSONObject.fromObject("{current:" + currentPage + "}"));
			Page page = service.countVideo(jsonData);
			serviceCall.setPage(page);
		}

		Object retVal = service.listVideo(jsonData);
		serviceCall.setData(retVal);

		serviceCall.setMessage("success");
		return serviceCall;
	}

	@RequestMapping(value = "/data/video/create", method = RequestMethod.POST)
	public @ResponseBody ServiceCall createVideo(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.createVideo(JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/video/update/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall updateVideo(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.updateVideo(id, JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/video/find/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall findVideo(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.findVideo(id);
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/video/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall deleteVideo(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.deleteVideo(id, JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/video/change", method = RequestMethod.POST)
	public @ResponseBody ServiceCall changeVideo(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.changeVideo(JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

}