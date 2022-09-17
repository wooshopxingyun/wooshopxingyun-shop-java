package com.wooshop.modules.service;

import com.wooshop.base.CommonService;
import com.wooshop.base.PageInfo;
import com.wooshop.domain.Log;
import com.wooshop.modules.service.dto.LogQueryParam;
//import me.zhengjie.base.CommonService;
//import me.zhengjie.base.PageInfo;
//import me.zhengjie.domain.Log;
//import me.zhengjie.service.dto.LogQueryParam;
//import me.zhengjie.service.dto.LogSmallDTO;
import com.wooshop.modules.service.dto.LogSmallDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
* @author jinjin
* @date 2020-09-27
*/
public interface LogService  extends CommonService<Log> {

    static final String CACHE_KEY = "log";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<Log>
    */
    Object queryAll(LogQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<Log>
    */
    List<Log> queryAll(LogQueryParam query);

    Log findById(Long id);

    /**
     * 查询用户日志
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return -
     */
    PageInfo<LogSmallDTO> queryAllByUser(LogQueryParam criteria, Pageable pageable);

    /**
     * 保存日志数据
     * @param username 用户
     * @param browser 浏览器
     * @param ip 请求IP
     * @param joinPoint /
     * @param log 日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log);

    /**
     * 查询异常详情
     * @param id 日志ID
     * @return Object
     */
    Object findByErrDetail(Long id);

    /**
     * 导出日志
     * @param logs 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<Log> logs, HttpServletResponse response) throws IOException;

    boolean removeByLogType(String logType);

    /**
     * 删除所有错误日志
     */
    void delAllByError();

    /**
     * 删除所有INFO日志
     */
    void delAllByInfo();
}
