package com.wooshop.modules.user.impl;

import com.wooshop.modules.service.impl.BaseServiceImpl;
import com.wooshop.modules.system.domain.Dept;
import com.wooshop.modules.system.service.dto.DeptDto;
import com.wooshop.modules.system.service.mapper.DeptMapper;
import com.wooshop.modules.user.WooDeptService;
import com.wooshop.utils.ConvertUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WooDeptServiceImpl extends BaseServiceImpl<DeptMapper, Dept> implements WooDeptService {





    @Override
    public Dept getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public DeptDto findById(Long id) {
        return ConvertUtil.convert(getById(id),DeptDto.class);
    }
}
