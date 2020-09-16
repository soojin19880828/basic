package com.whminwei.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class ImageFactory {

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param imgStr
	 *            base64编码字符串
	 * @param saveUrl
	 *            图片路径-具体到文件
	 * @return
	 */
	public static boolean generateImage(String imgStr, String saveUrl) {
		if (imgStr == null)
			return false;
		try {

			// 解密
			byte[] b = Base64.decodeBase64(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(saveUrl);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @author
	 * @功能 图片转base64
	 * @时间 2017年11月2日15:00:04
	 * @param imgFile
	 * @return
	 */
	public static String getImageStr(String imgFile) {
		InputStream inputStream = null;
		byte[] data = null;
		try {
			inputStream = new FileInputStream(imgFile);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 加密
		return Base64.encodeBase64String(data);
	}

	/**
	 * 缩放图片方法
	 * @param srcImageFile 要缩放的图片路径
	 * @param result 缩放后的图片路径
	 * @param height 目标高度像素
	 * @param width  目标宽度像素
	 * @param bb     是否补白
	 */
	public final static void scale(String srcImageFile, String result, int height, int width, boolean bb) {
		try {
			double ratio = 0.0; // 缩放比例
			File f = new File(srcImageFile);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);//bi.SCALE_SMOOTH  选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
			// 计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				double   ratioHeight = (new Integer(height)).doubleValue()/ bi.getHeight();
				double   ratioWhidth = (new Integer(width)).doubleValue()/ bi.getWidth();
				if(ratioHeight>ratioWhidth){
					ratio= ratioHeight;
				}else{
					ratio= ratioWhidth;
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform//仿射转换
						.getScaleInstance(ratio, ratio), null);//返回表示剪切变换的变换
				itemp = op.filter(bi, null);//转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
			}
			if (bb) {//补白
				BufferedImage image = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);//构造一个类型为预定义图像类型之一的 BufferedImage。
				Graphics2D g = image.createGraphics();//创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
				g.setColor(Color.white);//控制颜色
				g.fillRect(0, 0, width, height);// 使用 Graphics2D 上下文的设置，填充 Shape 的内部区域。
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
							itemp.getWidth(null), itemp.getHeight(null),
							Color.white, null);
				g.dispose();
				itemp = image;
			}
			ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));      //输出压缩图片
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 裁剪图片方法
	 * @param bufferedImage 图像源
	 * @param startX 裁剪开始x坐标
	 * @param startY 裁剪开始y坐标
	 * @param endX 裁剪结束x坐标
	 * @param endY 裁剪结束y坐标
	 * @return
	 */
	public static BufferedImage cropImage(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		if (startX == -1) {
			startX = 0;
		}
		if (startY == -1) {
			startY = 0;
		}
		if (endX == -1) {
			endX = width - 1;
		}
		if (endY == -1) {
			endY = height - 1;
		}
		BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {
				int rgb = bufferedImage.getRGB(x, y);
				result.setRGB(x - startX, y - startY, rgb);
			}
		}
		return result;
	}

	/**
	 * 裁剪图片方法(特定裁剪证书编号)
	 *
	 * @param sourcePath 源文件地址
	 * @param targetPath 目标文件地址
	 * @throws IOException
	 */
	public static boolean cropImage(String sourcePath, String targetPath) throws IOException {
		File newfile = new File(sourcePath);
		BufferedImage bufferedimage = ImageIO.read(newfile);
		int width = bufferedimage.getWidth();
		int height = bufferedimage.getHeight();
		bufferedimage = cropImage(bufferedimage, 200, 680, width - 240, height - 410);
		return ImageIO.write(bufferedimage, "jpg", new File(targetPath));    //输出裁剪图片
		
	}
	
	/**
	  * 
	  * @param srcPath 原图片路径 
	  * @param desPath  转换大小后图片路径 
	  * @param width   转换后图片宽度 
	  * @param height  转换后图片高度 
	  */
	 public static void resizeImage(String srcPath, String desPath,  
	         int width, int height) throws IOException {  

	     File srcFile = new File(srcPath);  
	     Image srcImg = ImageIO.read(srcFile);  
	     BufferedImage buffImg = null;  
	     buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	     //使用TYPE_INT_RGB修改的图片会变色 
	     buffImg.getGraphics().drawImage(  
	             srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,  
	             0, null);  

	     ImageIO.write(buffImg, "JPEG", new File(desPath));  
	 } 
}
