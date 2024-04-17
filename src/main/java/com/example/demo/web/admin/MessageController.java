package com.example.demo.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.common.CustomUser;
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
	 * 伝言登録画面
	 */
	@GetMapping(value = "/add/{toUserId}")
	public String form(Message message, @PathVariable Integer toUserId, Model model, @AuthenticationPrincipal CustomUser user, RedirectAttributes ra) {
		try {
			User fromUser = user.getUser();
			User toUser = userService.findById(toUserId);
//			List<Tweet> tweetlist = tweetService.findByUserId(userId);
//			model.addAttribute("user", tweetUser);
//			var list = tweetService.exchangeTweetInfoList(tweetlist, loginUser.getId());
//			model.addAttribute("userTweetList", list);
			model.addAttribute("message", message);
			model.addAttribute("fromUser", fromUser);
			model.addAttribute("toUser", toUser);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "admin/message/add";
	}
}