package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.StatusDao;
import com.example.demo.entity.Status;
import com.example.demo.repository.StatusRepository;

@Service
public class StatusService implements BaseService<Status> {
	@Autowired
	private StatusDao dao;
	
	@Autowired
    private StatusRepository statusRepository;

	@Override
	public List<Status> findAll() {
		return dao.findAll();
	}

	@Override
	public Status findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}

	@Override
	public void save(Status status) {
		dao.save(status);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
	
	public Status findByUserId(Integer userId) throws DataNotFoundException {
		return dao.findByUserId(userId);
	}

	@Override
	public Status findByUsername(String userName) throws DataNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
	
	public List<Status> getStatusAndUser() {
        return statusRepository.getStatusAndUser();
    }
}
