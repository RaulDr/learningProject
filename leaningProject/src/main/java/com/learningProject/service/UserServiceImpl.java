package com.learningProject.service;

import java.util.List;
import java.util.stream.Collectors;

import com.learningProject.model.User;
import com.learningProject.repository.UserRepository;
import com.learningProject.view.UserRegisterView;
import com.learningProject.view.UserView;
import com.learningProject.view.UserViewList;

import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import javassist.NotFoundException;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@GraphQLMutation(name = "registerUser")
	public void register(UserRegisterView userRegisterView) {
		userRepository.save(convertUserRegisterViewTo(userRegisterView));
	}

	@Override
	public UserViewList getAll() {
		return convertUsersTo(userRepository.findAll());
	}
	
	@Override
	@GraphQLQuery(name = "users")
	public List<UserView> getAllForGraphQL() {
		return convertUsersTo(userRepository.findAll()).getUsers();
	}

	@Override
	@GraphQLQuery(name = "user")
	public UserView getUserById(long id) throws NotFoundException {
		return userRepository.findById(id).map(this::convertUserTo)
				.orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
	}

	@Override
	@GraphQLMutation(name = "updateUser")
	public void updateUser(long id, UserRegisterView userRegisterView) throws NotFoundException {
		userRepository.findById(id).map(u -> userRepository.save(updateUser(u, userRegisterView)))
				.orElseThrow(() -> new NotFoundException(String.format("Pimp with id: %s not found!", id)));
	}

	@Override
	@GraphQLMutation(name = "deleteUser")
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}
	
	private User convertUserRegisterViewTo(UserRegisterView userRegisterView) {
		return new User(userRegisterView.getUsername(), userRegisterView.getPassword());
	}

	private UserViewList convertUsersTo(List<User> users) {
		return new UserViewList(
				userRepository.findAll().stream().map((u) -> convertUserTo(u)).collect(Collectors.toList()));
	}

	private UserView convertUserTo(User user) {
		return new UserView(user.getId(), user.getUsername());
	}

	private User updateUser(User user, UserRegisterView userRegisterView) {
		return new User(user.getId(), userRegisterView.getUsername(), userRegisterView.getPassword());
	}
}
