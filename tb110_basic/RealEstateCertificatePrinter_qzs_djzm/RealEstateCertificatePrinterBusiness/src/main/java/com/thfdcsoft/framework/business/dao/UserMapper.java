package com.thfdcsoft.framework.business.dao;

import java.util.List;
import java.util.Map;

import com.thfdcsoft.framework.business.dto.UserReq;

public interface UserMapper {

	List<Map<String,Object>> selectAll();
	
	List<Map<String,Object>> select_jf(String sex);
	
	
}
