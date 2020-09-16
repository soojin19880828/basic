package com.thfdcsoft.framework.business.controller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import com.thfdcsoft.framework.business.contant.StaticConstant;
import com.thfdcsoft.framework.business.dto.ChkRemainingReq;
import com.thfdcsoft.framework.business.dto.SubmitRemainingReq;
import com.thfdcsoft.framework.common.http.def.DefHttpClientFactory;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;

@RestController
public class DeviceController {

	@RequestMapping(value = "/chkRemaining", method = RequestMethod.POST)
	@ResponseBody
	public String chkRemaining(HttpServletRequest request, HttpServletResponse response, @RequestBody ChkRemainingReq remain) {
		BaseHttpRspn rspnDto = new BaseHttpRspn();
		rspnDto.setResult(false);

		String url = StaticConstant.MANAGE_URL + "business/selectByDeployNumberAndTernimalId";
		String data = JacksonFactory.writeJson(remain);
		try {
			String rspnJson = DefHttpClientFactory.getInstance().doPost(data, url);
			BaseHttpRspn rspn = JacksonFactory.readJson(rspnJson, BaseHttpRspn.class);
			if(rspn.isResult()){
				rspnDto.setResult(true);
				rspnDto.setRespJson(rspn.getRespJson());
				return JacksonFactory.writeJson(rspnDto);
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return JacksonFactory.writeJson(rspnDto);
	}

	/**
	　　* @description: 获取指定名称的设备中证书、证明数量
	　　* @params
	　　* @return
	　　* @author 高拓
	　　* @date 2020/9/7 11:00
	　　*/
	@RequestMapping(value = "/getRemainingCount", method = RequestMethod.GET)
	@ResponseBody
	public String getRemainingCount(HttpServletRequest request) {
		String deviceNumber = request.getParameter("deviceNumber");
		BaseHttpRspn rspnDto = new BaseHttpRspn();
		String url = StaticConstant.MANAGE_URL + "business/listByDeployNumber";
		try {
			String rspnJson = DefHttpClientFactory.getInstance().doPost(deviceNumber, url);
			BaseHttpRspn rspn = JacksonFactory.readJson(rspnJson, BaseHttpRspn.class);
			if(rspn.isResult()) {
				rspnDto.setResult(true);
				rspnDto.setRespJson(rspn.getRespJson());
				return JacksonFactory.writeJson(rspnDto);
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return JacksonFactory.writeJson(rspnDto);
	}


	//修改数据库权证书数量
	@RequestMapping(value = "/submitRemaining", method = RequestMethod.POST)
	@ResponseBody
	public String submitRemaining(HttpServletRequest request, HttpServletResponse response,@RequestBody SubmitRemainingReq remain) {
	BaseHttpRspn result = new BaseHttpRspn(false);

		String url =StaticConstant.MANAGE_URL +"business/updateRemaining";       //跳到Manage
		String data = JacksonFactory.writeJson(remain);
		try {
			String rspnJson = DefHttpClientFactory.getInstance().doPost(data, url);
			BaseHttpRspn rspn = JacksonFactory.readJson(rspnJson, BaseHttpRspn.class);
			if(rspn.isResult()){
				result.setResult(true);
				return JacksonFactory.writeJson(result);
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return JacksonFactory.writeJson(result);

	}
}
