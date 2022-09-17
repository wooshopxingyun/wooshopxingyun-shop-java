package com.wooshop.modules.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wooshop.base.CommonMapper;
import com.wooshop.domain.ColumnInfo;
import com.wooshop.domain.vo.TableInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-25
*/
@Repository
public interface ColumnInfoMapper extends CommonMapper<ColumnInfo> {

    List<TableInfo> getTables();

    int getTablesTotal();

    IPage<TableInfo> selectPageOfTables(IPage<?> page, @Param("name") String tableName);

    List<ColumnInfo> queryColumnInfo(String tableName);
}
