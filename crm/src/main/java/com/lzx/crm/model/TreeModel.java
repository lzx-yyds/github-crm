package com.lzx.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lizixiang
 * @Date 2022/9/18 11:37
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeModel {

    private Integer id;

    private Integer pId;

    private String name;

    private Boolean checked = false;
}
