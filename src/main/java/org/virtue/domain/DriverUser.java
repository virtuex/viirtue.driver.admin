package org.virtue.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_USER")
public class DriverUser {
    @Id
    //设置主键并且设置主键为自增
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "USER_ID")
    private long id;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_GENDER")
    private int gender;

    @Column(name = "USER_PROVINCE")
    private String province;

    @Column(name = "USER_CITY")
    private String city;

    @Column(name = "USER_TYPE")
    private int userType;

    @Column(name = "USER_AGE")
    private int age;



}
