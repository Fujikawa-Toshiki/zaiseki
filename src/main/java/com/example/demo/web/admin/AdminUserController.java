package com.example.demo.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Update;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
	@Autowired
	UserService userService;

	/*
	 * 一覧表示
	 */
	@GetMapping(path = {"", "/"})
	public String list(Model model) {
		// 全件取得
		List<User> users = userService.findAll();
		model.addAttribute("user", users);
		return "admin/user/list";
	}

	/*
	 * ログインユーザの編集画面表示
	 */
	@GetMapping(value = "/edit")
	public String edit(Model model, RedirectAttributes ra) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User editUser;
		try {
			editUser = userService.findByUsername(userName);
			// パスワードは表示しないので、空にする
			editUser.setPassword("");
		} catch (DataNotFoundException e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin";
		}
		model.addAttribute(editUser);
		return "admin/user/edit";
	}

	/*
	 * 更新
	 */
	@PostMapping(value = "/edit")
	public String update(@Validated(Update.class) User editUser, BindingResult result, Model model, RedirectAttributes ra) {
		// SpringSecurity側からログインユーザの情報を取得する
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		FlashData flash;
		try {
			User authUser = userService.findByUsername(userName);
			if (result.hasErrors()) {
				model.addAttribute(editUser);
				return "admin/user/edit";
			}
			if (!authUser.getUsername().equals(editUser.getUsername()) && !userService.isUnique(editUser.getUsername())) {
				// user_nameが重複している
				flash = new FlashData().danger("ユーザIDが重複しています");
				model.addAttribute("flash", flash);
				return "admin/user/edit";    
			}
			// リクエスト値とマージ
			authUser.setUserName(editUser.getUsername());
			authUser.encodePassword(editUser.getPassword());
			userService.save(authUser);
			flash = new FlashData().success("更新しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません!");
		} catch (Exception e) {
			flash = new FlashData().danger("エラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin";
	}

}
