package org.virtue.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
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
}
