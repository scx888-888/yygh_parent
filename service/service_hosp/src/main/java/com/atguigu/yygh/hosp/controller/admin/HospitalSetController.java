package com.atguigu.yygh.hosp.controller.admin;


import com.atguigu.yygh.common.result.R;
import com.atguigu.yygh.hosp.service.HospitalSetService;
import com.atguigu.yygh.hosp.utils.MD5;
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
import java.util.Random;

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
    @ApiOperation(value = "分页查询")
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
    @ApiOperation(value = "带条件分页查询")
    @PostMapping("{page}/{limit}")
    public R getHospitalSetPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable long page,
            @ApiParam(name = "limit", value = "每页显示几条", required = true)
            @PathVariable long limit,
            @ApiParam(name = "hospitalSetQueryVo", value = "封装查询条件", required = false)
            @RequestBody HospitalSetQueryVo hospitalSetQueryVo) {
        Page<HospitalSet> pageParam = new Page(page, limit);
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHosname())) {
            queryWrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }

        if (!StringUtils.isEmpty(hospitalSetQueryVo.getHoscode())) {
            queryWrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }

        hospitalSetService.page(pageParam, queryWrapper);

        //System.out.println("============"+pageParam.getTotal());
        //System.out.println(pageParam.getRecords());
        //System.out.println("*********************************");


        return R.ok().data("total", pageParam.getTotal()).data("rows", pageParam.getRecords());

    }


    /**
     * 新增医院设置
     * @param hospitalSet
     * @return
     */
    @ApiOperation(value ="新增医院设置")
    @PostMapping("/saveHospitalSet")
    public R save(
            @ApiParam(name = "hospitalSet", value = "医院设置对象", required = true)
            @RequestBody HospitalSet hospitalSet) {
        //设置状态 1使用  0不能使用
        hospitalSet.setStatus(1);
        System.out.println("hospitalSet = " + hospitalSet);
        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() +""+random.nextInt(1000)));

        hospitalSetService.save(hospitalSet);
        return R.ok();
    }

    /**
     * 根据id查询医院设置
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询医院设置")
    @GetMapping("/getHospSet/{id}")
    public R getById(
            @ApiParam(name = "id", value = "医院设置ID", required = true)
            @PathVariable String id){

        HospitalSet hospitalSet = hospitalSetService.getById(id);

        return R.ok().data("item", hospitalSet);
    }

    /**
     * 根据id修改医院设置
     * @param hospitalSet
     * @return
     */
    @ApiOperation(value = "根据ID修改医院设置")
    @PostMapping("/updateHospSet")
    public R updateById(@ApiParam(name = "hospitalSet", value = "医院设置对象", required = true)
                        @RequestBody HospitalSet hospitalSet){
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }


    /**
     * 批量删除医院设置
     * @param idList
     * @return
     */
    @ApiOperation(value = "批量删除医院设置")
    @DeleteMapping("/batchRemove")
    public R batchRemoveHospitalSet(@ApiParam(name = "idList",value ="批量删除的id") @RequestBody List<Long> idList) {
        System.out.println("进来了");
        hospitalSetService.removeByIds(idList);
        return R.ok();
    }


    /**
     * 医院设置锁定和解锁
     * @param id
     * @param status
     * @return
     */
    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public R lockHospitalSet(@PathVariable Long id,
                             @PathVariable Integer status) {
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置状态
        hospitalSet.setStatus(status);
        //调用方法
        hospitalSetService.updateById(hospitalSet);
        return R.ok();
    }

    @ApiOperation(value = "删除医院设置信息")
    @DeleteMapping("/removeHospById/{id}")
    public R removeHospById(@PathVariable Long id ){
        System.out.println("id = " + id);
        System.out.println("删除执行了");
        if(id==6){
            System.out.println("R.error() = " + R.error());
            return  R.error();
        }
        hospitalSetService.removeById(id);
        return  R.ok();
    }



}

