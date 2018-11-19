package dirtyBits.learningProject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dirtyBits.learningProject.service.UserService;
import dirtyBits.learningProject.view.SimpleSuccesResponseView;
import dirtyBits.learningProject.view.UserRegisterView;

@RestController
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/getDummy")
	public String getDummy() {
		System.out.println("fuckkkkkkkkkkk!");
		return "dummy";
	}

	@PostMapping(value = "/register/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> registerUser(@RequestBody UserRegisterView userRegisterView){
		userService.register(userRegisterView);
		return new ResponseEntity<SimpleSuccesResponseView>(new SimpleSuccesResponseView(String.format("User %s created!", userRegisterView.getUsername())), HttpStatus.CREATED);
	}
	
	
}
