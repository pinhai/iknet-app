/**
 * @(#)ValidCode.java 1.0 2013-4-8
 * @Copyright:  Copyright 2000 - 2013 Isoftstone Tech. Co. Ltd. All Rights Reserved.
 * @Description: 
 * 
 * Modification History:
 * Date:        2013-4-8
 * Author:      zhangzhongguo 38101
 * Version:     MPRSP_CAPV1.D1.0.0.0
 * Description: (Initialize)
 * Reviewer:    
 * Review Date: 
 */
package com.iknet.commons.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 验证码生成类
 * Copyright:   Copyright 2000 - 2014 yiken Tech. Co. Ltd. All Rights Reserved.
 * Date:        2013-4-8
 * Author:      zhangzhongguo 000001
 * Version:     MPRSP_CAPV1.D1.0.0.0
 * Description: Initialize
 */
public class ValidCode
{
    public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";//放到session中的key
    
    private Random random = new Random();
    
    private String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生的字符串
    
    private int width = 80;//图片宽
    
    private int height = 30;//图片高
    
    private int lineSize = 50;//干扰线数量
    
    private int stringNum = 4;//随机产生字符数量
    
    /*
     * 获得字体
     */
    private Font getFont()
    {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 20);
    }
    
    /*
     * 获得颜色
     */
    private Color getRandColor(int fc, int bc)
    {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }
    
    /**
     * 生成随机图片
     */
    public String getValidPic(int width, int height, OutputStream os)
    {
        //BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image =
            new BufferedImage(this.width, height, BufferedImage.TYPE_INT_BGR);
        
        //产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, this.width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 20));
        g.setColor(getRandColor(110, 133));
        
        //绘制干扰线
        for (int i = 0; i <= lineSize; i++)
        {
            drowLine(g);
        }
        
        //绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++)
        {
            randomString = drowString(g, randomString, i);
        }
        g.dispose();
        try
        {
            //将内存中的图片通过流动形式输出到客户端
            ImageIO.setUseCache(false);
            ImageIO.write(image, "JPEG", os);
            
            // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            // encoder.encode(image); 
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return randomString;
    }
    
    /*
     * 绘制字符串
     */
    private String drowString(Graphics g, String randomString, int i)
    {
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111),
            random.nextInt(121)));
        String rand =
            String.valueOf(getRandomString(random.nextInt(randString.length())));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 15 * (i - 1) + 10, 14);
        return randomString;
    }
    
    /*
     * 绘制干扰线
     */
    private void drowLine(Graphics g)
    {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }
    
    /*
     * 获取随机的字符
     */
    public String getRandomString(int num)
    {
        return String.valueOf(randString.charAt(num));
    }
}
