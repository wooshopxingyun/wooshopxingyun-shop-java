package com.wooshop.modules.mnt.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.modules.mnt.domain.DeploysServers;
import com.wooshop.modules.mnt.mapper.DeploysServersMapper;
import com.wooshop.modules.mnt.service.DeploysServersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jinjin on 2020-09-25.
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeploysServersServiceImpl extends CommonServiceImpl<DeploysServersMapper, DeploysServers> implements DeploysServersService {
    private final DeploysServersMapper deploysServersMapper;

    @Override
    public List<Long> queryDeployIdByServerId(Long id) {
        return lambdaQuery()
                .eq(DeploysServers::getServerId, id)
                .list().stream().map(DeploysServers::getDeployId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> queryServerIdByDeployId(Long id) {
        return lambdaQuery()
                .eq(DeploysServers::getDeployId, id)
                .list().stream().map(DeploysServers::getServerId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByDeployId(Long id) {
        return lambdaUpdate().eq(DeploysServers::getDeployId, id).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByServerId(Long id) {
        return lambdaUpdate().eq(DeploysServers::getServerId, id).remove();
    }
}
