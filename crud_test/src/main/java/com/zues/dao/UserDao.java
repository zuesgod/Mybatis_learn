package com.zues.dao;

import com.zues.entity.UserEntity;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    /**
     * 添加用户
     *
     * @return
     */
    int insertUser();

    int deleteUser(@Param("id") Long id);

    UserEntity getUserById(@Param("id") Long id);

    List<UserEntity> getUserList();

    int updateUser(@Param("name")String name, @Param("age")Integer age, @Param("id")Long id);


    /**
     * 根据id查询用户信息为map
     */
    Map<String,Object> getUserToMap(@Param("id") Long id);

    /**
     * 查询多条数据为map集合
     * 第一种方式
     */
    List<Map<String,Object>> getUserListToMap();

    /**
     * 查询多条数据为map集合
     * 第二种方式
     * 将表中的数据以map集合的方式查询，一条数据对应一个map，若有多条数据，就会产生多个map集合，并
     * 且最终以一个map的方式返回数据，此时需要通过MapKey注解设置map集合的键，值时每条数据锁对应的map集合
     */
    @MapKey("id")
    Map<String,Object> getAllUserToMap();
}
