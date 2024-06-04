package com.zues.dao;

import com.zues.entity.EmployeeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface EmployeeDao {

    List<EmployeeEntity> getEmpAndDeptByEid(@Param("deptId") Long deptId);


    /**
     * 分布查询
     */
    EmployeeEntity getEmpByStep(@Param("id") Long id);
}
