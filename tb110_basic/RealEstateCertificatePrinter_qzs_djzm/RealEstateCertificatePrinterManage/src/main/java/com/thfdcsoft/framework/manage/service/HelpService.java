package com.thfdcsoft.framework.manage.service;

import java.util.List;

import com.thfdcsoft.framework.manage.dto.Page;
import com.thfdcsoft.framework.manage.entity.Help;

public interface HelpService {

	List<Help> findDataByHelpTitle(String helpTitle);

	List<Help> selectByHelpPage(Page page);

	int updateHelp(Help help);

	int increaseHelp(Help help);

	int cancleHelp(Help help);

	Long selectByHelpTitle(String title);

	

}
