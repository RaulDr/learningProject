package com.learningProject.service.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.learningProject.model.User;
import com.learningProject.repository.UserRepository;

public class Mutation implements GraphQLMutationResolver{

	private UserRepository userRepository;
	
	public Mutation (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User saveUser(String username, String password) {
		return userRepository.save(new User(username, password));
	}
}
