package com.megaroy.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.megaroy.iservice.IUserService;
import com.megaroy.jwtutil.JwtUtil;
import com.megaroy.model.AuthToken;
import com.megaroy.model.User;

@RestController
@RequestMapping("/anthyakshri")
@CrossOrigin(origins="http://localhost:4200")
public class AuthenticationController {
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	private static final Logger log=LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private JwtUtil jwtTokenUtil;
    
    @Autowired
    private IUserService service;
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user,HttpSession session) throws AuthenticationException {
        log.info("============ Entering into  login method in "+AuthenticationController.log.getName()+"=================");
    	try {
			final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( user.getUserEmail(), user.getUserPwd()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final String token = jwtTokenUtil.generateToken(authentication);
			if(token!=null && !token.isEmpty()) {
				session.setAttribute("userName",user.getUserName());
				session.setAttribute("userEmail",user.getUserEmail());
			}
			return ResponseEntity.ok(new AuthToken(token));
		} catch (AuthenticationException e) {
            log.info("============ Entering into catch block of login method in "+AuthenticationController.log.getName()+"=================");
			return ResponseEntity.ok(e.getMessage());
		}
    }
}
