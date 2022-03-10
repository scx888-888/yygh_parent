package com.atguigu.yygh.hosp.service;

import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface DepartmentService {
    /**
     * 添加科室信息
     * @param paramMap
     */
    void saveDepartment(Map<String, Object> paramMap);

    /**
     * 分页查询科室列表
     * @param page
     * @param limit
     * @param departmentQueryVo
     * @return
     */
    Page<Department> selectPage(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo);

    /**
     * 删除科室
     * @param hoscode
     * @param depcode
     */
    void remove(String hoscode, String depcode);

    /**
     * 根据医院编号查询医院所有的科室信息
     * @param hoscode
     * @return
     */
    List<DepartmentVo> findDeptTree(String hoscode);

    /**
     * 根据医院编号和科室编号获取科室名称
     * @param hoscode
     * @param depcode
     * @return
     */
    String getDepName(String hoscode, String depcode);
}