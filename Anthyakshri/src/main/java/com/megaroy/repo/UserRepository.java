package com.megaroy.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.megaroy.model.User;

public interface UserRepository extends JpaRepository<User,String>{

	User findByUserName(String userName);
	User findByUserEmail(String userEmail);
	User findByUserEmailOrUserPhono(String emailOrPhno,String emailorPhno);

}
