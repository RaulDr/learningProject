package dirtyBits.learningProject.service;

import dirtyBits.learningProject.view.UserRegisterView;
import dirtyBits.learningProject.view.UserView;
import dirtyBits.learningProject.view.UserViewList;
import javassist.NotFoundException;

public interface UserService {

	void register(UserRegisterView userRegisterView);
	
	UserViewList getAll();
	
	UserView getUserById(long id) throws NotFoundException;
	
	void updateUser(long id, UserRegisterView userRegisterView) throws NotFoundException;
	
	void deleteUserById(long id);
}
