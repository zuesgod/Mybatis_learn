package com.zues.godbatis.utils;

import java.io.InputStream;

/**
 * 资源工具类
 * @author zues
 * @version 1.0
 * @since 1.0
 */
public class Resources {

    /**
     *从类路径中获取配置文件的输入流
     * @param config 配置文件路径
     * @return 输入流，该输入流指向类路径中的配置文件
     */
    public static InputStream getResourceAsStream(String config){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(config);
    }

}
