package org.virtue.constant;

public enum KonwLedgeEnum {
    SIGNAL(1,"信号"),
    TIME(2,"时间"),
    FINE(3,"罚款"),
    DISTANCE(4,"距离"),
    SPEED(5,"速度"),
    DRINK(6,"酒驾");
    private int id;
    private String desc;
    KonwLedgeEnum(int id,String desc){
        this.id=id;
        this.desc=desc;
    }

    public KonwLedgeEnum getKnowEnumById(int id){
        KonwLedgeEnum[] values = KonwLedgeEnum.values();
        for(KonwLedgeEnum value:values){
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
