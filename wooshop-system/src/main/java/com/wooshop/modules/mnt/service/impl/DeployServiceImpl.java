package com.wooshop.modules.mnt.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.wooshop.base.PageInfo;
import com.wooshop.base.QueryHelpMybatisPlus;
import com.wooshop.base.impl.CommonServiceImpl;
import com.wooshop.exception.BadRequestException;
import com.wooshop.modules.mnt.domain.*;
import com.wooshop.modules.mnt.mapper.DeployMapper;
import com.wooshop.modules.mnt.mapper.DeploysServersMapper;
import com.wooshop.modules.mnt.mapper.ServerMapper;
import com.wooshop.modules.mnt.service.*;
import com.wooshop.modules.mnt.service.dto.*;
import com.wooshop.modules.mnt.util.ExecuteShellUtil;
import com.wooshop.modules.mnt.util.ScpClientUtil;
import com.wooshop.modules.mnt.websocket.MsgType;
import com.wooshop.modules.mnt.websocket.SocketMsg;
import com.wooshop.modules.mnt.websocket.WebSocketServer;
import com.wooshop.utils.ConvertUtil;
import com.wooshop.utils.FileUtil;
import com.wooshop.utils.PageUtil;
import com.wooshop.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author jinjin
* @date 2020-09-27
*/
@Slf4j
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = DeployService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeployServiceImpl extends CommonServiceImpl<DeployMapper, Deploy> implements DeployService {

    private final String FILE_SEPARATOR = "/";
    // private final RedisUtils redisUtils;
    private final DeployMapper deployMapper;
    private final ServerMapper serverMapper;
    private final AppService appService;
    private final ServerService serverService;
    private final DeployHistoryService deployHistoryService;
    private final DeploysServersService deploysServersService;
    private final DeploysServersMapper deploysServersMapper;

    /**
     * 循环次数
     */
    private final Integer count = 30;

    @Override
    public PageInfo<DeployDto> queryAll(DeployQueryParam query, Pageable pageable) {
        IPage<Deploy> page = PageUtil.toMybatisPage(pageable);
        IPage<Deploy> pageList = deployMapper.selectPage(page, QueryHelpMybatisPlus.getPredicate(query));
        PageInfo<DeployDto> pi = ConvertUtil.convertPage(pageList, DeployDto.class);
        for (DeployDto dd: pi.getContent() ) {
            dd.setApp(appService.findById(dd.getAppId()));
            dd.setDeploys(new HashSet<>(ConvertUtil.convertList(serverMapper.lambdaQuery()
                    .in(Server::getId, deploysServersService.queryServerIdByDeployId(dd.getId()))
                    .list(), ServerDto.class)));
        }
        return pi;
    }

    @Override
    public List<DeployDto> queryAll(DeployQueryParam query){
        List<DeployDto> list = ConvertUtil.convertList(deployMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), DeployDto.class);
        for (DeployDto dd: list) {
            dd.setApp(appService.findById(dd.getAppId()));
            dd.setDeploys(new HashSet<>(ConvertUtil.convertList(serverMapper.lambdaQuery()
                    .in(Server::getId, deploysServersService.queryServerIdByDeployId(dd.getId()))
                    .list(), ServerDto.class)));
        }
        return list;
    }

    @Override
    public Deploy getById(Long id) {
        return getById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public DeployDto findById(Long id) {
        return ConvertUtil.convert(deployMapper.selectById(id), DeployDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(DeployDto resources) {
        Deploy deploy = ConvertUtil.convert(resources, Deploy.class);
        deploy.setAppId(resources.getApp().getId());
        int ret = deployMapper.insert(deploy);
        if (deploy.getId() != null) {
            deploysServersService.removeByDeployId(deploy.getId());
        }
        for (ServerDto server: resources.getDeploys()) {
            DeploysServers ds = new DeploysServers();
            ds.setDeployId(deploy.getId());
            ds.setServerId(server.getId());
            deploysServersMapper.insert(ds);
        }
        return ret > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(DeployDto resources){
        Deploy deploy = ConvertUtil.convert(resources, Deploy.class);
        deploy.setAppId(resources.getApp().getId());
        int ret = deployMapper.updateById(deploy);
        if (deploy.getId() != null) {
            deploysServersService.removeByDeployId(deploy.getId());
        }
        for (ServerDto server: resources.getDeploys()) {
            DeploysServers ds = new DeploysServers();
            ds.setDeployId(deploy.getId());
            ds.setServerId(server.getId());
            deploysServersMapper.insert(ds);
        }
        return ret > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Set<Long> ids){
        // delCaches(ids);
        for (Long id: ids) {
            deploysServersService.removeByDeployId(id);
        }
        return deployMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    /*
    private void delCaches(Long id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<Long> ids) {
        for (Long id: ids) {
            delCaches(id);
        }
    }*/

    @Override
    public void download(List<DeployDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (DeployDto deploy : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("应用编号", deploy.getApp().getId());
              map.put("创建者", deploy.getCreateBy());
              map.put("更新者", deploy.getUpdateBy());
                map.put(" createTime",  deploy.getCreateTime());
              map.put("更新时间", deploy.getUpdateTime());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }

    @Override
    public void deploy(String fileSavePath, Long id) {
        deployApp(fileSavePath, id);
    }

    /**
     * @param fileSavePath 本机路径
     * @param id ID
     */
    private void deployApp(String fileSavePath, Long id) {

        DeployDto deploy = findById(id);
        if (deploy == null) {
            sendMsg("部署信息不存在", MsgType.ERROR);
            throw new BadRequestException("部署信息不存在");
        }
        AppDto app = deploy.getApp();
        if (app == null) {
            sendMsg("包对应应用信息不存在", MsgType.ERROR);
            throw new BadRequestException("包对应应用信息不存在");
        }
        int port = app.getPort();
        //这个是服务器部署路径
        String uploadPath = app.getUploadPath();
        StringBuilder sb = new StringBuilder();
        String msg;
        Set<ServerDto> deploys = deploy.getDeploys();
        for (ServerDto deployDTO : deploys) {
            String ip = deployDTO.getIp();
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(ip);
            //判断是否第一次部署
            boolean flag = checkFile(executeShellUtil, app);
            //第一步要确认服务器上有这个目录
            executeShellUtil.execute("mkdir -p " + app.getUploadPath());
            executeShellUtil.execute("mkdir -p " + app.getBackupPath());
            executeShellUtil.execute("mkdir -p " + app.getDeployPath());
            //上传文件
            msg = String.format("登陆到服务器:%s", ip);
            ScpClientUtil scpClientUtil = getScpClientUtil(ip);
            log.info(msg);
            sendMsg(msg, MsgType.INFO);
            msg = String.format("上传文件到服务器:%s<br>目录:%s下，请稍等...", ip, uploadPath);
            sendMsg(msg, MsgType.INFO);
            scpClientUtil.putFile(fileSavePath, uploadPath);
            if (flag) {
                sendMsg("停止原来应用", MsgType.INFO);
                //停止应用
                stopApp(port, executeShellUtil);
                sendMsg("备份原来应用", MsgType.INFO);
                //备份应用
                backupApp(executeShellUtil, ip, app.getDeployPath()+FILE_SEPARATOR, app.getName(), app.getBackupPath()+FILE_SEPARATOR, id);
            }
            sendMsg("部署应用", MsgType.INFO);
            //部署文件,并启动应用
            String deployScript = app.getDeployScript();
            executeShellUtil.execute(deployScript);
            sleep(3);
            sendMsg("应用部署中，请耐心等待部署结果，或者稍后手动查看部署状态", MsgType.INFO);
            int i  = 0;
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            while (i++ < count){
                result = checkIsRunningStatus(port, executeShellUtil);
                if(result){
                    break;
                }
                // 休眠6秒
                sleep(6);
            }
            sb.append("服务器:").append(deployDTO.getName()).append("<br>应用:").append(app.getName());
            sendResultMsg(result, sb);
            executeShellUtil.close();
        }
    }

    private void sleep(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(),e);
        }
    }

    private void backupApp(ExecuteShellUtil executeShellUtil, String ip, String fileSavePath, String appName, String backupPath, Long id) {
        String deployDate = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        StringBuilder sb = new StringBuilder();
        backupPath += appName + FILE_SEPARATOR + deployDate + "\n";
        sb.append("mkdir -p ").append(backupPath);
        sb.append("mv -f ").append(fileSavePath);
        sb.append(appName).append(" ").append(backupPath);
        log.info("备份应用脚本:" + sb.toString());
        executeShellUtil.execute(sb.toString());
        //还原信息入库
        DeployHistory deployHistory = new DeployHistory();
        deployHistory.setAppName(appName);
        deployHistory.setDeployUser(SecurityUtils.getCurrentUsername());
        deployHistory.setIp(ip);
        deployHistory.setDeployId(id);
        deployHistoryService.save(deployHistory);
    }

    /**
     * 停App
     *
     * @param port 端口
     * @param executeShellUtil /
     */
    private void stopApp(int port, ExecuteShellUtil executeShellUtil) {
        //发送停止命令
        executeShellUtil.execute(String.format("lsof -i :%d|grep -v \"PID\"|awk '{print \"kill -9\",$2}'|sh", port));

    }

    /**
     * 指定端口程序是否在运行
     *
     * @param port 端口
     * @param executeShellUtil /
     * @return true 正在运行  false 已经停止
     */
    private boolean checkIsRunningStatus(int port, ExecuteShellUtil executeShellUtil) {
        String result = executeShellUtil.executeForResult(String.format("fuser -n tcp %d", port));
        return result.indexOf("/tcp:")>0;
    }

    private void sendMsg(String msg, MsgType msgType) {
        try {
            WebSocketServer.sendInfo(new SocketMsg(msg, msgType), "deploy");
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public String serverStatus(DeployDto resources) {
        Set<ServerDto> Servers = resources.getDeploys();
        AppDto app = resources.getApp();
        for (ServerDto Server : Servers) {
            StringBuilder sb = new StringBuilder();
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(Server.getIp());
            sb.append("服务器:").append(Server.getName()).append("<br>应用:").append(app.getName());
            boolean result = checkIsRunningStatus(app.getPort(), executeShellUtil);
            if (result) {
                sb.append("<br>正在运行");
                sendMsg(sb.toString(), MsgType.INFO);
            } else {
                sb.append("<br>已停止!");
                sendMsg(sb.toString(), MsgType.ERROR);
            }
            log.info(sb.toString());
            executeShellUtil.close();
        }
        return "执行完毕";
    }

    private boolean checkFile(ExecuteShellUtil executeShellUtil, AppDto appDTO) {
        String result = executeShellUtil.executeForResult("find " + appDTO.getDeployPath() + " -name " + appDTO.getName());
        return result.indexOf(appDTO.getName())>0;
    }

    /**
     * 启动服务
     * @param resources /
     * @return /
     */
    @Override
    public String startServer(DeployDto resources) {
        Set<ServerDto> deploys = resources.getDeploys();
        AppDto app = resources.getApp();
        for (ServerDto deploy : deploys) {
            StringBuilder sb = new StringBuilder();
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(deploy.getIp());
            //为了防止重复启动，这里先停止应用
            stopApp(app.getPort(), executeShellUtil);
            sb.append("服务器:").append(deploy.getName()).append("<br>应用:").append(app.getName());
            sendMsg("下发启动命令", MsgType.INFO);
            executeShellUtil.execute(app.getStartScript());
            sleep(3);
            sendMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看运行状态", MsgType.INFO);
            int i  = 0;
            boolean result = false;
            // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
            while (i++ < count){
                result = checkIsRunningStatus(app.getPort(), executeShellUtil);
                if(result){
                    break;
                }
                // 休眠6秒
                sleep(6);
            }
            sendResultMsg(result, sb);
            log.info(sb.toString());
            executeShellUtil.close();
        }
        return "执行完毕";
    }

    /**
     * 停止服务
     * @param resources /
     * @return /
     */
    @Override
    public String stopServer(DeployDto resources) {
        Set<ServerDto> deploys = resources.getDeploys();
        AppDto app = resources.getApp();
        for (ServerDto deploy : deploys) {
            StringBuilder sb = new StringBuilder();
            ExecuteShellUtil executeShellUtil = getExecuteShellUtil(deploy.getIp());
            sb.append("服务器:").append(deploy.getName()).append("<br>应用:").append(app.getName());
            sendMsg("下发停止命令", MsgType.INFO);
            //停止应用
            stopApp(app.getPort(), executeShellUtil);
            sleep(1);
            boolean result = checkIsRunningStatus(app.getPort(), executeShellUtil);
            if (result) {
                sb.append("<br>关闭失败!");
                sendMsg(sb.toString(), MsgType.ERROR);
            } else {
                sb.append("<br>关闭成功!");
                sendMsg(sb.toString(), MsgType.INFO);
            }
            log.info(sb.toString());
            executeShellUtil.close();
        }
        return "执行完毕";
    }

    @Override
    public String serverReduction(DeployHistory resources) {
        Long deployId = resources.getDeployId();
        Deploy deployInfo = deployMapper.selectById(deployId);
        String deployDate = DateUtil.format(resources.getDeployDate(), DatePattern.PURE_DATETIME_PATTERN);
        App app = appService.getById(deployInfo.getAppId());
        if (app == null) {
            sendMsg("应用信息不存在：" + resources.getAppName(), MsgType.ERROR);
            throw new BadRequestException("应用信息不存在：" + resources.getAppName());
        }
        String backupPath = app.getBackupPath()+FILE_SEPARATOR;
        backupPath += resources.getAppName() + FILE_SEPARATOR + deployDate;
        //这个是服务器部署路径
        String deployPath = app.getDeployPath();
        String ip = resources.getIp();
        ExecuteShellUtil executeShellUtil = getExecuteShellUtil(ip);
        String msg;

        msg = String.format("登陆到服务器:%s", ip);
        log.info(msg);
        sendMsg(msg, MsgType.INFO);
        sendMsg("停止原来应用", MsgType.INFO);
        //停止应用
        stopApp(app.getPort(), executeShellUtil);
        //删除原来应用
        sendMsg("删除应用", MsgType.INFO);
        executeShellUtil.execute("rm -rf " + deployPath + FILE_SEPARATOR + resources.getAppName());
        //还原应用
        sendMsg("还原应用", MsgType.INFO);
        executeShellUtil.execute("cp -r " + backupPath + "/. " + deployPath);
        sendMsg("启动应用", MsgType.INFO);
        executeShellUtil.execute(app.getStartScript());
        sendMsg("应用启动中，请耐心等待启动结果，或者稍后手动查看启动状态", MsgType.INFO);
        int i  = 0;
        boolean result = false;
        // 由于启动应用需要时间，所以需要循环获取状态，如果超过30次，则认为是启动失败
        while (i++ < count){
            result = checkIsRunningStatus(app.getPort(), executeShellUtil);
            if(result){
                break;
            }
            // 休眠6秒
            sleep(6);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("服务器:").append(ip).append("<br>应用:").append(resources.getAppName());
        sendResultMsg(result, sb);
        executeShellUtil.close();
        return "";
    }

    private ExecuteShellUtil getExecuteShellUtil(String ip) {
        ServerDto ServerDto = serverService.findByIp(ip);
        if (ServerDto == null) {
            sendMsg("IP对应服务器信息不存在：" + ip, MsgType.ERROR);
            throw new BadRequestException("IP对应服务器信息不存在：" + ip);
        }
        return new ExecuteShellUtil(ip, ServerDto.getAccount(), ServerDto.getPassword(),ServerDto.getPort());
    }

    private ScpClientUtil getScpClientUtil(String ip) {
        ServerDto ServerDto = serverService.findByIp(ip);
        if (ServerDto == null) {
            sendMsg("IP对应服务器信息不存在：" + ip, MsgType.ERROR);
            throw new BadRequestException("IP对应服务器信息不存在：" + ip);
        }
        return ScpClientUtil.getInstance(ip, ServerDto.getPort(), ServerDto.getAccount(), ServerDto.getPassword());
    }

    private void sendResultMsg(boolean result, StringBuilder sb) {
        if (result) {
            sb.append("<br>启动成功!");
            sendMsg(sb.toString(), MsgType.INFO);
        } else {
            sb.append("<br>启动失败!");
            sendMsg(sb.toString(), MsgType.ERROR);
        }
    }

}
