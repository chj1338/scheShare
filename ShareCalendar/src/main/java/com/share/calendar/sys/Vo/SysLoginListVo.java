package com.share.calendar.sys.Vo;

import java.util.List;

/**
 * 
 */
public class SysLoginListVo {

    private List<SysLoginVo> list;

    public List<SysLoginVo> getList() {
        return list;
    }

    public void setList(List<SysLoginVo> list) {
        this.list = list;
    }

    public SysLoginListVo(List<SysLoginVo> list) {
        super();
        this.list = list;
    }
}
