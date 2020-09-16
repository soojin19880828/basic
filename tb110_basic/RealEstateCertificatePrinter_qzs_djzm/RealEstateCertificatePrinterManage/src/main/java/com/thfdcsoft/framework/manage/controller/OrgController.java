package com.thfdcsoft.framework.manage.controller;

import java.util.List;

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
import com.thfdcsoft.framework.manage.dao.OrgAgentInfoMapper;
import com.thfdcsoft.framework.manage.dao.OrgInfoMapper;
import com.thfdcsoft.framework.manage.dto.DataTableView;
import com.thfdcsoft.framework.manage.dto.Page;
import com.thfdcsoft.framework.manage.entity.OrgAgentInfo;
import com.thfdcsoft.framework.manage.entity.OrgAgentInfoExample;
import com.thfdcsoft.framework.manage.entity.OrgInfo;
import com.thfdcsoft.framework.manage.entity.OrgInfoExample;
import com.thfdcsoft.framework.manage.util.AutoIdFactory;

/**
 * 部门管理页面控制器
 * 
 * @author 张嘉琪
 * @date 2019年1月23日
 */
@Controller
@RequestMapping("/org")
public class OrgController extends BaseController {

	@Autowired
	private OrgInfoMapper orgInfoMapper;

	@Autowired
	private OrgAgentInfoMapper orgAgentInfoMapper;

	/**
	 * 跳转至机构管理页面
	 * 
	 * @author 张嘉琪
	 * @date 2019年1月23日
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "modules/org/org/orgUI";
	}

	/**
	 * 查询机构信息
	 * 
	 * @author 张嘉琪
	 * @date 2019年1月23日
	 * @param request
	 * @param response
	 * @param page
	 * @param sDepartmentName
	 * @return
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response, Page page, String sOrgCode,
			String sOrgName) {
		OrgInfoExample example = new OrgInfoExample();
		OrgInfoExample.Criteria criteria = example.createCriteria();
		if ((sOrgCode != null) && (!sOrgCode.isEmpty())) {
			criteria.andOrgCodeLike("%" + sOrgCode + "%");
		}
		if ((sOrgName != null) && (!sOrgName.isEmpty())) {
			criteria.andOrgNameLike("%" + sOrgName + "%");
		}
		PageList<OrgInfo> orgs = this.orgInfoMapper.selectByExample(example, this.getPageBounds(page));
		Long count = this.orgInfoMapper.countByExample(example);
		DataTableView<OrgInfo> view = new DataTableView<OrgInfo>();
		view.setData(orgs);
		view.setRecordsTotal(count.intValue());
		view.setRecordsFiltered(count.intValue());
		System.out.println(JacksonFactory.writeJson(view));
		return JacksonFactory.writeJson(view);
	}

	/**
	 * 提交机构信息
	 * 
	 * @author 张嘉琪
	 * @date 2019年1月23日
	 * @param request
	 * @param response
	 * @param department
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public void submit(HttpServletRequest request, HttpServletResponse response, OrgInfo org) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		if (StringUtils.isNullOrEmpty(org.getOrgId())) {
			org.setOrgId(AutoIdFactory.getAutoId());
			int r = this.orgInfoMapper.insertSelective(org);
			if (r > 0) {
				result.setResult(true);
				result.setRespObj(org);
				result.setRespMsg("新建机构成功，可修改后重复提交");
			} else {
				result.setResult(false);
				result.setRespMsg("新建机构失败，可修改后再次提交");
			}
		} else {
			int r = this.orgInfoMapper.updateByPrimaryKeySelective(org);
			if (r > 0) {
				result.setResult(true);
				result.setRespObj(org);
				result.setRespMsg("编辑机构成功，可修改后重复提交");
			} else {
				result.setResult(false);
				result.setRespMsg("编辑机构失败，可修改后再次提交");
			}
		}
		writeJSONResult(result, response);
	}

	/**
	 * 删除部门
	 * 
	 * @author 张嘉琪
	 * @date 2019年1月23日
	 * @param request
	 * @param response
	 * @param departmentId
	 */
	@RequestMapping(value = "/cancle", method = RequestMethod.POST)
	public void cancle(HttpServletRequest request, HttpServletResponse response, String orgId) {
		BaseHttpRspn result = new BaseHttpRspn(false);
		OrgAgentInfoExample example = new OrgAgentInfoExample();
		OrgAgentInfoExample.Criteria criteria = example.createCriteria();
		criteria.andOrgIdEqualTo(orgId);
		List<OrgAgentInfo> agents = this.orgAgentInfoMapper.selectByExample(example);
		if (agents.isEmpty()) {
			int r = this.orgInfoMapper.deleteByPrimaryKey(orgId);
			if (r > 0) {
				result.setResult(true);
				result.setRespMsg("删除机构成功");
			} else {
				result.setRespMsg("删除机构失败");
			}
		} else {
			result.setRespMsg("该机构下有代理人存在，不可执行删除操作");
		}
		writeJSONResult(result, response);
	}

	/**
	 * 获取机构列表
	 * 
	 * @author 张嘉琪
	 * @date 2019年1月23日
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getOrgList", method = RequestMethod.POST)
	public void getOrgList(HttpServletRequest request, HttpServletResponse response) {
		List<OrgInfo> orgs = this.orgInfoMapper.selectByExample(new OrgInfoExample());
		writeJSONResult(new BaseHttpRspn(!orgs.isEmpty(), orgs), response);
	}
}
