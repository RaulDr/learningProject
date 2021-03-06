package com.learningProject.view;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

public class UserRegisterView {

	@JsonProperty("username")
	private final String username;

	@JsonProperty("password")
	private final String password;

	@JsonCreator
	public UserRegisterView(@JsonProperty("username") @GraphQLArgument(name = "username") String username, @JsonProperty("password") @GraphQLArgument(name = "password") String password) {
		this.username = username;
		this.password = password;
	}

	@GraphQLQuery(name = "username")
	public String getUsername() {
		return username;
	}
	
	@GraphQLQuery(name = "password")
	public String getPassword() {
		return password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserRegisterView other = (UserRegisterView) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRegisterView [username=" + username + ", password=" + password + "]";
	}
	
	
}
