package com.atguigu.yygh.cmn.EasyExcelDemo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.atguigu.yygh.cmn.pojo.Stu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author scx
 * @Date 2022/3/4 19:38
 * @Description EasyExcel写操作
 */
public class EasyExcelWriter {
    public static void main(String[] args) throws Exception {
        // 写法1
        String fileName = "D:\\11.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        List<Stu> stus = new ArrayList<Stu>();
        stus.add(new Stu(1, "沈楚雄"));

        //自动关流
        EasyExcel.write(fileName, Stu.class).sheet(0,"sheet1").doWrite(stus);
        //EasyExcel.write(fileName, Stu.class).sheet(1,"sheet2").doWrite(stus);



        //需要finish帮忙关流(手动关流)

        //指定文件和写入类型
        ExcelWriter excelWriter = EasyExcel.write(fileName, Stu.class).build();

        //指定sheet，和给需要写入的数据
        WriteSheet writeSheet = EasyExcel.writerSheet(0,"sheet1").build();
        //写入
        excelWriter.write(stus, writeSheet);

        writeSheet = EasyExcel.writerSheet(0, "模板1").build();
        excelWriter.write(stus, writeSheet);

        writeSheet = EasyExcel.writerSheet(1, "模板2").build();
        excelWriter.write(stus, writeSheet);

        writeSheet = EasyExcel.writerSheet(2, "模板3").build();
        excelWriter.write(stus, writeSheet);

        writeSheet = EasyExcel.writerSheet(3, "模板4").build();
        excelWriter.write(stus, writeSheet);

        excelWriter.finish();



    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<Stu> data() {
        List<Stu> list = new ArrayList<Stu>();
        for (int i = 0; i < 10; i++) {
            Stu data = new Stu();
            data.setSno(i);
            data.setSname("张三" + i);
            list.add(data);
        }
        return list;
    }


}
