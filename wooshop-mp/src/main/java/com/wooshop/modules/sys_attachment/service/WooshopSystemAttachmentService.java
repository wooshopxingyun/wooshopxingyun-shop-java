
package com.wooshop.modules.sys_attachment.service;


import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import com.wooshop.modules.sys_attachment.service.dto.WooshopSystemAttachmentDto;
import org.springframework.data.domain.Pageable;
import com.wooshop.modules.service.BaseService;

import com.wooshop.domain.PageResult;
import com.wooshop.modules.sys_attachment.domain.WooshopSystemAttachment;

import com.wooshop.modules.sys_attachment.service.dto.WooshopSystemAttachmentQueryCriteria;

/**
* @author woo
* @date 2022-06-02
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


public interface WooshopSystemAttachmentService  extends BaseService<WooshopSystemAttachment>{

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    PageResult<WooshopSystemAttachmentDto>  queryAll(WooshopSystemAttachmentQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<WooshopSystemAttachmentDto>
    */
    List<WooshopSystemAttachment> queryAll(WooshopSystemAttachmentQueryCriteria criteria);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<WooshopSystemAttachmentDto> all, HttpServletResponse response) throws IOException;

    /**
    * 包含新增和更新功能
    * @param resources
    * @return
    */
    PageResult<WooshopSystemAttachmentDto> addAndUpdate(WooshopSystemAttachment resources);


    /**
    * 根据id 进行删除
    * @param id
    */
    void cacheRemoveById(Long id);

    /**
     * 通过附件名称 查询数据
     * @param attName 附件名称
     * @return
     */
    WooshopSystemAttachment findByName(String attName);

    /**
     * 添加附件记录
     * @param name 名称
     * @param attSize 附件大小
     * @param attDir 路径
     * @param sattDir 路径
     */
    void attachmentAdd(String name, String attSize, String attDir,String sattDir);

    /**
     * 删除海报数据
     * @param id 主键id
     * @return
     */
    int delById(Long id);

    /**
     * 添加附件记录
     * @param name 名称
     * @param attSize 附件大小
     * @param attDir 路径
     * @param sattDir 路径
     * @param uid 用户id
     * @param code 邀请码
     */
    void newAttachmentAdd(String name,String attSize,String attDir,String sattDir,Long uid,String code);
}
