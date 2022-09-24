package com.lzx.crm.query;

import com.lzx.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lizixiang
 * @Date 2022/9/10 12:51
 * @Description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CusDevPlanQuery extends BaseQuery {

    private Integer saleChanceId; // 营销机会的主键
}
