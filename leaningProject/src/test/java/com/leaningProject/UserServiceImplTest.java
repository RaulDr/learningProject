package com.leaningProject;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.learningProject.model.User;
import com.learningProject.repository.UserRepository;
import com.learningProject.service.UserServiceImpl;
import com.learningProject.view.UserRegisterView;
import com.learningProject.view.UserView;
import com.learningProject.view.UserViewList;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Test
	public void registerTest() {
		User user = new User("user1", "password");
		Mockito.when(userRepository.save(user)).thenReturn(user);
		userServiceImpl.register(new UserRegisterView("user1", "password"));
		Mockito.verify(userRepository).save(user);
	}

	@Test
	public void getAllTest() {
		List<User> users = UtilForTests.getUserList();
		UserViewList userViewList = UtilForTests.getUserViewList();
		Mockito.when(userRepository.findAll()).thenReturn(users);
		assertEquals(userViewList, userServiceImpl.getAll());
	}

	@Test
	public void getUserByIdTest() throws NotFoundException {
		User user = new User(1l, "userTest", "password");
		UserView userView = new UserView(1l, "userTest");
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
		assertEquals(userView, userServiceImpl.getUserById(1l));
	}

	@Test(expected = NotFoundException.class)
	public void getUserByIdTestWhenExpectException() throws NotFoundException {
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.empty());
		userServiceImpl.getUserById(1l);
	}
	
	@Test
	public void updateUserTest() throws NotFoundException {
		User user = new User(1l, "userTest", "password");
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(new User(1l, "oldUser", "password")));
		userServiceImpl.updateUser(1l, new UserRegisterView("userTest", "password"));
		Mockito.verify(userRepository).save(user);
		Mockito.verify(userRepository).findById(1l);
	}
	
	@Test(expected = NotFoundException.class)
	public void updateUserTestWhenExpectException() throws NotFoundException{
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.empty());
		userServiceImpl.updateUser(1l,new UserRegisterView("userTest", "password"));
	}
	
	@Test
	public void deleteUserByIdTest() {
		Mockito.doNothing().when(userRepository).deleteById(1l);
		userServiceImpl.deleteUserById(1l);
		Mockito.verify(userRepository).deleteById(1l);
	}
}
