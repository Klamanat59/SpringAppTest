package com.example.springApp.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springApp.config.JwtTokenUtil;
import com.example.springApp.entity.User;
import com.example.springApp.model.JwtRequest;
import com.example.springApp.model.JwtResponse;
import com.example.springApp.model.SignupRequest;
import com.example.springApp.repository.UserRepository;
import com.example.springApp.service.JwtUserDetailsService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) throws Exception {
		User user = new User();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		Map<String, Integer> result = new HashMap<String, Integer>();
		String type = "";

		User u = userRepository.findByUsername(signupRequest.getUsername());

		if (u != null) {
			throw new Exception("Username is already taken!");
		}

		String referenceCode = dateFormat.format(date);
		referenceCode = referenceCode + signupRequest.getPhone().substring(6);

		int salary_int = Integer.valueOf(signupRequest.getSalary());

		if (salary_int > 50000) {
			// Platinum.
			type = "Platinum";
		} else if (salary_int >= 30000 && salary_int <= 50000) {
			// Gold.
			type = "Gold";
		} else if (salary_int >= 15000 && salary_int < 30000) {
			// Silver.
			type = "Silver";
		} else {
			// Reject.
			throw new Exception("Invalid user salary.");
		}

		user.setUsername(signupRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
		user.setAddress(signupRequest.getAddress());
		user.setPhone(signupRequest.getPhone());
		user.setReferenceCode(referenceCode);
		user.setType(type);

		userRepository.save(user);

		result.put("id", user.getId());

		return ResponseEntity.ok(result);
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public ResponseEntity<?> currentUserNameSimple(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		String name = principal.getName();
		User user = userRepository.findByUsername(name);

		return ResponseEntity.ok(user);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
