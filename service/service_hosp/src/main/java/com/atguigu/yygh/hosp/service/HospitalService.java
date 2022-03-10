package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
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

    /**
     * 获取医院列表
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    Page<Hospital> getHospitalList(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    /**
     * 更改医院状态
     * @param id
     * @param status
     */
    void updateHospStatus(String id, Integer status);

    /**
     * 根据id获取医院信息
     * @param id
     * @return
     */
    Map<String, Object> getHospById(String id);


    /**
     * 根据医院编号获取医院名称
     * @param hoscode
     * @return
     */
    String getHospName(String hoscode);

    /**
     * 根据医院名称获取医院列表
     * @param hosname
     * @return
     */
    List<Hospital> findByHosname(String hosname);

    /**
     * 医院预约挂号详情
     */
    Map<String, Object> item(String hoscode);
}
