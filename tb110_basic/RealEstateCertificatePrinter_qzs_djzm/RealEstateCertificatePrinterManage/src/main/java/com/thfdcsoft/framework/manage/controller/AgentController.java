package com.thfdcsoft.framework.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.common.string.StringUtils;
import com.thfdcsoft.framework.manage.dao.OrgAgentInfo4ViewMapper;
import com.thfdcsoft.framework.manage.dao.OrgAgentInfoMapper;
import com.thfdcsoft.framework.manage.dto.DataTableView;
import com.thfdcsoft.framework.manage.dto.Page;
import com.thfdcsoft.framework.manage.entity.OrgAgentInfo;
import com.thfdcsoft.framework.manage.entity.OrgAgentInfo4View;
import com.thfdcsoft.framework.manage.util.AutoIdFactory;

@Controller
@RequestMapping("/agent")
public class AgentController extends BaseController {

	@Autowired
	private OrgAgentInfoMapper orgAgentInfoMapper;

	@Autowired
	private OrgAgentInfo4ViewMapper orgAgentInfo4ViewMapper;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "modules/org/agent/agentUI";
	}

	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response, Page page, String sOrgId,
			String sAgentIdNumber, String sAgentFullName) {
		Map<String, String> param = new HashMap<String, String>();
		if ((sOrgId != null) && (!sOrgId.isEmpty())) {
			param.put("orgId", sOrgId);
		}
		if ((sAgentIdNumber != null) && (!sAgentIdNumber.isEmpty())) {
			param.put("agentIdNumber", sAgentIdNumber);
		}
		if ((sAgentFullName != null) && (!sAgentFullName.isEmpty())) {
			param.put("agentFullName", sAgentFullName);
		}
		PageList<OrgAgentInfo4View> posts = this.orgAgentInfo4ViewMapper.selectByExample(param,
				this.getPageBounds(page));
		Long count = this.orgAgentInfo4ViewMapper.countByExample(param);
		DataTableView<OrgAgentInfo4View> view = new DataTableView<OrgAgentInfo4View>();
		view.setData(posts);
		view.setRecordsTotal(count.intValue());
		view.setRecordsFiltered(count.intValue());
		System.out.println(JacksonFactory.writeJson(view));
		return JacksonFactory.writeJson(view);
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public void submit(HttpServletRequest request, HttpServletResponse response, OrgAgentInfo agent) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		if (StringUtils.isNullOrEmpty(agent.getAgentId())) {
			agent.setAgentId(AutoIdFactory.getAutoId());
			int r = this.orgAgentInfoMapper.insertSelective(agent);
			if (r > 0) {
				result.setResult(true);
				result.setRespObj(agent);
				result.setRespMsg("新建代理人成功，可修改后重复提交");
			} else {
				result.setResult(false);
				result.setRespMsg("新建代理人失败，可修改后再次提交");
			}
		} else {
			int r = this.orgAgentInfoMapper.updateByPrimaryKeySelective(agent);
			if (r > 0) {
				result.setResult(true);
				result.setRespObj(agent);
				result.setRespMsg("编辑代理人成功，可修改后重复提交");
			} else {
				result.setResult(false);
				result.setRespMsg("编辑代理人失败，可修改后再次提交");
			}
		}
		writeJSONResult(result, response);
	}

	@RequestMapping(value = "/cancle", method = RequestMethod.POST)
	public void cancle(HttpServletRequest request, HttpServletResponse response, String agentId) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		int r = this.orgAgentInfoMapper.deleteByPrimaryKey(agentId);
		if (r > 0) {
			result.setResult(true);
			result.setRespMsg("删除代理人成功");
		}else {
			result.setRespMsg("删除代理人失败");
		}
		writeJSONResult(result, response);
	}
}
