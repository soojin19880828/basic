package com.thfdcsoft.framework.business.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController extends BaseController {

	@RequestMapping(value = "${ctxPath}", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "index";
	}
	
}
