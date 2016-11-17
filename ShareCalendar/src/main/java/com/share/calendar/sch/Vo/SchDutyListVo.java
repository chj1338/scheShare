package com.share.calendar.sch.Vo;

import java.util.List;


public class SchDutyListVo extends SchDutyVo {
    
    private List<SchDutyVo> list;

    public List<SchDutyVo> getList() {
        return list;
    }

    public void setList(List<SchDutyVo> list) {
        this.list = list;
    }

    public SchDutyListVo(List<SchDutyVo> list) {
        super();
        this.list = list;
    }
}
