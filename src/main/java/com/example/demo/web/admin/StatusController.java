package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.common.CustomUser;
import com.example.demo.entity.Status;
import com.example.demo.entity.User;
import com.example.demo.service.StatusService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/status")
public class StatusController {
	@Autowired
	StatusService statusService;
	
	@Autowired
	UserService userService;

	/*
	 * 一覧表示
	 */
	@GetMapping(path = {"", "/"})
	public String list(Model model, @AuthenticationPrincipal CustomUser user) {
		try {
			// ログインユーザのStatus情報を取得
			User loginUser = user.getUser();
			Status myStatus = statusService.findByUserId(loginUser.getId());
			// Userテーブルを結合して全件取得
			List<Status> status = statusService.getStatusAndUser();
//			List<Status> status = statusService.findAll();
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
	public String edit(@PathVariable Integer id, Model model, @AuthenticationPrincipal CustomUser user, RedirectAttributes ra) {
		try {
			User loginUser = user.getUser();
			Status status = statusService.findById(id);
			model.addAttribute("status", status);
			model.addAttribute("user", loginUser);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/status";
		}
		return "admin/status/edit";
	}
	
	/*
	 * 更新
	 */
	@PostMapping(value = "/edit/{id}")
	public String update(@PathVariable Integer id, @Valid Status status, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "admin/status/edit";
			}
			statusService.findById(id);
			// 更新
			statusService.save(status);
			flash = new FlashData().success("更新しました");
		} catch (Exception e) {
			flash = new FlashData().danger("該当データがありません");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/status";
	}

}
