package org.virtue.constant;

import lombok.Data;


public enum DiffLevelEnum {
    ERROR_PRONE_PROBLEM(1,"易错题"),
    COMMON_ITEM(2,"一般题"),
    EASY_ITEM(3,"简单题");
    private int id;
    private String desc;
    DiffLevelEnum(int id,String desc){
        this.id = id;
        this.desc=desc;
    }
    public DiffLevelEnum getLevelById(int id){
        DiffLevelEnum[] values = DiffLevelEnum.values();
        for(DiffLevelEnum value:values){
            if(id==value.getId()){
                return value;
            }
        }
        return null;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
