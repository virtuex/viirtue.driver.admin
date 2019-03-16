package org.virtue.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 收藏表
 */
@Data
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
}
