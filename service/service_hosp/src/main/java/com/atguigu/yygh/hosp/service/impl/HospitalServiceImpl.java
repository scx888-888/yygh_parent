package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.cmn.client.DictFeignClient;
import com.atguigu.yygh.enums.DictEnum;
import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.repository.HospitalRepository;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author scx
 * @Date 2022/3/7 17:43
 * @Description 医院信息实现类
 */
@Service
public class HospitalServiceImpl implements HospitalService  {


    @Autowired
    private HospitalRepository hospitalRepository;


    //注入远程调用数据字典
    @Autowired
    private DictFeignClient dictFeignClient;

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

    /**
     * 获取医院信息分页列表
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @Override
    public Page<Hospital> getHospitalList(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        //设置排序规则
        Sort sort =  Sort.by(Sort.Direction.DESC,"createTime");
        //0为第一页
        Pageable pageable = PageRequest.of(page-1, limit, sort);

        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);

        //创建匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()//构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)//改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true);//改变默认大小写忽略方式：忽略大小写

        //创建实例
        Example<Hospital> example = Example.of(hospital,matcher);
        Page<Hospital> pages = hospitalRepository.findAll(example,pageable);

        //封装医院数据
        pages.getContent().stream().forEach(itme->{
            this.packHospital(itme);
        });


        return pages;
    }

    /**
     * 更改医院状态
     * @param id
     * @param status
     */
    @Override
    public void updateHospStatus(String id, Integer status) {
        if(status==0 ||status==1){
            Hospital hospital = hospitalRepository.findById(id).get();
            hospital.setStatus(status);
            hospital.setUpdateTime(new Date());
            hospitalRepository.save(hospital);

        }
    }


    /**
     * 根据id获取医院信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getHospById(String id) {
        Map<String, Object> result = new HashMap<>();
        Hospital hospital = this.packHospital(hospitalRepository.findById(id).get());
        //医院基本信息（包含医院等级）
        result.put("hospital",hospital);
        //单独处理更直观
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return result;
    }

    /**
     * 根据医院编号获取医院名称
     * @param hoscode
     * @return
     */
    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.findHospitalByHoscode(hoscode);
        return hospital.getHosname();
    }

    /**
     * 根据医院名称获取医院信息列表
     * @param hosname
     * @return
     */
    @Override
    public List<Hospital> findByHosname(String hosname) {

        return  hospitalRepository.findHospitalByHosnameLike(hosname);
    }

    /**
     * 医院预约挂号详情
     * @param hoscode
     * @return
     */
    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> result = new HashMap<>();
        //医院详情
        Hospital hospital = this.packHospital(this.getHospital(hoscode));
        result.put("hospital", hospital);
        //预约规则
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return result;

    }


    /**
     * 封装数据
     */
    public Hospital packHospital(Hospital hospital) {

       // System.out.println("hospital = " + hospital);
        //医院等级
        String hostypeString = dictFeignClient.getName(DictEnum.HOSTYPE.getDictCode(), hospital.getHostype());
        //省
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        //市
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        //区
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("hostypeString",hostypeString);
        hospital.getParam().put("fullAddress",provinceString+cityString+districtString+hospital.getAddress());


        return hospital;


    }
}
