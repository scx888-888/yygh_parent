package com.atguigu.yygh.cmn.service.impl;


import com.alibaba.excel.EasyExcel;
import com.atguigu.yygh.cmn.config.DictListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.cmn.service.DictService;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-04
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * 读操作监听器
     */
    //@Autowired
    //private DictListener dictListener;


    /**
     * 查询子列表
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = "dict", key = "'selectIndexList'+#id")
    public List<Dict> findChildData(Long id) {
        System.out.println("该方法执行了！！！！！！！！！！！！！！！！！！");
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //给list集合中的每个dict对象设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChild = this.isChildren(dictId);
            dict.setHasChildren(isChild);
        }
        return dictList;
    }


    /**
     * 导出（写操作）
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {


        try {
            //设置响应头类型
            response.setContentType("application/vnd.ms-excel");
            //设置响应头编码
            response.setCharacterEncoding("utf-8");
            //这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("数据字典", "UTF-8");
            //设置响应头
            response.setHeader("Content-disposition","attachment;filename="+fileName+".xlsx");

            //查询数据数据字典数据
            List<Dict> dictList = baseMapper.selectList(null);
            List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
            for (Dict dict : dictList) {
                DictEeVo dictEeVo = new DictEeVo();
                BeanUtils.copyProperties(dict, dictEeVo);
                dictVoList.add(dictEeVo);
            }

            EasyExcel.write(response.getOutputStream(),DictEeVo.class).sheet("数据字典").doWrite(dictVoList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 导入文件（写操作）
     * @param file
     */
    @Override
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断该id下面是否有子节点
     *
     * @param id
     * @return
     */
    public boolean isChildren(Long id) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;
    }
}
