package com.learningProject.service;

import com.learningProject.view.UserRegisterView;
import com.learningProject.view.UserView;
import com.learningProject.view.UserViewList;

import javassist.NotFoundException;

public interface UserService {

	void register(UserRegisterView userRegisterView);
	
	UserViewList getAll();
	
	UserView getUserById(long id) throws NotFoundException;
	
	void updateUser(long id, UserRegisterView userRegisterView) throws NotFoundException;
	
	void deleteUserById(long id);
}
