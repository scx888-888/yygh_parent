package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.HospitalRepository;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @Author scx
 * @Date 2022/3/7 17:43
 * @Description 医院信息实现类
 */
@Service
public class HospitalServiceImpl implements HospitalService  {


    @Autowired
    private HospitalRepository hospitalRepository;

    /**
     * 获取医院签名信息
     */


    /**
     * 新增医院信息到啊mongodb
     * @param paramMap
     */
    @Override
    public void saveHospital(Map<String, Object> paramMap) {
        Hospital hospital = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Hospital.class);
        //判断该医院信息是否存在
        Hospital targetHospital = hospitalRepository.getHospitalByHoscode(hospital.getHoscode());
        if(targetHospital != null){//已存在，做更新
            hospital.setStatus(targetHospital.getStatus());
            hospital.setCreateTime(targetHospital.getCreateTime());
            hospital.setUpdateTime(targetHospital.getUpdateTime());
            hospital.setIsDeleted(0);
            hospital.setId(targetHospital.getId());
            //更新
            hospitalRepository.save(hospital);
        }else {//不存在，做添加
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setStatus(0);
            //添加
            hospitalRepository.save(hospital);
        }

    }

    /**
     * 根据医院编号查询医院信息
     * @param hoscode
     * @return
     */
    @Override
    public Hospital getHospital(String hoscode) {
        Hospital hospital = hospitalRepository.findHospitalByHoscode(hoscode);
        return hospital;
    }
}
