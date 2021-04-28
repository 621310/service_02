package com.liuyl.service_02.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author liuyl01
 */

@Mapper
public interface TestMapper {

    public Map<String,Object> testMybatis();
}
