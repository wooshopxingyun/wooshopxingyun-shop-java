


package com.wooshop.modules.test001.service.impl;

import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.utils.FileUtil;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.wooshop.dozer.service.IGenerator;

import java.util.List;
import java.util.Map;
import com.wooshop.modules.test001.domain.TestTable;
import org.springframework.stereotype.Service;
import com.wooshop.modules.test001.service.dto.TestTableQueryCriteria;
import com.wooshop.modules.test001.service.mapper.TestTableMapper;
import com.wooshop.modules.test001.service.TestTableService;
import com.wooshop.modules.test001.service.dto.TestTableDto;


/**
* @author woo
* @date 2021-10-26
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TestTableServiceImpl extends BaseServiceImpl<TestTableMapper, TestTable> implements TestTableService {

//    @Autowired
    private final IGenerator generator;

    @Override
    //@Cacheable
    public PageResult<TestTableDto> queryAll(TestTableQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<TestTable> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,TestTableDto.class);
    }


    @Override
    //@Cacheable
    public List<TestTable> queryAll(TestTableQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(TestTable.class, criteria));
    }


    @Override
    public void download(List<TestTableDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TestTableDto testTable : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("测试名称", testTable.getTest());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
