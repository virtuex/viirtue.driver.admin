package org.virtue.domain;

import lombok.Data;
import org.omg.CORBA.OBJ_ADAPTER;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "ITEM_BANK_ANSWER_ANALYSE",length = 1024)
    private String itemBankAnswerAnalyse;

    @Column(name = "ITEM_BANK_KOWNLEDGE_TYPE")
    private int itemBankKownledgeType;

    @Column(name = "ITEM_BANK_DIFFICUT_LEVEL")
    private int itemBankDifficutLevel;

    @Column(name = "ITEM_BANK_SUBJECT_TYPE")
    private int itemBankSubjectType;

    @Column(name = "ITEM_BANK_IMAGE_URL")
    private String itemBankSubjectImageUrl;

    @Override
    public boolean equals(Object obj) {
        return  this.itemBankId-((ItemBank)obj).itemBankId>0;
    }

    /**
     * 重写hashcode 方法，返回的hashCode不一样才再去比较每个属性的值
     */
    @Override
    public int hashCode() {
        return Long.valueOf(itemBankId).hashCode();
    }

    public boolean isInList(List<ItemBank> banks){
        for(ItemBank bank:banks){
            if(this.itemBankId==bank.getItemBankId()){
                return true;
            }
        }
        return false;
    }


    public long getItemBankId() {
        return itemBankId;
    }

    public void setItemBankId(long itemBankId) {
        this.itemBankId = itemBankId;
    }

    public String getItemBankSubject() {
        return itemBankSubject;
    }

    public void setItemBankSubject(String itemBankSubject) {
        this.itemBankSubject = itemBankSubject;
    }

    public String getItemBankChoiceA() {
        return itemBankChoiceA;
    }

    public void setItemBankChoiceA(String itemBankChoiceA) {
        this.itemBankChoiceA = itemBankChoiceA;
    }

    public String getItemBankChoiceB() {
        return itemBankChoiceB;
    }

    public void setItemBankChoiceB(String itemBankChoiceB) {
        this.itemBankChoiceB = itemBankChoiceB;
    }

    public String getItemBankChoiceC() {
        return itemBankChoiceC;
    }

    public void setItemBankChoiceC(String itemBankChoiceC) {
        this.itemBankChoiceC = itemBankChoiceC;
    }

    public String getItemBankChoiceD() {
        return itemBankChoiceD;
    }

    public void setItemBankChoiceD(String itemBankChoiceD) {
        this.itemBankChoiceD = itemBankChoiceD;
    }

    public String getItemBankAnswer() {
        return itemBankAnswer;
    }

    public void setItemBankAnswer(String itemBankAnswer) {
        this.itemBankAnswer = itemBankAnswer;
    }

    public String getItemBankAnswerAnalyse() {
        return itemBankAnswerAnalyse;
    }

    public void setItemBankAnswerAnalyse(String itemBankAnswerAnalyse) {
        this.itemBankAnswerAnalyse = itemBankAnswerAnalyse;
    }

    public int getItemBankKownledgeType() {
        return itemBankKownledgeType;
    }

    public void setItemBankKownledgeType(int itemBankKownledgeType) {
        this.itemBankKownledgeType = itemBankKownledgeType;
    }

    public int getItemBankDifficutLevel() {
        return itemBankDifficutLevel;
    }

    public void setItemBankDifficutLevel(int itemBankDifficutLevel) {
        this.itemBankDifficutLevel = itemBankDifficutLevel;
    }

    public int getItemBankSubjectType() {
        return itemBankSubjectType;
    }

    public void setItemBankSubjectType(int itemBankSubjectType) {
        this.itemBankSubjectType = itemBankSubjectType;
    }

    public String getItemBankSubjectImageUrl() {
        return itemBankSubjectImageUrl;
    }

    public void setItemBankSubjectImageUrl(String itemBankSubjectImageUrl) {
        this.itemBankSubjectImageUrl = itemBankSubjectImageUrl;
    }
}
