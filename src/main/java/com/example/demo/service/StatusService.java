package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.BaseDao;
import com.example.demo.entity.Status;

@Service
public class StatusService implements BaseService<Status> {
	@Autowired
	private BaseDao<Status> dao;

	@Override
	public List<Status> findAll() {
		return dao.findAll();
	}

	@Override
	public Status findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(Status orderDetail) {
		dao.save(orderDetail);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
}
