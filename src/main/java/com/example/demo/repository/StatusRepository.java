package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Integer>{
	public List<Status> findByUserId(Integer userId);
}
