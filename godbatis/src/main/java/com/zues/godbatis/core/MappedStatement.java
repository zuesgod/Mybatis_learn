package com.zues.godbatis.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 普通的java类，POJO，封装了一个SQL标签
 * 一个MappedStatement对象对应一个SQL标签
 * 一个SQL标签中所有信息封装到MappedStatement对象当中
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MappedStatement {


    /**
     * sql
     */
    private String sql;

    /**
     *  resultType 要封装的结果集类型，有的时候是null
     *  比如 insert  delete update语句的时候resultType是null
     *  只有当sql语句是select语句的时候resultType才有值
     *
     */
    private String resultType;
}
