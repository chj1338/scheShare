package com.share.calendar.sch.Vo;

import java.util.List;


public class SchListVo extends SchVo {
    
    private List<SchVo> list;

    public List<SchVo> getList() {
        return list;
    }

    public void setList(List<SchVo> list) {
        this.list = list;
    }

    public SchListVo(List<SchVo> list) {
        super();
        this.list = list;
    }
}
