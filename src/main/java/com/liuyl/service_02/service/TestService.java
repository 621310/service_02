package com.liuyl.service_02.service;

import com.liuyl.service_02.mapper.TestMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author liuyl01
 */
@Service
public class TestService {


    private final TestMapper testMapper;

    public TestService(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    public Map<String,Object> testMybatis(){
     return testMapper.testMybatis();
    }
}
