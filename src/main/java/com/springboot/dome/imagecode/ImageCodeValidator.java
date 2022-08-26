package com.springboot.dome.imagecode;


import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;

@Configuration
public class ImageCodeValidator {

    //验证码保存在session中的属性key
    public static final String SESSION_CODE_KEY = "IMAGE_VALIDATE_CODE";

    // 图片的宽度。
    private int width = 160;
    // 图片的高度。
    private int height = 40;
    // 验证码字符个数
    private int codeCount = 4;
    // 验证码干扰线数
    private int lineCount = 150;

    /*private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'c', 'd', 'e', 'k', 'n', 'r', 's', 't', 'v', 'w', 'u', 'v', 'f', 'h'};*/

   //private char[] codeSequence = {'2', '3', '4', '5', '6', '7', '8', '9', 'a', 'c', 'd', 'e', 'k', 'n', 'r', 's', 't', 'v', 'w', 'u', 'v', 'f', 'h'};
    private char[] codeSequence = {'1','2', '3', '4', '5', '6', '7', '8', '9'};

    public BufferedImage createSessionCode(HttpSession session) {
        int x = 0, fontHeight = 0, codeY = 0;
        int red = 0, green = 0, blue = 0;

        x = width / (codeCount + 2);// 每个字符的宽度
        fontHeight = height - 4;// 字体的高度
        codeY = height - 7;
        // 图像buffer
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        // 生成随机数
        Random random = new SecureRandom();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体
        ImgFontByte imgFont = new ImgFontByte();
        Font font = imgFont.getFont(fontHeight);
        g.setFont(font);

        color(g, random);
        Random rand = new SecureRandom();
        // randomCode记录随机产生的验证码
        StringBuffer randomCode = new StringBuffer();
        // 随机产生codeCount个字符的验证码。
        for (int i = 0; i < codeCount; i++) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
            red = random.nextInt(225);
            green = random.nextInt(225);
            blue = random.nextInt(225);
            g.setColor(new Color(red, green, blue));

            AffineTransform affine = new AffineTransform();
            int dx = (i + 1) * x;
            int dy = codeY;
            affine.rotate(Math.PI / 10 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), dx + (x / 2), dy / 2);
            g.setTransform(affine);

            g.drawString(strRand, dx, dy);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }

        color(g, random);

        // 将四位数字的验证码保存到Session中。
        String code = randomCode.toString();
        session.setAttribute(SESSION_CODE_KEY, code);
        return bi;
    }

    private void color(Graphics2D g, Random random) {
        int red;
        int green;
        int blue;
        for (int i = 0; i < lineCount / 2; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width / 8);
            int ye = ys + random.nextInt(height / 8);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }
    }

    public boolean verifySession(HttpSession session, String code) {
        if (null == session || null == code || "".equals(code)) {
            return false;
        }
        String codeInSession = (String) session.getAttribute(SESSION_CODE_KEY);
        boolean b = code.equalsIgnoreCase(codeInSession); //不区分大小写
        //boolean b = code.contains(codeInSession); // 区分大小写
        if (b) {
            session.removeAttribute(SESSION_CODE_KEY);
        }
        return b;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCodeCount() {
        return codeCount;
    }

    public void setCodeCount(int codeCount) {
        this.codeCount = codeCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

}
