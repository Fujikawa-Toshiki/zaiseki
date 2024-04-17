package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer>{
	public Status findByUserId(Integer userId);
	
	@Query(value = "select s.*, u.name as name "
	        + "from status s "
	        + "left join user u on u.id = s.user_id "
//	        + "where u.user_name = ?1 "
	        + "order by s.id ",
	        nativeQuery = true)
	List<Status> getStatusAndUser();
}
