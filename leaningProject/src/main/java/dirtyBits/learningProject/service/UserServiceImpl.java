package dirtyBits.learningProject.service;

import dirtyBits.learningProject.model.User;
import dirtyBits.learningProject.repository.UserRepository;
import dirtyBits.learningProject.view.UserRegisterView;

public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void register(UserRegisterView userRegisterView) {
		userRepository.save(convertUserRegisterView(userRegisterView));
	}
	
	private User convertUserRegisterView(UserRegisterView userRegisterView) {
		return new User(userRegisterView.getUsername(), userRegisterView.getPassword());
	}
}
