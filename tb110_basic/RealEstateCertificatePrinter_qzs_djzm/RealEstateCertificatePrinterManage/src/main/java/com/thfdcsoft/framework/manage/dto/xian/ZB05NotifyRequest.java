package com.thfdcsoft.framework.manage.dto.xian;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 打印结果通知接口请求对象<br>
 * 参见《登记证明自助打证机、打印接口（西安市）》
 * 
 * @author 张嘉琪
 * @date 2019年1月5日
 */
public class ZB05NotifyRequest {


	// 领证人姓名;必填
	private String fullName;

	// 领证人身份证号;必填
	private String idNumer;

	// 行政区编码
	@JsonProperty("XZQ")  
	private String xzq;

	// 文件流集合
	private List<File> fileList;

	// 证明信息集合
	private List<Cert> certList;

	public ZB05NotifyRequest() {

	}

	public ZB05NotifyRequest(String fullName,String idnumer,List<BaseZB05File> basefiles,List<Cert> certs) {
		this.setFullName(fullName);
		this.setIdNumer(idnumer);
		this.setXzq("610100");
		List<File> fileList = new LinkedList<File>();
		for (BaseZB05File baseFile : basefiles) {
			File file = new File(baseFile);
			fileList.add(file);
		}
		this.setFileList(fileList);
		List<Cert> certList = new LinkedList<Cert>();
		for(Cert c:certs){
			certList.add(c);
		}
		
		this.setCertList(certList);
	}
	
	public ZB05NotifyRequest(List<Cert> certs) {
		this.setXzq("610100");
		List<Cert> certList = new LinkedList<Cert>();
		for(Cert c:certs){
			certList.add(c);
		}
		
		this.setCertList(certList);
	}

	

	public ZB05NotifyRequest(String fullName, String idNumer, String xzq, List<File> fileList, List<Cert> certList) {
		this.setFullName(fullName);
		this.setIdNumer(idNumer);
		this.setXzq(xzq);
		this.setFileList(fileList);
		this.setCertList(certList);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	

	public String getIdNumer() {
		return idNumer;
	}

	public void setIdNumer(String idNumer) {
		this.idNumer = idNumer;
	}

	@JsonIgnore
	public String getXzq() {
		return xzq;
	}

	public void setXzq(String xzq) {
		this.xzq = xzq;
	}

	public List<File> getFileList() {
		return fileList;
	}

	public void setFileList(List<File> fileList) {
		this.fileList = fileList;
	}

	public List<Cert> getCertList() {
		return certList;
	}

	public void setCertList(List<Cert> certList) {
		this.certList = certList;
	}

}
