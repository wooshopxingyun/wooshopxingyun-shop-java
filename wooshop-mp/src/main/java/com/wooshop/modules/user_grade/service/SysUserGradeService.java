
package com.wooshop.modules.user_grade.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.user_grade.service.dto.SysUserGradeDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.user_grade.domain.SysUserGrade;

import com.wooshop.modules.user_grade.service.dto.SysUserGradeQueryCriteria;

/**
* @author woo
* @date 2021-12-13
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface SysUserGradeService  extends BaseService<SysUserGrade>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<SysUserGradeDto>  queryAll(SysUserGradeQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<SysUserGradeDto>
    */
    List<SysUserGrade> queryAll(SysUserGradeQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<SysUserGradeDto> all, HttpServletResponse response) throws IOException;

    /**
     * 获取可用等级列表
     * @return List
     */
    List<SysUserGrade> getUsableList();

    /**
     * 查询等级配置
     * @param id
     */
    SysUserGrade findById(Integer id);
}
