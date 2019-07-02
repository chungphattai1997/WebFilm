package com.phattai.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phattai.finalproject.dto.UserDTO;
import com.phattai.finalproject.model.User;
import com.phattai.finalproject.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// GET all user
	@GetMapping("/getall")
	public List<UserDTO> getAll() {
		List<User> listUser = (List<User>) userService.findAll();
		List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
		listUser.forEach(user -> {
			UserDTO userDTO = userService.convert(user);
			listUserDTO.add(userDTO);
		});
		return listUserDTO;
	}

	// GET user by user name
	@GetMapping
	public UserDTO getByUsername(@RequestParam String username) {
		User user = userService.findById(username);
		if (user == null) {
			return null;
		}
		UserDTO userDTO = userService.convert(user);
		return userDTO;
	}

	// POST user
	@PostMapping("/add")
	public String add(@RequestBody User user) {
		if (userService.findById(user.getUsername()) != null) {
			return "Username already exist";
		}
		String pwd = user.getPassword();
		String encryptPwd = passwordEncoder.encode(pwd);
		user.setPassword(encryptPwd);
		userService.save(user);
		return "Created " + user.getUsername();
	}

	// PUT user to update
	@PutMapping("/update")
	public String update(@RequestBody User user) {
		if (userService.findById(user.getUsername()) == null) {
			return "Username not found";
		}
		userService.save(user);
		return "Updated " + user.getUsername();
	}

	// DELETE user by username
	@DeleteMapping("/delete/{username}")
	public String deleteById(@PathVariable("username") String username) {
		User user = userService.findById(username);
		if (user == null) {
			return "Username not found";
		}
		userService.delete(username);
		return "Deleted " + user.getUsername();
	}

	// Check login
	@GetMapping("/login")
	public ResponseEntity<UserDTO> checkLogin(@RequestParam String username, @RequestParam String password) {
		User user = userService.findById(username);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			if (!user.getPassword().equals(password)) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			UserDTO userDTO = getByUsername(username);
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		}
	}

}
