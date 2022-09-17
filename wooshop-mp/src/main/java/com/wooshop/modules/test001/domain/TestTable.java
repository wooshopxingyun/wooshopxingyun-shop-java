

package com.wooshop.modules.test001.domain;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.hutool.core.bean.BeanUtil;

import com.wooshop.domain.BaseDomain;

import javax.validation.constraints.*;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;





/**
* @author woo
* @date 2021-10-26
* 注意：
* 本软件为www.wooshop.com开发研制，未经购买不得使用
* 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
* 一经发现盗用、分享等行为，将追究法律责任，后果自负
*/


@Data
@TableName("test_table")
public class TestTable extends BaseDomain {
    @TableId
    private Long id;

    /** 测试名称 */
    @NotBlank
    private String test;


    public void copy(TestTable source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
