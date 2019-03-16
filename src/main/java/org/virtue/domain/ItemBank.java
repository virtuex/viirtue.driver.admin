package org.virtue.domain;

import lombok.Data;
import org.omg.CORBA.OBJ_ADAPTER;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "ITEM_BANK_ANSWER_ANALYSE")
    private String itemBankAnswerAnalyse;

    @Column(name = "ITEM_BANK_KOWNLEDGE_TYPE")
    private int itemBankKownledgeType;

    @Column(name = "ITEM_BANK_DIFFICUT_LEVEL")
    private int itemBankDifficutLevel;

    @Column(name = "ITEM_BANK_SUBJECT_TYPE")
    private int itemBankSubjectType;


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
}
