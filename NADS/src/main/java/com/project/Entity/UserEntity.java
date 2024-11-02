package com.project.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_info")
@Getter
@Setter
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
