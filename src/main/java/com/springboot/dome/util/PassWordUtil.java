package com.springboot.dome.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author :王磊
 * @version :
 * @date :Created in 2021/12/29 10:48
 * @description: 密码工具类
 */
public class PassWordUtil {

    // 用户锁定时间，单位秒，默认30分钟
    public final static long USER_LOCKING_TIME = 30 * 60L;
    // 用户登录错误redis存储key
    public final static String KEY_USER_PSD_COUNT = "_USER_PSD_COUNT_";
    // 用户登录连续错误5次，账号将锁定30分钟
    public final static int MAX_COUNT = 5;

    public final static int MIN_COUNT = 0;

    public final static String MD5 = "MD5";

    /**
     * 验证密码是否超过90天
     *
     * @param passValidityTime
     * @return
     */
    public static boolean psdExpire(Date passValidityTime) {
        Date nowTime = new Date();
        if (passValidityTime != null) {
            int daysBetween = DateUtils.getDaysBetween(passValidityTime, nowTime);
        }
        return false;
    }

    /**
     * 验证密码的复杂度，至少含有数字，小写字母，大写字母，特殊字符中的三种
     *
     * @param password
     * @return
     */
    public static boolean psdTooSimple(String password) {
        // 长度至少6
        if (password.length() < 6) {
            return false;
        }
        int typecount = 0;
        // 如果含有数字
        if (Pattern.matches(".*[0-9]+.*", password)) {
            typecount++;
        }
        // 如果含有大写字母
        if (Pattern.matches(".*[A-Z]+.*", password)) {
            typecount++;
        }
        // 如果含有小写字母
        if (Pattern.matches(".*[a-z]+.*", password)) {
            typecount++;
        }
        // 如果含有特殊字符
        if (Pattern.matches(".*[^A-Za-z0-9]+.*", password)) {
            typecount++;
        }
        // 至少含有数字、大写、小写、特殊字符中的3种
        if (typecount >= 3) {
            return false;
        }
        return true;
    }

    /**
     * 密码输入正确，删除redis上存放的count值
     *
     * @param redisTemplate
     * @param psd_count
     * @param key
     */
    public static void deletePsdCountIfNecessary(RedisTemplate<Object, Object> redisTemplate, Integer psd_count,
                                                 String key) {
        if (psd_count != 0) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 为原始密码生成加密密码
     *
     * @param username
     * @param initPassword
     * @return
     */
    public static String generatePassword(String username, String initPassword) {
        Object credentials = initPassword;
        Object salt = ByteSource.Util.bytes(username);
        int hashIterations = 1024;
        Object password = new SimpleHash(MD5, credentials, salt, hashIterations);
        return password.toString();
    }

    public static void main(String[] args) {
        String admin = generatePassword("admin", "123456");
        Object salt = ByteSource.Util.bytes("admin");
        System.out.println(salt);
        System.out.println(admin);
    }
}
