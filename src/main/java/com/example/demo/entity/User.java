package com.example.demo.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.common.PasswordHasher;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.common.ValidationGroups.Update;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends AbstractEntity implements UserDetails {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 255, nullable = false, unique = true)
	@NotEmpty(groups = { Create.class, Update.class }, message = "ユーザ名は必須項目です")
	private String userName;

	@Column(length = 255, nullable = false)
	@NotEmpty(groups = { Create.class }, message = "パスワードは必須項目です")
	private String password;
	
	@Column(length = 255, nullable = false)
	private String name;

	@Transient
	private Boolean auth;

	// 平文を暗号文にしてpasswordに設定する
	public void encodePassword(String rawPassword) {
		if (!"".equals(rawPassword)) {
			this.setPassword(PasswordHasher.create(rawPassword));
		}
	}

	// ユーザーに与えられる権限リストを返却するメソッド
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return null;
	}
	
	// ユーザー名を返却するメソッド
	@Override
	public String getUsername() {
		return this.userName;
	}
	
	// パスワードを返却するメソッド
	@Override
	public String getPassword() {
		return this.password;
	}

	// アカウントの有効期限の状態を判定するメソッド
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// アカウントのロック状態を判定するメソッド
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 資格情報の有効期限の状態を判定するメソッド
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 有効なユーザーか判定するメソッド
	@Override
	public boolean isEnabled() {
		return true;
	}
}
