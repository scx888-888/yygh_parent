package com.atguigu.yygh.cmn.service;


import com.atguigu.yygh.model.cmn.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 组织架构表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-04
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据数据id查询子列表
     * @param id
     * @return
     */
    List<Dict> findChildData(Long id);

    /**
     * 导出（写操作）
     * @param response
     */
    void exportData(HttpServletResponse response);

    void importDictData(MultipartFile file);
}
