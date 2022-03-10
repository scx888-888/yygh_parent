package com.atguigu.yygh.cmn.controller;


import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.result.R;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 组织架构表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-03-04
 */
@RestController
@RequestMapping("/admin/cmn/dict")
@Api(description = "数据字典接口")
//@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;


    /**
     * 根据id查询子数据列表
     *
     * @param id
     * @return
     */
    @GetMapping("/findChildData/{id}")
    @ApiOperation(value = "根据id查询子数据列表")
    public R findChildData(@PathVariable Long id) {

        List<Dict> list = dictService.findChildData(id);

        return R.ok().data("list", list);
    }

    /**
     * 根据dictCode查询下级节点
     * @param dictCode
     * @return
     */
    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping(value = "/findByDictCode/{dictCode}")
    public R findByDictCode(
            @ApiParam(name = "dictCode", value = "节点编码", required = true)
            @PathVariable String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return R.ok().data("list",list);
    }


    /**
     * 导出文件（写操作）
     */
    @ApiOperation(value = "导出文件")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        dictService.exportData(response);
    }


    /**
     * 导入文件（读操作）
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "导入")
    @PostMapping("importData")
    public R importData(MultipartFile file) {
        dictService.importDictData(file);
        return R.ok();
    }


    /**
     * 1、根据省、市、区标号查询对应的地址信息
     */
    @ApiOperation(value = "获取数据字典名称")
    @GetMapping("/getName/{parentDictCode}/{value}")
    public String getName(@ApiParam(name = "parentDictCode", value = "上级编码", required = true)
                          @PathVariable("parentDictCode") String parentDictCode,
                          @ApiParam(name = "value", value = "值", required = true)
                          @PathVariable("value") String value) {

        return dictService.getNameByParentDictCodeAndValue(parentDictCode,value);

    }

    /**
     * 根据医院等级编号和hoscode查询医院等级
     */
    @ApiOperation(value = "获取数据字典名称")
    @GetMapping(value = "/getName/{value}")
    public String getName(
            @ApiParam(name = "value", value = "值", required = true)
            @PathVariable("value") String value) {
        return dictService.getNameByParentDictCodeAndValue(null, value);
    }





}

