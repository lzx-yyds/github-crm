package com.lzx.crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer id;

    private String khno;

    private String name;

    private String area;

    private String cusManager;

    private String level;

    private String myd;

    private String xyd;

    private String address;

    private String postCode;

    private String phone;

    private String fax;

    private String webSite;

    private String yyzzzch;

    private String fr;

    private String zczj;

    private String nyye;

    private String khyh;

    private String khzh;

    private String dsdjh;

    private String gsdjh;

    private Integer state;

    private Integer isValid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;


}