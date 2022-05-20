package com.pmp.pensionerauth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pmp.pensionerauth.jwt.JwtRequest;
import com.pmp.pensionerauth.jwt.JwtUtil;
import com.pmp.pensionerauth.model.RegisterModel;
import com.pmp.pensionerauth.service.PensionerDetailsService;

import lombok.extern.slf4j.Slf4j;
import com.pmp.pensionerauth.exception.PensionerDetailsInvalidParameters;


@CrossOrigin
@Slf4j
@RestController
public class PensionerAuthController {
	
	@Autowired 
	private JwtUtil jwtUtil;
	
	@Autowired
	private PensionerDetailsService service;
	
	//login
	@RequestMapping(value = "pmp/pensioner-auth/login", method = RequestMethod.POST)
	public ResponseEntity<Object> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		log.info("inside pensionerAuth - create Token");
		
		service.findUser(authenticationRequest.getUserId(), authenticationRequest.getPassword());

		final String token = jwtUtil.generateToken(authenticationRequest.getUserId());
		
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("responded","pensioner-auth");
	    
	    Map<String, Object> res = new HashMap<String, Object>();
		res.put("Authentication","Bearer "+token);
		
		return new ResponseEntity<>(res,headers, HttpStatus.ACCEPTED);
		
	}
	
	//register precheck
	@PostMapping("pmp/pensioner-auth/registerconformationdetails")
	public ResponseEntity<Object> pensionerDetailRegisConformation(@RequestBody RegisterModel register) throws PensionerDetailsInvalidParameters  {
		
		log.info("inside pensionerAuth - pensionerDetailRegisConformation");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("responded","pensioner-auth");
		
		service.validateDetails(register);
		 
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("pensionerDetails", register.getPensioner());
		
		return new ResponseEntity<Object>(res,headers,HttpStatus.ACCEPTED);
	}

	//register
	@RequestMapping(value = "pmp/pensioner-auth/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody RegisterModel register) throws Exception {
		log.info(" inside pensionerAuth - Register ");
		
		service.save(register);
		HttpHeaders headers = new HttpHeaders();
		headers.add("responded","pensioner-auth");

		Map<String, Object> res = new HashMap<String, Object>();
		res.put("msg","registered");
		
		return new ResponseEntity<>(res,headers, HttpStatus.CREATED);
	}
	

	
}

