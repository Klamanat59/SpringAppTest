package com.example.springApp.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springApp.entity.User;
import com.example.springApp.model.UserPrincipal;
import com.example.springApp.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// Let people login with either username or email
		User user = userRepository.findByUsername(usernameOrEmail);

		if (user == null) {
			throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
		}

		return UserPrincipal.create(user);
	}
}
