package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.entity.Status;
import com.example.demo.entity.User;
import com.example.demo.service.BaseService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admin/status")
public class StatusController {
	@Autowired
	BaseService<Status> statusService;
	
	@Autowired
	UserService userService;

	/*
	 * 一覧表示
	 */
	@GetMapping(path = {"", "/"})
	public String list(Model model, @AuthenticationPrincipal UserDetails user) {
		try {
			// ログインユーザのuser_nameからStatus情報を取得
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			User loginUser = userService.findByUsername(userName);
			Status myStatus = statusService.findByUserId(loginUser.getId());
			
			// 全件取得
			List<Status> status = statusService.findAll();
			model.addAttribute("status", status);
			model.addAttribute("id", myStatus.getId());
		} catch (Exception e) {
			
		}
			return "admin/status/list";
	}
	
	/*
	 * 編集画面表示
	 */
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			Status status = statusService.findById(id);
			model.addAttribute("status", status);
			
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/status";
		}
		return "admin/status/edit";
	}
	
	/*
	 * 編集画面表示
	 */
//	@GetMapping(value = "/edit/{id}")
//	public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
//		try {
//			// 存在確認
//			Status status = statusService.findById(id);
//			model.addAttribute("status", status);
//		} catch (Exception e) {
//			FlashData flash = new FlashData().danger("該当データがありません");
//			ra.addFlashAttribute("flash", flash);
//			return "redirect:/admin/status";
//		}
//		return "admin/status/edit";
//	}

}
