package org.virtue.constant;

public enum ItemType {
    CHOICE(1,"选择题"),
    JUDGE(2,"判断题");
    private int id;
    private String desc;
    ItemType(int id,String desc){
        this.id=id;
        this.desc=desc;
    }

    public ItemType getItemTypeById(int id){
        ItemType[] values = ItemType.values();
        for(ItemType value:values){
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
