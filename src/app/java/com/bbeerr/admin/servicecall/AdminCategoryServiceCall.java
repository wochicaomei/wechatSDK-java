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

import com.bbeerr.app.form.query.CategoryQueryForm;
import com.bbeerr.app.service.ICategoryService;
import com.bbeerr.base.Page;
import com.bbeerr.core.service.ServiceCall;

@Controller
@RequestMapping(value = "/admin")
public class AdminCategoryServiceCall {

	@Autowired
	ICategoryService service;

	@RequestMapping(value = "/data/category/list/{currentPage}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall listCategory(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data, @ModelAttribute("queryForm") CategoryQueryForm queryForm, @PathVariable("currentPage") int currentPage) {
		ServiceCall serviceCall = new ServiceCall(ref, data);
		JSONObject jsonData = JSONObject.fromObject(data);

		JSONObject $criteria = JSONObject.fromObject(queryForm);
		jsonData.put("$criteria", $criteria);

		if (currentPage != 0) {
			jsonData.put("$page", JSONObject.fromObject("{current:" + currentPage + "}"));
			Page page = service.countCategory(jsonData);
			serviceCall.setPage(page);
		}

		Object retVal = service.listCategory(jsonData);
		serviceCall.setData(retVal);

		serviceCall.setMessage("success");
		return serviceCall;
	}

	@RequestMapping(value = "/data/category/create", method = RequestMethod.POST)
	public @ResponseBody ServiceCall createCategory(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.createCategory(JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/category/update/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall updateCategory(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.updateCategory(id, JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/category/find/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall findCategory(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.findCategory(id);
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/category/delete/{id}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall deleteCategory(@PathVariable("id") int id, @RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.deleteCategory(id, JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

	@RequestMapping(value = "/data/category/change", method = RequestMethod.POST)
	public @ResponseBody ServiceCall changeCategory(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data) {
		ServiceCall serviceCall = new ServiceCall(ref, data);

		try {
			Object retVal = service.changeCategory(JSONObject.fromObject(data));
			serviceCall.setData(retVal);
			serviceCall.setMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			serviceCall.setMessage("error");
		}

		return serviceCall;
	}

}