package com.pp.tool;

import java.util.ArrayList;
import java.util.List;



public class testSealmachine {

	
	public static void main(String[] args) {
		
		//盖章机启动异常 return
		//NativeFunc.SetDllPath("D:\\dll"); // 载入硬件dll位置

		// 初始化硬件调用类
					
		//线程等待 3秒
		// 写入硬件读取地址
		//NativeFunc.SetDllPath("d:\\dll");
		
		SealMachine sealmachine = new SealMachine();//初始化盖章机函数 //初始化盖章机函数 端口号 x坐标 y坐标
	    String 	 resStr = sealmachine.StartSeal(8, 1200,750); //启动盖章机 并判断盖章机状态 
		 
		 if(!resStr.equals("success")){
			 //盖章机启动异常 
			 System.out.println("liu6"+resStr);
		 }
		
		 PrintText   tshshhs=new PrintText();
	     List<TextStruct> words =new ArrayList<TextStruct>();
	     
		 tshshhs.PrintTest("HP LaserJet Pro M402-M403 PCL 6", words);
		 
		 resStr = sealmachine.Sealing(); 
		 sealmachine.closeSeal();
		 if(!resStr.equals("success")){
			 System.out.println("liu6"); 
		 }
		
	}
	
	
	
	
	

}
