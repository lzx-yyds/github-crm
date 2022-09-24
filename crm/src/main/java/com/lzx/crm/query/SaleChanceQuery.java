package com.lzx.crm.query;

import com.lzx.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleChanceQuery extends BaseQuery {

    // 营销机会管理
    private String customerName;
    private String createMan;
    private Integer state;

    // 客户开发计划
    private String devResult;
    private Integer assignMan;
}
