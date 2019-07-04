package com.phattai.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public String news() {
		return "News";
	}
}
