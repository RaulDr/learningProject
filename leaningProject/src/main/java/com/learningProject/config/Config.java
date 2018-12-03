package com.learningProject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.learningProject.controller.RestExceptionHandler;
import com.learningProject.controller.UserController;
import com.learningProject.repository.UserRepository;
import com.learningProject.service.UserService;
import com.learningProject.service.UserServiceImpl;
import com.learningProject.service.graphql.Mutation;
import com.learningProject.service.graphql.Query;

@Configuration
@Import({JpaConfig.class, SecurityConfig.class})
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
	
//	@Bean
//	public GraphQLController graphQLController() {
//		return new GraphQLController(userService());
//	}
	
	@Bean
	public RestExceptionHandler restExceptionHandler () {
		return new RestExceptionHandler();
	}
	
	@Bean
	public Query query() {
		return new Query(userRepository);
	}
	
	@Bean
	public Mutation mutation() {
		return new Mutation(userRepository);
	}
}
