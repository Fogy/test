package com.vtu.util;

import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 图片水印
 * @blog http://sjsky.iteye.com
 * @author Michael
 */
public class ImageCompress {

    /**
     * @param
     */
    @Test
    public  void test1() {
        String srcImgPath = "E:\\vtuweb\\test\\aa.gif";
        String iconPath = "E:\\vtuweb\\videoLog\\logo.png";
        String targerPath = "E:\\vtuweb\\test\\bb.gif";
        //String targerPath2 = "d:/test/michael/img_mark_icon_rotate.jpg";
        // 给图片添加水印
        ImageCompress.markImageByIcon(iconPath, srcImgPath, targerPath);
        // 给图片添加水印,水印旋转-45aa
        /*ImageMarkLogoByIcon.markImageByIcon(iconPath, srcImgPath, targerPath2,
                -45);*/

    }

    /**
     * 给图片添加水印、可设置水印图片旋转角度
     * @param iconPath 水印图片路径
     * @param srcImgPath 源图片路径
     * @param targerPath 目标图片路径
     */
    public static void markImageByIcon(String iconPath, String srcImgPath,
                                       String targerPath) {
        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            int ImageWidth = srcImg.getWidth(null);
            int imageHeight = srcImg.getHeight(null);
            double scale  = ImageWidth < imageHeight?ImageWidth/540:ImageWidth/540;
            int w = (int)Math.round(ImageWidth*scale);
            int h = (int)Math.round(imageHeight*scale);
           BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = buffImg.createGraphics();
            // 设置对线段的锯齿状边缘处理
           g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(ImageWidth, imageHeight, Image.SCALE_SMOOTH), 0, 0, null);
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度
            ImageIcon imgIcon = new ImageIcon(iconPath);

            // 得到Image对象。
            Image img = imgIcon.getImage();

            float alpha = 0.5f; // 透明度
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 表示水印图片的位置
            //g.drawImage(buffImg, 0,0,w,h, null);
            g.drawImage(img, 100, 100,266,100, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            g.dispose();
            os = new FileOutputStream(targerPath);
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);

            System.out.println("图片完成添加Icon印章。。。。。。");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
