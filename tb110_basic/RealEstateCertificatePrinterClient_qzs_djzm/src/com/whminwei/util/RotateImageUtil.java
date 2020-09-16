package com.whminwei.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author ww
 * @date 2019/5/15 13:39
 */
public class RotateImageUtil {

    public static void main(String[] args) {
        File file = new File("C:/Users/Rejects/Desktop/img/1.jpg");
        try {
            BufferedImage src = ImageIO.read(file);
            //顺时针旋转90度
            BufferedImage des1 = RotateImageUtil.Rotate(src, 90);
            ImageIO.write(des1, "jpg", new File("C:/Users/Rejects/Desktop/img/1_90.jpg"));
            //顺时针旋转180度
            BufferedImage des2 = RotateImageUtil.Rotate(src, 180);
            ImageIO.write(des2, "jpg", new File("C:/Users/Rejects/Desktop/img/1_180.jpg"));
            //顺时针旋转270度
            BufferedImage des3 = RotateImageUtil.Rotate(src, 270);
            ImageIO.write(des3, "jpg", new File("C:/Users/Rejects/Desktop/img/1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 旋转270度覆盖原图片
     *
     * @param filePath 文件路径
     * @return
     */
    public static boolean Rotate90(String filePath){
        File file = new File(filePath);
        try {
            BufferedImage src = ImageIO.read(file);
            //顺时针旋转270度
            BufferedImage des3 = Rotate(src, 90);
            ImageIO.write(des3, "jpg", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 对图片进行旋转
     *
     * @param src   被旋转图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    static BufferedImage Rotate(Image src, int angel) {
        int src_width = src.getWidth(null);
        int src_height = src.getHeight(null);
        // 计算旋转后图片的尺寸
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), angel);
        BufferedImage res = null;
        res = new BufferedImage(rect_des.width, rect_des.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = res.createGraphics();
        // 进行转换
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

        g2.drawImage(src, null, null);
        return res;
    }

    /**
     * 计算旋转后的图片
     *
     * @param src   被旋转的图片
     * @param angel 旋转角度
     * @return 旋转后的图片
     */
    static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        // 如果旋转的角度大于90度做相应的转换
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel = angel % 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
        double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
        double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
        double angel_dalta_width = Math.atan((double) src.height / src.width);
        double angel_dalta_height = Math.atan((double) src.width / src.height);

        int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_width));
        int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
                - angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }

}
