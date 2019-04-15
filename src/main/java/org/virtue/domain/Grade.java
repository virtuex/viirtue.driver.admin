package org.virtue.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_GRADE")
public class Grade {
    @Id
    //设置主键并且设置主键为自增
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GRADE_ID")
    private long gradeId;
    @Column(name = "GRADE_USER_GRADE")
    private int userGrade;
    @Column(name = "GRADE_RIGHT_ITEM_IDS")
    private String rightIds;
    @Column(name = "GRADE_ERROR_ITEM_IDS")
    private String errorIds;
    @Column(name = "GRADE_USER_ID")
    private long userId;
    @Column(name = "GRADE_DATE")
    private String date;


    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }

    public int getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(int userGrade) {
        this.userGrade = userGrade;
    }

    public String getRightIds() {
        return rightIds;
    }

    public void setRightIds(String rightIds) {
        this.rightIds = rightIds;
    }

    public String getErrorIds() {
        return errorIds;
    }

    public void setErrorIds(String errorIds) {
        this.errorIds = errorIds;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
