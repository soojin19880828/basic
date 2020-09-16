package com.thfdcsoft.framework.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thfdcsoft.framework.business.entity.BankUserAll;


public interface BankUserAllMapper {

//	public List<BankUserAll> selectByBankUserAll(String name,String idNumber);
	public BankUserAll selectByBankUserAll(@Param("name") String name,@Param("idNumber") String idNumber);
	
}