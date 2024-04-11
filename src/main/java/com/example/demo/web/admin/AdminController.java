package com.example.demo.web.admin;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	/*
	 * ダッシュボード表示
	 */
	@GetMapping(path = {"/", ""})
	public String list(Model model) {
		// ダッシュボードで表示するリンク
		Map<String, String> links = new LinkedHashMap<String, String>();
		links.put("/admin/status","在籍情報一覧");
//		links.put("/admin/message","伝言メモ");
		links.put("/admin/user","ユーザ一覧");
		model.addAttribute("links", links);
		return "admin/admin/list";
	}
}
