package com.thfdcsoft.framework.manage.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.thfdcsoft.framework.manage.entity.PrintRecord;
import com.thfdcsoft.framework.manage.entity.PrintRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PrintRecordMapper {
    long countByExample(PrintRecordExample example);

    int deleteByExample(PrintRecordExample example);

    int deleteByPrimaryKey(String recordId);

    int insert(PrintRecord record);

    int insertSelective(PrintRecord record);

    List<PrintRecord> selectByExample(PrintRecordExample example);

    PrintRecord selectByPrimaryKey(String recordId);

    int updateByExampleSelective(@Param("record") PrintRecord record, @Param("example") PrintRecordExample example);

    int updateByExample(@Param("record") PrintRecord record, @Param("example") PrintRecordExample example);

    int updateByPrimaryKeySelective(PrintRecord record);

    int updateByPrimaryKey(PrintRecord record);

    PageList<PrintRecord> selectByExample(PrintRecordExample example, PageBounds pageBounds);

    List<PrintRecord> selectPrintRecordListByIdNumber(String idNumber);

    List<PrintRecord> getDjzmRecordsByCertNums(List<String> certNumList);

    List<PrintRecord> getQzsRecordsByCertNums(List<String> certNumList);

    List<PrintRecord> getRecordsBycetNums(List<String> certNumList);
}