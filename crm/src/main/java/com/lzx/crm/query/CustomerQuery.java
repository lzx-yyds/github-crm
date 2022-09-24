package com.lzx.crm.query;

import com.lzx.crm.base.BaseQuery;
import lombok.Data;

/**
 * @Author lizixiang
 * @Date 2022/9/20 20:00
 * @Description:
 */
@Data
public class CustomerQuery extends BaseQuery {

    private String customerName;
    private String customerNo;
    private String level;// 客户级别
}
