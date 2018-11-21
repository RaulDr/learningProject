package com.learningProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learningProject.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
