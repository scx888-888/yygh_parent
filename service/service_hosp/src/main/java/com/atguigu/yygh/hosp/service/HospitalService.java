package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;

import java.util.Map;

/**
 * @Author scx
 * @Date 2022/3/7 17:43
 * @Description 医院信息服务类
 */
public interface HospitalService {

    /**
     * 添加医院信息
     * @param paramMap
     */
    void saveHospital(Map<String, Object> paramMap);


    /**
     * 根据医院编号查询医院信息
     * @param hoscode
     * @return
     */
    Hospital getHospital(String hoscode);
}
