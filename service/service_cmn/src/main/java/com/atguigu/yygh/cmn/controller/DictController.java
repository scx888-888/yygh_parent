package com.atguigu.yygh.cmn.controller;


import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.common.result.R;
import com.atguigu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@CrossOrigin
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
     * 导出文件（写操作）
     */
    @ApiOperation(value = "导出文件")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) {
        dictService.exportData(response);
    }


    /**
     * 导入文件（读操作）
     * @param file
     * @return
     */
    @ApiOperation(value = "导入")
    @PostMapping("importData")
    public R importData(MultipartFile file) {
        dictService.importDictData(file);
        return R.ok();
    }

}

