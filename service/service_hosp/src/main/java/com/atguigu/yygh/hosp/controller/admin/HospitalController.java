package com.atguigu.yygh.hosp.controller.admin;

import com.atguigu.yygh.common.result.R;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author scx
 * @Date 2022/3/8 10:16
 * @Description 医院列表
 */
@RestController
@RequestMapping("/admin/hosp/hospital")
//@CrossOrigin
public class HospitalController {


    @Autowired
    private HospitalService hospitalService;


    /**
     * 获取医院分页信息
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @ApiOperation(value = "获取医院信息分页列表")
    @GetMapping("{page}/{limit}")
    public R getHospitalList(@PathVariable Integer page, @PathVariable Integer limit, HospitalQueryVo hospitalQueryVo){
       // System.out.println("进来了***********************************");
        Page<Hospital> hospitalList = hospitalService.getHospitalList(page, limit, hospitalQueryVo);

        return R.ok().data("pages",hospitalList);
    }

    /**
     * 更改医院状态
     * @return
     */
    @ApiOperation(value = "改变医院状态")
    @GetMapping("/updateHospStatus/{id}/{status}")
    public R updateHospStatus(@ApiParam(name = "id", value = "医院id", required = true)
                                  @PathVariable("id") String id,
                              @ApiParam(name = "status", value = "状态（0：未上线 1：已上线）", required = true)
                              @PathVariable("status") Integer status){
        hospitalService.updateHospStatus(id,status);
        return R.ok();
    }


    /**
     * 根据id获取医院详细信息
     */
    @ApiOperation(value = "根据id获取医院信息")
    @GetMapping("/getHospById/{id}")
    public R getHospById(@PathVariable("id") String id){
        Map<String, Object> map = hospitalService.getHospById(id);
        return R.ok().data("hospital",map);
    }






}
