package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "message")
public class Message extends AbstractEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@JoinColumn(name = "user_id")
	@NotNull(message = "ユーザIDは必須入力です")
	private Integer toUserId;

	@Column(length = 255)
	@NotEmpty(message = "相手先部門は必須入力です")
	private String passSec;

	@Column(length = 255)
	@NotEmpty(message = "相手先電話は必須入力です")
	private String passTel;

	@Column(length = 255)
	@NotEmpty(message = "相手先名前は必須入力です")
	private String passName;

	@Column(nullable = false)
	@NotNull(message = "伝言区分は必須入力です")
	private Integer msec;

	@Column(length = 255)
	private String note;

	@Column(nullable = false)
	@JoinColumn(name = "user_id")
	@NotNull(message = "受付ユーザIDは必須入力です")
	private Integer fromUserId;

}
