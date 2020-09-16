package com.thfdcsoft.framework.manage.dao;

import java.util.List;

import com.thfdcsoft.framework.manage.entity.QueryTerminals;

/**
 * 终端机信息查数据持久层查询接口
 * 
 * @author 张嘉琪
 * @date 2017年11月24日上午9:27:12
 */
public interface QueryTerminalsMapper {

	public List<QueryTerminals> selectAll();
}
