package com.thfdcsoft.framework.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thfdcsoft.framework.manage.controller.BaseController;
import com.thfdcsoft.framework.manage.dao.HelpMapper;
import com.thfdcsoft.framework.manage.dto.Page;
import com.thfdcsoft.framework.manage.entity.Help;
import com.thfdcsoft.framework.manage.entity.HelpExample;
import com.thfdcsoft.framework.manage.entity.HelpExample.Criteria;
import com.thfdcsoft.framework.manage.service.HelpService;
import com.thfdcsoft.framework.manage.util.AutoIdConfig;
import com.thfdcsoft.framework.manage.util.AutoIdFactory;

@Service
@Transactional
public class HelpServiceImpl extends BaseController implements HelpService  {
	
	@Autowired
	private HelpMapper helpMapper;

	@Override
	public List<Help> findDataByHelpTitle(String helpTitle) {
		HelpExample example = new HelpExample();
		Criteria criteria = example.createCriteria();
		criteria.andHelpTitleEqualTo(helpTitle);
		example.setOrderByClause("HELP_ID asc");//desc 降序 asc升序
		List<Help> helpList = helpMapper.selectByExample(example);
		return helpList;
	}

	@Override
	public List<Help> selectByHelpPage(Page page) {
		HelpExample example = new HelpExample();
		Criteria criteria = example.createCriteria();
		
		criteria.andHelpTitleEqualTo(page.getTitle());
		example.setOrderByClause("HELP_ID asc");
		
		List<Help> helpList = helpMapper.selectByExample(example, this.getPageBounds(page));
		return helpList;
	}

	@Override
	public int updateHelp(Help help) {
		int i = helpMapper.updateByPrimaryKey(help);
		return i;
	}

	@Override
	public int increaseHelp(Help help) {
		help.setRecordId(AutoIdFactory.getAutoId());
		int i = helpMapper.insert(help);
		return i;
	}

	@Override
	public int cancleHelp(Help help) {
		int i = helpMapper.deleteByPrimaryKey(help.getRecordId());
		return i;
	}

	@Override
	public Long selectByHelpTitle(String title) {
		HelpExample example = new HelpExample();
		Criteria criteria = example.createCriteria();
		criteria.andHelpTitleEqualTo(title);
		example.setOrderByClause("HELP_ID asc");
		long i = helpMapper.countByExample(example);
		return i;
	}

	
	
}
