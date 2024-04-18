package com.example.demo.entity;

import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.common.ValidationGroups.Update;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "status")
public class Status extends AbstractEntity {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

//	@Column(nullable = false)
//	@JoinColumn(name = "user_id")
//	@NotNull(message = "ユーザIDは必須入力です")
//	private Integer userId;

	@Column(nullable = false)
	@NotNull(message = "在籍状況が未入力です")
	private Integer present;

	@Column(length = 255, nullable = false)
	@NotEmpty(groups = { Create.class, Update.class }, message = "行き先が未入力です")
	private String destination;

	@Column(length = 255, nullable = false)
	private String reachTime;

	@Column(length = 255, nullable = false)
	private String memo;

}
