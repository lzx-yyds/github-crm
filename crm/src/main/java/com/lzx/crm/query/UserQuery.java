package com.lzx.crm.query;

import com.lzx.crm.base.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lizixiang
 * @Date 2022/9/14 21:08
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuery extends BaseQuery {

    private String userName;

    private String email;

    private String phone;
}
