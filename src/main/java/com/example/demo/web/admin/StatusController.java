package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Status;
import com.example.demo.service.BaseService;

@Controller
@RequestMapping("/admin/status")
public class StatusController {
	@Autowired
	BaseService<Status> statusService;

	/*
	 * 一覧表示
	 */
	@GetMapping(path = {"", "/"})
	public String list(Model model) {
		// 全件取得
		List<Status> status = statusService.findAll();
		model.addAttribute("status", status);
		return "admin/status/list";
	}
}
