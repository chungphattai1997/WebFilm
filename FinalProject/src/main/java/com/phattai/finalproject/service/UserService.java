package com.phattai.finalproject.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phattai.finalproject.dto.UserDTO;
import com.phattai.finalproject.model.User;
import com.phattai.finalproject.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	// Find all category
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	// Find category by Id
	public User findById(String id) {
		Optional<User> op = userRepository.findById(id);
		if (!op.isPresent()) {
			return null;
		}
		return op.get();
	}

	// Add or update category
	public void save(User user) {
		userRepository.save(user);
	}

	// Delete category
	public void delete(String id) {
		userRepository.deleteById(id);
	}
	
	//Convert User to UserDTO
	public UserDTO convert(User user) {
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		return userDTO;
	}
}
