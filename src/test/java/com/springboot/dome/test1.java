package com.springboot.dome;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author :王磊
 * @version :
 * @date :Created in 2021/12/21 9:23
 * @description:
 */
@SpringBootTest
public class test1 {
    static Log log = LogFactory.getLog(test1.class);

    public static void main(String[] args) {
        log.info("start........");
        try {
            int i = 1/0;
        } catch (Exception e) {
            log.error(e);
        }

        log.info("end........");
    }
    @Test
    public void test() {

    }

}
