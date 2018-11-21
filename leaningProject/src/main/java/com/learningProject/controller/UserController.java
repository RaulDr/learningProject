package com.learningProject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learningProject.service.UserService;
import com.learningProject.view.SimpleSuccesResponseView;
import com.learningProject.view.UserRegisterView;
import com.learningProject.view.UserView;
import com.learningProject.view.UserViewList;

import javassist.NotFoundException;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/getDummy")
	public String getDummy() {
		System.out.println("fuckkkkkkkkkkk!");
		return "dummy";
	}

	@PostMapping(value = "/register/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> registerUser(@RequestBody UserRegisterView userRegisterView) {
		userService.register(userRegisterView);
		return new ResponseEntity<SimpleSuccesResponseView>(
				new SimpleSuccesResponseView(String.format("User %s created!", userRegisterView.getUsername())),
				HttpStatus.CREATED);
	}

	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getUsers() {
		return new ResponseEntity<UserViewList>(userService.getAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getUserById(@PathVariable("id") long id) throws NotFoundException {
		return new ResponseEntity<UserView>(userService.getUserById(id), HttpStatus.OK);
	}

	// TODO: To ask about produces and consumes!
	@PutMapping(value = "/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserRegisterView userRegisterView) throws NotFoundException {
		userService.updateUser(id, userRegisterView);
		return new ResponseEntity<SimpleSuccesResponseView>(
				new SimpleSuccesResponseView(String.format("User %s was updated!", userRegisterView.getUsername())),
				HttpStatus.OK);
	}
	
	@DeleteMapping(value = "user/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable long id){
		userService.deleteUserById(id);
		return new ResponseEntity<SimpleSuccesResponseView>(
				new SimpleSuccesResponseView(String.format("User with id %s was deleted!", id)), HttpStatus.GONE);
	}
	
}
