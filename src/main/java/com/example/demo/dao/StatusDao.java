package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Status;
import com.example.demo.repository.StatusRepository;

@Repository
public class StatusDao implements BaseDao<Status> {
	@Autowired
	StatusRepository repository;

	@Override
	public List<Status> findAll() {
		return repository.findAll();
	}

	@Override
	public Status findById(Integer id) throws DataNotFoundException {
		return repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}

	@Override
	public void save(Status status) {
		this.repository.save(status);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Status status = this.findById(id);
			this.repository.deleteById(status.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}
}
