package com.learningProject.service;

import com.learningProject.view.UserRegisterView;
import com.learningProject.view.UserView;
import com.learningProject.view.UserViewList;

import javassist.NotFoundException;

public interface UserService {

	boolean register(UserRegisterView userRegisterView);
	
	UserView getUserById(Long id) throws NotFoundException;
	
	UserViewList getAll();
	
	boolean updateUser(long id, UserRegisterView userRegisterView) throws NotFoundException;
	
	boolean deleteUserById(long id);

	UserViewList getUserByUsername(String username);
}
	
