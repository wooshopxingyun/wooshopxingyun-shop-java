package com.wooshop.modules.system.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wooshop.base.CommonMapper;
import com.wooshop.modules.system.domain.DictDetail;
import com.wooshop.modules.system.service.dto.DictDetailDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author jinjin
* @date 2020-09-24
*/
@Repository
public interface DictDetailMapper extends CommonMapper<DictDetail> {

//    @Select("select * FROM sys_dict_detail where dictName= #{dictName}")
    List<DictDetailDto> getDictDetailsByDictName(@Param("dictName") String dictName);


//    @Select("select * FROM sys_dict_detail ${ew.customSqlSegment}")
//    @Select("<script>SELECT d.* from dict_detail d LEFT JOIN dict t on d.dict_id = t.id where 1=1  <if test = \"dictName != ''||dictName !=null\" > AND t.name = #{dictName} order by t.sort asc</if></script>")

//    IPage<DictDetailDto> getDictDetailsByDictName(@Param("dictName") String dictName, IPage<DictDetailDto> page);
    IPage<DictDetailDto> getDictDetailsByDictName(IPage<DictDetailDto> page,@Param("dictName") String dictName);
}
