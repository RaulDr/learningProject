package dirtyBits.leaningProject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import dirtyBits.learningProject.model.User;
import dirtyBits.learningProject.repository.UserRepository;
import dirtyBits.learningProject.service.UserServiceImpl;
import dirtyBits.learningProject.view.UserRegisterView;

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
}
