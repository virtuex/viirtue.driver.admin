package org.virtue.domain;

import lombok.Data;

import javax.persistence.*;

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
    private String userProvince;

    @Column(name = "USER_CITY")
    private String city;

    @Column(name = "USER_TYPE")
    private int userType;

    @Column(name = "USER_BIRTH")
    private String userBirth;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }
}
