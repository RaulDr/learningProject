package com.leaningProject;

import java.util.ArrayList;
import java.util.List;

import com.learningProject.model.User;
import com.learningProject.view.UserView;
import com.learningProject.view.UserViewList;

public class UtilForTests {
	
	public static List<User> getUserList(){
		List<User> users = new ArrayList<>();
		users.add(new User(1l, "user1", "password"));
		users.add(new User(2l, "user2", "password"));
		users.add(new User(3l, "user3", "password"));
		return users;
	}

	public static UserViewList getUserViewList() {
		List<UserView> users = new ArrayList<UserView>();
		users.add(new UserView(1l, "user1"));
		users.add(new UserView(2l, "user2"));
		users.add(new UserView(3l, "user3"));
		return new UserViewList(users);
	}
}
