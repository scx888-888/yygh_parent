package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author scx
 * @Date 2022/3/7 17:48
 * @Description
 */
public interface HospitalRepository extends MongoRepository<Hospital, String> {


    /**
     * 根据hoscode(医院编号)查询医院信息
     * @param hoscode
     * @return
     */
    Hospital getHospitalByHoscode(String hoscode);

    /**
     * 根据医院编号查询医院信息
     * @param hoscode
     * @return
     */
    Hospital findHospitalByHoscode(String hoscode);

    /**
     * 根据医院名称获取医院信息列表
     * @param hosname
     * @return
     */
    List<Hospital> findHospitalByHosnameLike(String hosname);
}
