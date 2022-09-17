package com.wooshop.modules.test001.service.impl;

import com.wooshop.modules.test001.domain.TestTable;
import com.wooshop.modules.test001.service.TestServiceM;
import com.wooshop.modules.test001.service.TestTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestServiceimplM implements TestServiceM {


    @Autowired
    private TestTableService testTableService;


    @Override
    public List<TestTable> getTest() throws Exception {
        List<TestTable> list = testTableService.list();

        return list;
    }
}
