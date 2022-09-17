


package com.wooshop.modules.user_grade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageInfo;
import com.wooshop.common.QueryHelpPlus;
import com.wooshop.modules.utils.RedisUache;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.enums.MenuType;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.impl.BaseServiceImpl;
import lombok.AllArgsConstructor;
import java.io.IOException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.wooshop.domain.PageResult;
import javax.servlet.http.HttpServletResponse;

import com.wooshop.dozer.service.IGenerator;

import com.wooshop.modules.user_grade.domain.SysUserGrade;
import org.springframework.stereotype.Service;
import com.wooshop.modules.user_grade.service.dto.SysUserGradeQueryCriteria;
import com.wooshop.modules.user_grade.service.mapper.SysUserGradeMapper;
import com.wooshop.modules.user_grade.service.SysUserGradeService;
import com.wooshop.modules.user_grade.service.dto.SysUserGradeDto;


/**
* @author woo
* @date 2021-12-13
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SysUserGradeServiceImpl extends BaseServiceImpl<SysUserGradeMapper, SysUserGrade> implements SysUserGradeService {

    private final IGenerator generator;
    private final RedisUache redisUache;

    @Override
    public PageResult<SysUserGradeDto> queryAll(SysUserGradeQueryCriteria criteria, Pageable pageable) {
        setPageAndOrder(pageable.getPageNumber(), pageable.getPageSize(),pageable.getSort().toString());
        PageInfo<SysUserGrade> page = new PageInfo<>(queryAll(criteria));
        return generator.convertPageInfo(page,SysUserGradeDto.class);
    }


    @Override
    public List<SysUserGrade> queryAll(SysUserGradeQueryCriteria criteria){
        return baseMapper.selectList(QueryHelpPlus.getPredicate(SysUserGrade.class, criteria));
    }


    @Override
    public void download(List<SysUserGradeDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysUserGradeDto sysUserGrade : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("等级名称", sysUserGrade.getGradeName());
            map.put("等级权重", sysUserGrade.getGradeWeight());
            map.put("等级条件", sysUserGrade.getGradeCondition());
            map.put("等级权益", sysUserGrade.getGradeRights());
            map.put("创建时间", sysUserGrade.getCreateTime());
            map.put("更新时间", sysUserGrade.getUpdateTime());
            map.put("1启用 0关闭 状态", sysUserGrade.getIsStart());
            map.put("逻辑删除 1表示删除", sysUserGrade.getIsDel());
            map.put("等级图标", sysUserGrade.getGradeIcons());
            map.put("排序 数字越小越靠前", sysUserGrade.getSort());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 获取可用等级列表
     * @return List
     */
    @Override
    public List<SysUserGrade> getUsableList() {
        LambdaQueryWrapper<SysUserGrade> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUserGrade::getIsStart,  MenuType.IS_STATUS_1.getValue());
        lqw.eq(SysUserGrade::getIsDel,  MenuType.IS_DEL_STATUS_0.getValue());
        lqw.orderByAsc(SysUserGrade::getGradeWeight);
        return baseMapper.selectList(lqw);
    }

    /**
     * 查询等级配置
     * @param id
     */
    @Override
    public SysUserGrade findById(Integer id) {

        return baseMapper.selectById(id);
    }


}
