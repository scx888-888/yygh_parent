package com.atguigu.yygh.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.atguigu.yygh.cmn.mapper")
public class CmnConfig {
}