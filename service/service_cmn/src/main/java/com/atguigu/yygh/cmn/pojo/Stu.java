package com.atguigu.yygh.cmn.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stu {

    //设置表头名称
    @ExcelProperty("学生编号")
    private int sno;

    //设置表头名称
    @ExcelProperty("学生姓名")
    private String sname;



}