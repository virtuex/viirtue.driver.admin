package org.virtue.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_ITEM_BANK")
public class ItemBank {
    @Id
    //设置主键并且设置主键为自增
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ITEM_BANK_ID")
    private long itemBankId;

    @Column(name = "ITEM_BANK_SUBJECT")
    private String itemBankSubject;

    @Column(name = "ITEM_BANK_CHOICEA")
    private String itemBankChoiceA;

    @Column(name = "ITEM_BANK_CHOICEB")
    private String itemBankChoiceB;

    @Column(name = "ITEM_BANK_CHOICEC")
    private String itemBankChoiceC;

    @Column(name = "ITEM_BANK_CHOICED")
    private String itemBankChoiceD;

    @Column(name = "ITEM_BANK_ANSWER")
    private String itemBankAnswer;

    @Column(name = "ITEM_BANK_KOWNLEDGE_TYPE")
    private int itemBankKownledgeType;

    @Column(name = "ITEM_BANK_DIFFICUT_LEVEL")
    private int itemBankDifficutLevel;

    @Column(name = "ITEM_BANK_SUBJECT_TYPE")
    private int itemBankSubjectType;


}
