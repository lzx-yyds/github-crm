package com.lzx.crm.query;

import com.lzx.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lizixiang
 * @Date 2022/9/17 21:09
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleQuery extends BaseQuery {

    private String roleName;
}
