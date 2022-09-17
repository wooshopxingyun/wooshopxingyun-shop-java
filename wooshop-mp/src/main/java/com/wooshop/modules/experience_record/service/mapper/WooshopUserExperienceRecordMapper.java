

package com.wooshop.modules.experience_record.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.wooshop.modules.experience_record.domain.WooshopUserExperienceRecord;
import com.wooshop.common.mapper.CoreMapper;


/**
* @author woo
* @date 2022-03-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Repository
public interface WooshopUserExperienceRecordMapper extends CoreMapper<WooshopUserExperienceRecord> {


     /**
      * 统计当天获得的经验
      * @param queryWrapper
      */
     @Select("SELECT sum(experience) FROM wooshop_user_experience_record ${ew.customSqlSegment}")
     Integer getDayExpTotal(@Param(Constants.WRAPPER) QueryWrapper queryWrapper);
}
