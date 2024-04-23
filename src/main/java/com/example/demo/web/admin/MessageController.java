package com.example.demo.web.admin;

import java.util.List;

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
import com.example.demo.common.DataNotFoundException;
import com.example.demo.common.FlashData;
import com.example.demo.common.ValidationGroups.Create;
import com.example.demo.common.ValidationGroups.Update;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.service.MessageService;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = { "/admin/message" })
public class MessageController {
	@Autowired
	MessageService messageService;

	@Autowired
	UserService userService;

	/*
	 * 一覧表示
	 */
	@GetMapping(path = {"", "/"})
	public String list(Model model, @AuthenticationPrincipal CustomUser user) {
		try {
			// 自分宛てのメッセージを全件取得
			User loginUser = user.getUser();
			List<Message> messageList = messageService.findByToUserId(loginUser.getId());
			model.addAttribute("messageList", messageList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "admin/message/list";
	}
	
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
				// 宛先ユーザ
				User toUser = userService.findById(message.getToUserId());
				message.setToUserId(toUser.getId());
				model.addAttribute("toUser", toUser);
				model.addAttribute("message", message);
				return "/admin/message/add";
			}
			// 新規登録
			messageService.save(message);
			flash = new FlashData().success("伝言メモを登録しました");
		} catch (Exception e) {
			flash = new FlashData().danger("処理中にエラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/status";
	}
	
	/*
	 * 伝言編集画面表示
	 */
	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		try {
			Message message = messageService.findById(id);
			User toUser = userService.findById(message.getToUserId());
			model.addAttribute("message", message);
			model.addAttribute("toUser", toUser);
		} catch (Exception e) {
			FlashData flash = new FlashData().danger("該当データがありません");
			ra.addFlashAttribute("flash", flash);
			return "redirect:/admin/status";
		}
		return "admin/message/edit";
	}
	
	/*
	 * 伝言更新
	 */
	@PostMapping(value = "/edit/{id}")
	public String update(@Validated(Update.class) @PathVariable Integer id, @Valid Message message, BindingResult result, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			if (result.hasErrors()) {
				User toUser = userService.findById(message.getToUserId());
				model.addAttribute("toUser", toUser);
				return "admin/message/edit";
			}
			messageService.findById(id);
			// 更新
			messageService.save(message);
			flash = new FlashData().success("伝言を更新しました");
		} catch (Exception e) {
			flash = new FlashData().danger("該当データがありません");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/message";
	}
	
	/*
	 * 伝言削除
	 */
	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable Integer id, Model model, RedirectAttributes ra) {
		FlashData flash;
		try {
			messageService.findById(id);
			messageService.deleteById(id);
			flash = new FlashData().success("伝言を削除しました");
		} catch (DataNotFoundException e) {
			flash = new FlashData().danger("該当データがありません");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			flash = new FlashData().danger("エラーが発生しました");
		}
		ra.addFlashAttribute("flash", flash);
		return "redirect:/admin/message/";
	}
}