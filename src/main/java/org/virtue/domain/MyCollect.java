package org.virtue.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 收藏表
 */
@Entity
@Table(name = "TB_COLLECT")
public class MyCollect {
    @Id
    //设置主键并且设置主键为自增
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COLLECT_ID")
    private long CollectId;
    @Column(name = "COLLECT_BANK_ID")
    private long itemBankId;
    @Column(name = "COLLECT_USER_ID")
    private long userId;

    public long getCollectId() {
        return CollectId;
    }

    public void setCollectId(long collectId) {
        CollectId = collectId;
    }

    public long getItemBankId() {
        return itemBankId;
    }

    public void setItemBankId(long itemBankId) {
        this.itemBankId = itemBankId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}



