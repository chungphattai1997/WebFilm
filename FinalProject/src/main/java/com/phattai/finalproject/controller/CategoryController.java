package com.phattai.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phattai.finalproject.model.Category;
import com.phattai.finalproject.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	// GET all category
	@GetMapping("/getall")
	public List<Category> getAll() {
		return (List<Category>) categoryService.findAll();
	}

	// GET category by id
	@GetMapping("/{id}")
	public Category getById(@PathVariable("id") long id) {
		Category category = categoryService.findById(id);
		if (category == null) {
			return null;
		}
		return category;
	}

	// POST category
	@PostMapping("/add")
	public Category add(@RequestBody Category category) {
		System.out.println("add");
		categoryService.save(category);
		System.out.println("successful");
		return category;
	}

	// PUT category to update
	@PutMapping("/update")
	public Category update(@RequestBody Category category) {
		Category temp = categoryService.findById(category.getId());
		if (temp != null) {
			categoryService.save(category);
			return category;
		}
		return temp;
	}

	// DELETE category by id
	@DeleteMapping("/delete/{id}")
	public String deleteById(@PathVariable("id") long id) {
		Category category = categoryService.findById(id);
		if (category == null) {
			return "Not found ID";
		}
		categoryService.delete(id);
		return "Delete success";
	}

}
