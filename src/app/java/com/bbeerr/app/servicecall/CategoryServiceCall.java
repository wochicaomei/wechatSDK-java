package com.bbeerr.app.servicecall;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbeerr.app.service.ICategoryService;
import com.bbeerr.base.Page;
import com.bbeerr.core.service.ServiceCall;

@Controller
public class CategoryServiceCall {

	@Autowired
	ICategoryService service;

	@RequestMapping(value = "/data/category/list/{currentPage}", method = RequestMethod.POST)
	public @ResponseBody ServiceCall listCategory(@RequestParam(value = "ref", required = false, defaultValue = "{}") String ref,
			@RequestParam(value = "data", required = false, defaultValue = "{}") String data, @PathVariable("currentPage") int currentPage) {

		ServiceCall serviceCall = new ServiceCall(ref, data);
		JSONObject jsonData = JSONObject.fromObject(data);

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

}