package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.entity.Status;
import com.example.demo.entity.User;
import com.example.demo.service.StatusService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	StatusService statusService;

	@GetMapping
	public String index() {
		return "index/index";
	}

	/*
	 * 新規作成画面表示
	 */
	@GetMapping(value = "/register")
	public String form(User user, Model model) {
		model.addAttribute("user", user);
		return "user/register";
	}

	/*
	 * 新規登録
	 */
	@PostMapping(value = "/register")
	public String register(@Validated(Create.class) User user, BindingResult result, Model model,
			RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				return "user/register";
			}
			if (!userService.isUnique(user.getUsername())) {
				// user_nameが重複している
				flash = new FlashData().danger("ユーザ名が重複しています");
				model.addAttribute("flash", flash);
				return "user/register";
			}
			// 平文のパスワードを暗号文にする
			user.encodePassword(user.getPassword());
			// 新規登録
			userService.save(user);
			user.setAuth(true);

			// 在席情報の新規登録
			Status status = new Status();
			status.setUser(user);
			status.setPresent(0);
			status.setDestination("");
			status.setReachTime("");
			status.setMemo("");
			statusService.save(status);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/user/login";
	}

	/*
	 * ログイン画面表示
	 */
	@GetMapping(value = "/login")
	public String loginForm(User user, Model model) {
		return "user/login";
	}
}
