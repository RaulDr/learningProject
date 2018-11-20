package dirtyBits.learningProject.service;

import java.util.List;
import java.util.stream.Collectors;

import dirtyBits.learningProject.model.User;
import dirtyBits.learningProject.repository.UserRepository;
import dirtyBits.learningProject.view.UserRegisterView;
import dirtyBits.learningProject.view.UserView;
import dirtyBits.learningProject.view.UserViewList;
import javassist.NotFoundException;

public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void register(UserRegisterView userRegisterView) {
		userRepository.save(convertUserRegisterViewTo(userRegisterView));
	}

	@Override
	public UserViewList getAll() {
		return convertUsersTo(userRepository.findAll());
	}

	@Override
	public UserView getUserById(long id) throws NotFoundException {
		return userRepository.findById(id).map(this::convertUserTo)
				.orElseThrow(() -> new NotFoundException(String.format("User with id: %s not found!", id)));
	}

	@Override
	public void updateUser(long id, UserRegisterView userRegisterView) throws NotFoundException {
		userRepository.findById(id).map(u -> userRepository.save(updateUser(u, userRegisterView)))
				.orElseThrow(() -> new NotFoundException(String.format("Pimp with id: %s not found!", id)));
	}

	@Override
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
