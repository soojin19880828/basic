package com.thfdcsoft.framework.manage.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thfdcsoft.framework.manage.dao.PrintRecordMapper;
import com.thfdcsoft.framework.manage.dto.lankao.UsageAndPrintRecordReq;
import com.thfdcsoft.framework.manage.entity.PrintRecordExample;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.thfdcsoft.framework.common.http.dto.BaseHttpRspn;
import com.thfdcsoft.framework.common.json.jackson.JacksonFactory;
import com.thfdcsoft.framework.manage.dao.UsageRecordMapper;
import com.thfdcsoft.framework.manage.dto.DataTableView;
import com.thfdcsoft.framework.manage.dto.Page;
import com.thfdcsoft.framework.manage.entity.PrintRecord;
import com.thfdcsoft.framework.manage.entity.UsageRecord;
import com.thfdcsoft.framework.manage.entity.UsageRecordExample;
import com.thfdcsoft.framework.manage.entity.UsageRecordExample.Criteria;

@Controller
@RequestMapping("/usage")
public class UsageController extends BaseController {
	
	@Autowired
	private UsageRecordMapper usageRecordMapper;

	@Autowired
	private PrintRecordMapper printRecordMapper;

	/**
	 * 跳转至使用记录页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "modules/terminals/usage/usageUI";
	}
	
	/**
	 * 查询使用记录信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces="text/json;charset=UTF-8")
	@ResponseBody
	public String query(HttpServletRequest request, HttpServletResponse response, Page page) {
		UsageRecordExample example = new UsageRecordExample();
		example.setOrderByClause("USAGE_TIME desc");
		PageList<UsageRecord> records = this.usageRecordMapper.selectByExample(example, this.getPageBounds(page));
		Long count = this.usageRecordMapper.countByExample(example);
		DataTableView<UsageRecord> view = new DataTableView<UsageRecord>();
		view.setData(records);
		view.setRecordsTotal(count.intValue());
		view.setRecordsFiltered(count.intValue());
		return JacksonFactory.writeJson(view);
	}
	
	/**
	 * 通过身份证查询使用记录
	 *
	 * @param request
	 * @param response
	 * @param idNumber
	 * @return
	 */
	@RequestMapping(value = "/selectByIdnumber")
	@ResponseBody
	public String queryRecord(HttpServletRequest request, HttpServletResponse response, @RequestBody String idNumber) {
		BaseHttpRspn result = new BaseHttpRspn();
		result.setResult(false);
		
		UsageRecordExample example = new UsageRecordExample();
		Criteria criteria = example.createCriteria();
		Criteria criteria2 = criteria.andUserIdnumberEqualTo(idNumber);
		List<UsageRecord> usageRecords = this.usageRecordMapper.selectByExample(example);
		if(!usageRecords.isEmpty()) {
			result.setResult(true);
			result.setRespJson(JacksonFactory.writeJson(usageRecords));
		}
		return JacksonFactory.writeJson(result);
	}

	/**
	 * 通过业务编号查询使用记录和打印记录
	 *
	 * @param request
	 * @param response
	 * @param bizNumber
	 * @return
	 */
	@RequestMapping(value = "/usageRecordAndPrintRecordByBizNumber")
	@ResponseBody
	public String usageRecordAndPrintRecordByBizNumber(HttpServletRequest request, HttpServletResponse response, @RequestBody String bizNumber) {
		BaseHttpRspn result = new BaseHttpRspn();
		result.setResult(false);
		// 查询打印记录
		PrintRecordExample printExample = new PrintRecordExample();
		PrintRecordExample.Criteria printCriteria = printExample.createCriteria();
		printCriteria.andBizNumberEqualTo(bizNumber);
		List<PrintRecord> printRecordList = this.printRecordMapper.selectByExample(printExample);
		PrintRecord printRecord = printRecordList.get(0);
		// 通过打印记录查询使用记录
		UsageRecord usageRecord = this.usageRecordMapper.selectByPrimaryKey(printRecord.getUsageId());
		if(printRecord != null && usageRecord!= null) {
			UsageAndPrintRecordReq upReq = new UsageAndPrintRecordReq();
			upReq.setPrintRecord(printRecord);
			upReq.setUsageRecord(usageRecord);
			result.setResult(true);
			result.setRespJson(JacksonFactory.writeJson(upReq));
		}
		return JacksonFactory.writeJson(result);
	}

	@RequestMapping(value = "/getPic", method = RequestMethod.GET)
	@ResponseBody
	public void getPic(HttpServletRequest request, HttpServletResponse response, @Param("picPath") String picPath) {
		try {
			BufferedImage img = new BufferedImage(100, 120, BufferedImage.TYPE_INT_RGB);
			img = ImageIO.read(new FileInputStream(picPath));
			if (img != null) {
				ImageIO.write(img, "JPEG", response.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getLog", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getLog(HttpServletRequest request, HttpServletResponse response, @Param("logPath") String logPath){
		File file = new File(logPath);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", file.getName());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        try {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
			        headers, HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
