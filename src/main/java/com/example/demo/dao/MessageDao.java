package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.entity.Message;
import com.example.demo.repository.MessageRepository;

@Repository
public class MessageDao implements BaseDao<Message> {
	@Autowired
	MessageRepository repository;

	@Override
	public List<Message> findAll() {
		return repository.findAll();
	}

	@Override
	public Message findById(Integer id) throws DataNotFoundException {
		return this.repository.findById(id).orElseThrow(() -> new DataNotFoundException());
	}
	
	public List<Message> findByToUserId(Integer toUserId) {
		System.out.println(toUserId);
		return this.repository.findByToUserId(toUserId);
	}

	@Override
	public void save(Message message) {
		this.repository.save(message);
	}

	@Override
	public void deleteById(Integer id) {
		try {
			Message message = this.findById(id);
			this.repository.deleteById(message.getId());
		} catch (DataNotFoundException e) {
			System.out.println("no data");
		}
	}

}