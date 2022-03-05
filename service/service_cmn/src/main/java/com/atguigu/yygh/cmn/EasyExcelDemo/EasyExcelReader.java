package com.atguigu.yygh.cmn.EasyExcelDemo;

import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.pojo.Stu;

/**
 * @Author scx
 * @Date 2022/3/4 20:36
 * @Description EasyExcel读操作
 */
public class EasyExcelReader {
    public static void main(String[] args) throws Exception {

        String fileName = "D:\\11.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, Stu.class, new ExcelListener()).sheet(1).doRead();
    }
}
