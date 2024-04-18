package com.example.demo.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.CustomUser;
import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;

@Controller
@RequestMapping(value = { "/admin/message" })
public class MessageController {
	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	/*
	 * 伝言登録画面表示
	 */
	@GetMapping(value = "/add/{toUserId}")
	public String form(Message message, @PathVariable Integer toUserId, Model model,
			RedirectAttributes ra, @AuthenticationPrincipal CustomUser user) {
		try {
			// 受付ユーザ(ログインユーザ)
			User fromUser = user.getUser();
			message.setFromUserId(fromUser.getId());
			// 宛先ユーザ
			User toUser = userService.findById(toUserId);
			message.setToUserId(toUser.getId());
			model.addAttribute("toUser", toUser);
			model.addAttribute("message", message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "admin/message/add";
	}
	
	/*
	 * 伝言登録
	 */
	@PostMapping(value = "/add")
	public String add(@Validated(Create.class) Message message, BindingResult result, Model model,
			RedirectAttributes ra, @AuthenticationPrincipal CustomUser user) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				model.addAttribute("message", message);
				return "/admin/message/add";
			}
			// 新規登録
			messageService.save(message);
			flash = new FlashData().success("新規作成しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/status";
	}
}