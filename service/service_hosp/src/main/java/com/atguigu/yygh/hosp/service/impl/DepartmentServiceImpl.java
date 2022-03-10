package com.atguigu.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.hosp.repository.DepartmentRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void saveDepartment(Map<String, Object> paramMap) {
        String paramMapString = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(paramMapString, Department.class);
        //根据医院编号 和 科室编号查询科室是否存在
        Department departmentExist = departmentRepository.getDepartmentByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        if(departmentExist!=null){//已存在，做修改
            department.setCreateTime(departmentExist.getCreateTime());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            department.setId(departmentExist.getId());
            departmentRepository.save(department);
        }else {//不存在，做添加
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }

    @Override
    public Page<Department> selectPage(Integer page, Integer limit, DepartmentQueryVo departmentQueryVo) {

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");

        //0为第一页
        Pageable pageable = PageRequest.of(page-1, limit, sort);

        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo, department);
        department.setIsDeleted(0);

        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写


        //创建实例
        Example<Department> example = Example.of(department, matcher);
        Page<Department> pages = departmentRepository.findAll(example, pageable);
        return pages;
    }

    @Override
    public void remove(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(null != department) {
            departmentRepository.deleteById(department.getId());
        }
    }

    /**
     * 根据医院编号查询科室信息
     * @param hoscode
     * @return
     */
    @Override
    public List<DepartmentVo> findDeptTree(String hoscode) {
        //创建list集合，用于最终数据封装
        List<DepartmentVo> result = new ArrayList<>();

        //根据医院编号查询科室信息
        Department department = new Department();
        department.setHoscode(hoscode);

        Example example = Example.of(department);

        //获取所有科室信息
        List<Department> departmentList = departmentRepository.findAll(example);

        //根据大科室编号（bigcode）进行分组，获取每个大科室下面的子科室
        Map<String, List<Department>> departmentMap = departmentList.stream().collect(Collectors.groupingBy(Department::getBigcode));

        //遍历map集合
        for (Map.Entry<String, List<Department>> entry : departmentMap.entrySet()){
            //大科室编号
            String bidcode = entry.getKey();

            //大科室编号对应的全局数据
            List<Department> departments = entry.getValue();

            //封装大科室
            DepartmentVo departmentVo = new DepartmentVo();
            departmentVo.setDepcode(bidcode);
            departmentVo.setDepname(departments.get(0).getBigname());

            //封装小科室
            List<DepartmentVo> children = new ArrayList<>();
            for(Department dep : departments){
                DepartmentVo departmentVo2 =  new DepartmentVo();
                departmentVo2.setDepcode(dep.getDepcode());
                departmentVo2.setDepname(dep.getDepname());
                children.add(departmentVo2);
            }
            //把小科室list集合封装到大科室children里面
            departmentVo.setChildren(children);

            result.add(departmentVo);
        }
        return result;
    }

    /**
     * 根据医院编号和科室编号获取科室名称
     * @param hoscode
     * @param depcode
     * @return
     */
    @Override
    public String getDepName(String hoscode, String depcode) {
        Department department = departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);
        if(department != null) {
            return department.getDepname();
        }
        return null;
    }
}