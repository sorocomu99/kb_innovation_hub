package com.kb.inno.admin.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class MenuDTO {
    private int menu_sn;
    private String menu_nm;
    private int menu_depth;
    private int menu_up_sn;
    private int menu_sort;
    private String use_yn;
    private int frst_rgtr;
    private Date frst_reg_dt;
    private int last_mdfr;
    private Date last_mdfcn_dt;
}
