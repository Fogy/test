package com.vtu.web.util.gif;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageUtils {

    /**
     * 读入文件
     */
    public BufferedImage loadImageLocal(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param mini 贴图
     * @param scale 缩放比例
     * @return
     */
    public static BufferedImage modifyImageScale(BufferedImage mini,double scale) {
        int w = (int)Math.round(mini.getWidth()*scale);
        int h = (int)Math.round(mini.getHeight()*scale);
        //设置生成图片宽*高，色彩
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //创建画布
        Graphics2D g2 = bi.createGraphics();
        //设置图片透明  注********：只有png格式的图片才能设置背景透明，jpg设置图片颜色变的乱七八糟
        //bi = g2.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        //重新创建画布
        g2 = bi.createGraphics();
        //画图
        g2.drawImage(mini, 0,0,w,h, null);
        //关闭资源
        g2.dispose();
        return bi;
    }

    /**
     *
     * @param mini 贴图
     * @param ratio  旋转角度
     * @return
     */
    public BufferedImage modifyImageRatio(BufferedImage mini,int ratio) {
        int src_width = mini.getWidth();
        int src_height = mini.getHeight();
        //针对图片旋转重新计算图的宽*高
        Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
                src_width, src_height)), ratio);
        //设置生成图片的宽*高，色彩度
        BufferedImage res = new BufferedImage(rect_des.width, rect_des.height,BufferedImage.TYPE_INT_RGB);
        //创建画布
        Graphics2D g2 = res.createGraphics();
        res = g2.getDeviceConfiguration().createCompatibleImage(rect_des.width, rect_des.height, Transparency.TRANSLUCENT);
        g2 = res.createGraphics();
        //重新设定原点坐标
        g2.translate((rect_des.width - src_width) / 2,
                (rect_des.height - src_height) / 2);
        //执行图片旋转，rotate里包含了translate，并还原了原点坐标
        g2.rotate(Math.toRadians(ratio), src_width / 2, src_height / 2);
        g2.drawImage(mini, null, null);
        g2.dispose();
        return res;
    }

    /**
     * 旋转图像
     * @param src
     * @param angel
     * @return
     */
    private Rectangle CalcRotatedSize(Rectangle src, int angel) {
        if (angel >= 90) {
            if(angel / 90 % 2 == 1){
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

    /**
     * 旋转图像
     * @param newPath
     * @param newImg
     */
    public void writeImageLocal(String newPath, BufferedImage newImg) {
        if (newPath != null && newImg != null) {
            try {
                File outputfile = new File(newPath);
                ImageIO.write(newImg, "png", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }



}
