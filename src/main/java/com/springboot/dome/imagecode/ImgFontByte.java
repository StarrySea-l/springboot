package com.springboot.dome.imagecode;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.security.SecureRandom;

public class ImgFontByte {

	public Font getFont(int fontHeight) {
		try {
			URL resource = ImgFontByte.class.getClassLoader().getResource("fonts");
			File path = new File(resource.getFile());
			File[] listFiles = path.listFiles();
			if(listFiles.length>0){
				int i = (int) (new SecureRandom().nextDouble() * listFiles.length);
				Font font = Font.createFont(Font.TRUETYPE_FONT, listFiles[i]);
				return font.deriveFont(Font.PLAIN, fontHeight);
			}
		} catch (Exception e) {
		}
		return new Font("Arial", Font.PLAIN, fontHeight);
	}

}
