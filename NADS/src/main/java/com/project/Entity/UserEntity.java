package com.project.Entity;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_info")
public class UserEntity {
	
	@Id
	@Column(name="user_id")
	private String id;
	
	private String pw;
	private String name;
	
	@Column(name="e_mail")
	private String mail;
	
	@Column(name="phone_number")
	private String phone;
	
	@Column(name="joined_at")
	private java.sql.Timestamp joinDate;
	
	@Column(name="ip_add")
	private String ip;
	
	private String grade;
	
}
