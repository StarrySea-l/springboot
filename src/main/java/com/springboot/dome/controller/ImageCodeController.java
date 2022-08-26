package com.springboot.dome.controller;


import com.springboot.dome.imagecode.ImageCodeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class ImageCodeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ImageCodeValidator imageCodeValidator;

    @RequestMapping("/getImage")
    public void getImage(HttpServletRequest request, HttpServletResponse response) {
        BufferedImage code = imageCodeValidator.createSessionCode(request.getSession());
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(code, "png", outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("生成验证码发生异常");
        }
    }
}
