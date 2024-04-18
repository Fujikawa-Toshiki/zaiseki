package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.dao.MessageDao;
import com.example.demo.entity.Message;

@Service
public class MessageService implements BaseService<Message> {
	@Autowired
	private MessageDao dao;

	@Override
	public List<Message> findAll() {
		return dao.findAll();
	}

	@Override
	public Message findById(Integer id) throws DataNotFoundException {
		return dao.findById(id);
	}
	
	public List<Message> findByToUserId(Integer toUserId) {
		return dao.findByToUserId(toUserId);
	}

	@Override
	public void save(Message follow) {
		dao.save(follow);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

}