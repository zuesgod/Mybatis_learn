package com.zues.godbatis.utils;

import java.io.InputStream;

/**
 * 获取资源工具类
 */
public class Resources {

    /**
     *
     * @param filePath 文件路径
     */
    public static InputStream getResourceAsStream(String filePath){
        return ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
    }

}
