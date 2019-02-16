package org.virtue.dao;

import javax.persistence.*;

/**
 * 设置实体类   加上注解后 在项目启动的时候自动生成数据库表
 */
@Entity
public class User {
	@Id
	//设置主键并且设置主键为自增
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;


	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private Integer role;//1,管理员。2，仓库。3，客服

	private Integer status;//1.禁用  2.启用

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", role='" + role + '\'' +"status="+status+
				'}';
	}
}
