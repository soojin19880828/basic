package com.thfdcsoft.framework.business.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thfdcsoft.framework.business.dto.HelpJspRspn;
import com.thfdcsoft.framework.business.entity.Help;
import com.thfdcsoft.framework.business.service.ManageService;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;

/**
 * 办事指南 等controller
 *
 * @author Zenith
 * @date 2019/7/4 09:58
 */
@Controller
@RequestMapping(value = "/help")
public class HelpController extends BaseController {
	
	@Autowired
    private ManageService manageService;
	
	/**
	 * 请求管理系统获取办事指南等内容
	 * 
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/detail")
    public String choice(HttpServletRequest request) {
        String helpType = request.getParameter("helpType");
        System.out.println("helpType: " + helpType);
        String helpTitle = "";
        if("bszn".equals(helpType)) {
        	helpTitle = "办事指南";
        } else if ("flfg".equals(helpType)){
        	helpTitle = "法律法规";
        } else if ("sfbz".equals(helpType)){
        	helpTitle = "收费标准";
        }
        
        BaseHttpRspn rspn = manageService.post2ManageAndTrans("help/search", helpType, BaseHttpRspn.class);
        String helpJson = "";
        if(rspn.isResult()) {
        	 helpJson = rspn.getRespJson();
        } else {
        	// 无记录
        	helpJson = "[{\"recordId\":\"20190705174953000005\",\"helpId\":\"1\",\"helpTitle\":\"sfbz\",\"helpContent\":\"无记录\"}]";
        }
        System.out.println("helpJson: " + helpJson);
        List<Help> helpList0 = JacksonFactory.readJsonList(helpJson, Help.class);
        List<Help> helpList = new ArrayList<>();	// 要处理返回的编号
        for(int i=0; i<helpList0.size(); i++) {
        	Help help = new Help();
        	help.setHelpId(i+1+"");
        	help.setHelpContent(helpList0.get(i).getHelpContent());
        	helpList.add(help);
        }    
        
        request.getSession().setAttribute("title", helpTitle);
        request.getSession().setAttribute("helpList", helpList);
        return "help";
    }

}
