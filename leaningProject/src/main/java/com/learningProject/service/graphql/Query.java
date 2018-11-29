package com.learningProject.service.graphql;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.learningProject.model.User;
import com.learningProject.repository.UserRepository;

public class Query implements GraphQLQueryResolver{
	
	private UserRepository userRepository;
	
	public Query(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> allUsers(){
		return userRepository.findAll();
	}
}
