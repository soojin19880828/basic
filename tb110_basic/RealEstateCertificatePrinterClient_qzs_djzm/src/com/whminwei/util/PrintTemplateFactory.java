package com.whminwei.util;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.whminwei.dto.EstateInfo;
import com.whminwei.dto.PrintProp;
import com.whminwei.dto.PrintValue;
import com.whminwei.dto.TextStruct;

public class PrintTemplateFactory {

	// List在进行Add操作时会出现异常，未发现明显原因，暂时弃用
	// List<PrintValue>与List<TextStruct>也会出现相同问题
	// 与JDK版本无关，与内容无关
	// private List<PrintProp> printTemplate;

	private PrintProp[] printTemplates;

	public void buildTemplate() {
		System.out.println("正在获取打印模板……");
		// 读取模板配置文件
		String filePath = PropertyFactory.getPath() + "template.xml";
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(filePath);
			// 获取默认配置
			NodeList templates = document.getElementsByTagName("Template");
			Node template = templates.item(0);
			String defFontFamily = null;
			String defFontSize = null;
			String defMaxWidth = null;
			String defFontWeight = null;
			PrintProp[] tempArray = new PrintProp[template.getChildNodes().getLength()];
			int l = 0;
			for (int t = 0; t < template.getChildNodes().getLength(); t++) {
				Node node = template.getChildNodes().item(t);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if ("Default".equals(node.getNodeName())) {
						NodeList defProps = node.getChildNodes();
						for (int i = 0; i < defProps.getLength(); i++) {
							Node defProp = defProps.item(i);
							if (defProp.getNodeType() == Node.ELEMENT_NODE) {
								String nodeName = defProp.getNodeName();
								String nodeValue = defProp.getTextContent();
								if ("FontFamily".equals(nodeName)) {
									defFontFamily = nodeValue;
								}
								if ("FontSize".equals(nodeName)) {
									defFontSize = nodeValue;
								}
								if ("MaxWidth".equals(nodeName)) {
									defMaxWidth = nodeValue;
								}
								if ("FontWeight".equals(nodeName)) {
									defFontWeight = nodeValue;
								}
							}
						}
					} else {
						NodeList props = node.getChildNodes();
						PrintProp print = new PrintProp();
						for (int n = 0; n < props.getLength(); n++) {
							Node prop = props.item(n);
							if (prop.getNodeType() == Node.ELEMENT_NODE) {
								String nodeName = prop.getNodeName();
								String nodeValue = prop.getTextContent();
								if ("Key".equals(nodeName)) {
									print.setKey(nodeValue);
								}
								if ("X".equals(nodeName)) {
									print.setX(nodeValue);
								}
								if ("Y".equals(nodeName)) {
									print.setY(nodeValue);
								}
								if ("FontFamily".equals(nodeName)) {
									print.setFontFamily(nodeValue);
								}
								if ("FontSize".equals(nodeName)) {
									print.setFontSize(nodeValue);
								}
								if ("FontFamily".equals(nodeName)) {
									print.setFontFamily(nodeValue);
								}
								if ("MaxWidth".equals(nodeName)) {
									print.setMaxWidth(nodeValue);
								}
								if ("FontWeight".equals(nodeName)) {
									print.setFontWeight(nodeValue);
								}
							}
						}
						if (print.getFontFamily() == null) {
							print.setFontFamily(defFontFamily);
						}
						if (print.getFontSize() == null) {
							print.setFontSize(defFontSize);
						}
						if (print.getMaxWidth() == null) {
							print.setMaxWidth(defMaxWidth);
						}
						if (print.getFontWeight() == null) {
							print.setFontWeight(defFontWeight);
						}
						tempArray[t] = print;
						l++;
					}
				}
			}
			printTemplates = new PrintProp[l];
			int s = 0;
			for (PrintProp temp : tempArray) {
				if (temp != null) {
					printTemplates[s] = temp;
					s++;
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		System.out.println("获取打印模板成功！");
	}

	private TextStruct[] buildTextStruct(PrintValue[] printList) {
		System.out.println("正在组装打印数据模板……");
		TextStruct[] words = null;
		try {

			// List<TextStruct> words = new ArrayList<TextStruct>();
			int l = 0;
			for (PrintValue value : printList) {
				String[] values = value.getValue().split("\\r\\n");
				l += values.length;
			}
			words = new TextStruct[l];
			int s = 0;
			for (PrintValue value : printList) {
				// 控制换行
				String[] values = value.getValue().split("\\r\\n");
				int rows = 0;
				for (int i = 0; i < values.length; i++) {
					Float x = (Float.parseFloat(value.getProp().getX()) - 2.6f) / 3.5f * 100;
					Float y = (Float.parseFloat(value.getProp().getY())
							+ (Float.parseFloat(value.getProp().getFontSize()) * rows) - 2.1f) / 3.5f * 103;
					Integer fontSize = (int) (Float.parseFloat(value.getProp().getFontSize()) * 10 * 3.2f);
					Float maxWidth = Float.parseFloat(value.getProp().getMaxWidth()) / 3.5f * 100;
					TextStruct struct = new TextStruct(values[i], x, y, value.getProp().getFontFamily(), fontSize,
							maxWidth, Integer.parseInt(value.getProp().getFontWeight()));
					// 判断是否会自动换行
					if ((values[i].length() * fontSize) > maxWidth) {
						rows += (int) ((values[i].length() * fontSize) / maxWidth);
					}

					words[s] = struct;
					s++;
					rows++;
				}
			}
			System.out.println("打印数据模板组装成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return words;
	}

	public TextStruct[] buildPrintData(EstateInfo estate) {
		System.out.println("正在获取打印数据……");
		String certProvince = estate.getCertNumber().substring(0, 1);
		String certYear = estate.getCertNumber().substring(2, 6);
		String certCity = estate.getCertNumber().substring(7, 10);
		String certNumber = estate.getCertNumber().substring(16, estate.getCertNumber().length() - 1);
		// List<PrintValue> printList = new ArrayList<PrintValue>();
		System.out.println("正在获取打印数据55555……"+printTemplates.length);
		PrintValue[] printList = new PrintValue[printTemplates.length];
		for (int i = 0; i < printTemplates.length; i++) {
			PrintProp prop = printTemplates[i];
			PrintValue print = new PrintValue();
			print.setProp(prop);
			if ("cert-province".equals(prop.getKey())) {
				print.setValue(certProvince);
			} else if ("cert-year".equals(prop.getKey())) {
				print.setValue(certYear);
			} else if ("cert-city".equals(prop.getKey())) {
				print.setValue(certCity);
			} else if ("cert-number".equals(prop.getKey())) {
				print.setValue(certNumber);
			} else if ("busi-type".equals(prop.getKey())) {
				print.setValue(estate.getBusiType());
			} else if ("obligee".equals(prop.getKey())) {
				print.setValue(estate.getObligee());
			} else if ("obligor".equals(prop.getKey())) {
				print.setValue(estate.getObligor());
			} else if ("located".equals(prop.getKey())) {
				print.setValue(estate.getLocated());
			} else if ("unit-number".equals(prop.getKey())) {
				print.setValue(estate.getUnitNumber());
			} else if ("other-cases".equals(prop.getKey())) {

				if (estate.getOtherCases() != null) {
					print.setValue(estate.getOtherCases());
					System.out.println("我不等于空1");
				} else {
					print.setValue("  ");
					System.out.println("我等于空2");
				}
//				print.setValue(".");
			} else if ("notes".equals(prop.getKey())) {
				if (estate.getNotes() != null ) {
					print.setValue(estate.getNotes());
					System.out.println("我不等于空3");
				} else {
					print.setValue("  ");
					System.out.println("我等于空4");
				}
				System.out.println("我等于空5"+estate.getRegisterTime());
//				print.setValue(".");
			} else if ("year".equals(prop.getKey())) {
				 print.setValue(estate.getRegisterTime().substring(0, 4));
			//	print.setValue(estate.getYear());
			} else if ("month".equals(prop.getKey())) {
				 print.setValue(estate.getRegisterTime().substring(5, 7));
//				print.setValue(estate.getMonth());
			} else if ("day".equals(prop.getKey())) {
				 print.setValue(estate.getRegisterTime().substring(8, 10));
//				print.setValue(estate.getDay());
			}
			printList[i] = print;
		}
		System.out.println("打印数据获取成功！");
		return buildTextStruct(printList);
	}

	private static PrintTemplateFactory instance;

	public static PrintTemplateFactory getInstance() {
		if (instance == null) {
			instance = new PrintTemplateFactory();
			instance.buildTemplate();
		}
		return instance;
	}

}
