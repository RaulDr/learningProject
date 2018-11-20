package dirtyBits.learningProject.view;

import java.util.List;

public class UserViewList {

	private final List<UserView> users;
	
	public UserViewList(List<UserView> users) {
		this.users = users;
	}

	public List<UserView> getUsers() {
		return users;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((users == null) ? 0 : users.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserViewList other = (UserViewList) obj;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserViewList [users=" + users + "]";
	}

	
}
