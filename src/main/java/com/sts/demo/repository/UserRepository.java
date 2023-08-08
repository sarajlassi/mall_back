package com.sts.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sts.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	
	Optional<User> findByEmail(String email);

	User save(User user);

	
}
