package dirtyBits.learningProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import dirtyBits.learningProject.controller.UserController;
import dirtyBits.learningProject.repository.UserRepository;
import dirtyBits.learningProject.service.UserService;
import dirtyBits.learningProject.service.UserServiceImpl;

@Configuration
@Import({JpaConfig.class})
public class Config {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl(userRepository);
	}
	
	@Bean
	public UserController userController() {
		return new UserController(userService());
	}
}
