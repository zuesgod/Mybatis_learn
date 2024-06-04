package com.zues.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EmployeeEntity {

    private Long id;

    private String name;

    private Long deptId;

    private Integer salary;

    private DeptEntity dept;

}
