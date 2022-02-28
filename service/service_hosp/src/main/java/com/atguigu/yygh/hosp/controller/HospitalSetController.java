package com.atguigu.yygh.hosp.controller;


import com.atguigu.yygh.common.result.R;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 医院设置表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-02-28
 */
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin//跨域操作
@Api(description = "医院设置接口")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    /**
     * 查询所有医院设置
     */
    @GetMapping("/list")
    @ApiOperation(value = "医院设置列表")
    public R findAll() {
        List<HospitalSet> list = hospitalSetService.list();
        for (HospitalSet hospitalSet : list) {
            System.out.println(hospitalSet);
        }
        return R.ok().data("list", list);
    }


    /**
     * 根据id删除医院设置
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "医院设置删除")
    public R removeById(@ApiParam(name = "id", value = "医院设置id", required = true) @RequestParam("id") String id) {
        boolean b = hospitalSetService.removeById(id);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 分页查询
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<HospitalSet> hospitalSetPage = new Page<>(page, limit);
        hospitalSetService.page(hospitalSetPage);
        //System.out.println("page1 = " + page1);
        System.out.println("======================================================================");
        List<HospitalSet> records = hospitalSetPage.getRecords();
        System.out.println("records = " + records);
        System.out.println("======================================================================");
        long total = hospitalSetPage.getTotal();
        System.out.println("total = " + total);

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 带条件分页查询
     *
     * @param page
     * @param limit
     * @param hospitalSetQueryVo
     * @return
     */
    @PostMapping("{page}/{limit}")
    public R getHospitalSetPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable long page,
            @ApiParam(name = "limit", value = "每页显示几条", required = true)
            @PathVariable long limit,
            @ApiParam(name = "hospitalSetQueryVo", value = "封装查询条件", required = false)
            @RequestBody HospitalSetQueryVo hospitalSetQueryVo) {
        Page pageParam = new Page(page, limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())) {
            queryWrapper.like("hostname", hospitalSetQueryVo.getHosname());
        }

        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())) {
            queryWrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }

        hospitalSetService.page(pageParam, queryWrapper);

        return R.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords());

    }


}

