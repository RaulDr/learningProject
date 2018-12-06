package com.learningProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.remoting.soap.SoapFaultException;
import org.springframework.stereotype.Service;

import com.learningProject.model.User;
import com.learningProject.repository.UserRepository;
import com.learningProject.view.UserRegisterView;
import com.learningProject.view.UserView;
import com.learningProject.view.UserViewList;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import javassist.NotFoundException;
import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@GraphQLMutation(name = "registerUser")
	public boolean register(@GraphQLArgument(name = "userRegisterView") UserRegisterView userRegisterView) {
		userRepository.save(convertUserRegisterViewTo(userRegisterView));
		return true;
	}

	@Override
	@GraphQLQuery(name = "getUsers")
	public UserViewList getAll() {
		return convertUsersTo(userRepository.findAll());
	}
	
	@Override
	@GraphQLQuery(name = "userById")
	public UserView getUserById(@GraphQLArgument(name = "id") Long id) throws NotFoundException {
		return userRepository.findById(id).map(this::convertUserTo)
				.orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
	}

	@Override
	@GraphQLQuery(name = "userByUsername")
	public UserViewList getUserByUsername(@GraphQLArgument(name = "username") String username){
		System.out.println(username);
		List<User> users = userRepository.findByUsername(username);
		System.out.println(users);
		return convertUsersTo(userRepository.findByUsername(username));
		
	}
	
	@Override
	@GraphQLMutation(name = "updateUser")
	public boolean updateUser(@GraphQLArgument(name = "id") long id, @GraphQLArgument(name = "userRegisterView") UserRegisterView userRegisterView) throws NotFoundException {
		userRepository.findById(id).map(u -> userRepository.save(updateUser(u, userRegisterView)))
				.orElseThrow(() -> new NotFoundException(String.format("Pimp with id: %s not found!", id)));
		return true;
	}

	@Override
//	@GraphQLMutation(name = "deleteUser")
	public void deleteUserById(long id) {
		userRepository.deleteById(id);
	}
	
	private User convertUserRegisterViewTo(UserRegisterView userRegisterView) {
		return new User(userRegisterView.getUsername(), RandomString.make());
	}

	private UserViewList convertUsersTo(List<User> users) {
		return new UserViewList(
				users.stream().map((u) -> convertUserTo(u)).collect(Collectors.toList()));
	}

	private UserView convertUserTo(User user) {
		return new UserView(user.getId(), user.getUsername());
	}

	private User updateUser(User user, UserRegisterView userRegisterView) {
		return new User(user.getId(), userRegisterView.getUsername(), userRegisterView.getPassword());
	}
}
