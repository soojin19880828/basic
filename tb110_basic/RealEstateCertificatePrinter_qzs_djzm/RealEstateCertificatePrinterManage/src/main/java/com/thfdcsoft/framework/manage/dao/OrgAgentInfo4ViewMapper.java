package com.thfdcsoft.framework.manage.dao;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.thfdcsoft.framework.manage.entity.OrgAgentInfo4View;

public interface OrgAgentInfo4ViewMapper {

	long countByExample(Map<String, String> param);

	PageList<OrgAgentInfo4View> selectByExample(Map<String, String> param, PageBounds pageBounds);

}
