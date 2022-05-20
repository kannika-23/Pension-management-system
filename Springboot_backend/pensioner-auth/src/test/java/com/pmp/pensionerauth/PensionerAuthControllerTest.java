package com.pmp.pensionerauth;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pmp.pensionerauth.controller.PensionerAuthController;
import com.pmp.pensionerauth.exception.PensionerDetailsInvalidParameters;
import com.pmp.pensionerauth.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerauth.jwt.JwtUtil;
import com.pmp.pensionerauth.model.DAOUser;
import com.pmp.pensionerauth.service.PensionerDetailsService;
import com.pmp.pensionerauth.model.Pensioner;
import com.pmp.pensionerauth.model.RegisterModel;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class PensionerAuthControllerTest {

	@Mock 
	private JwtUtil jwtUtil;
	
	@Mock
	private PensionerDetailsService service;
	
	@InjectMocks
	PensionerAuthController pensionerAuthController = new PensionerAuthController();
	
	private DAOUser mockDAOUser(){
		DAOUser user = new DAOUser();
		return user;
		
	}
	private Pensioner mockpensioner(){
		Pensioner pensioner = new Pensioner();
		pensioner.setAadhar(123456788765L);
		return pensioner;
	}
	
	private RegisterModel mockRegiter(){
		RegisterModel RegisterModel  = new RegisterModel ();
		
		return RegisterModel ;
	}
	//createtoken
	@Test
	public void testcreateAuthenticationToken() throws PensionerDetailsNotFoundException{
		
		doNothing().when(service).findUser(Mockito.anyLong(),Mockito.anyString());
		service.findUser(mockDAOUser().getUserId(),mockDAOUser().getPassword());
		
	}
	
	@Test
	public void test1createAuthenticationToken() throws PensionerDetailsNotFoundException{
		
		try {
			doThrow(new PensionerDetailsNotFoundException("already in use")).when(service).findUser(Mockito.anyLong(),Mockito.anyString());
			service.findUser(mockDAOUser().getUserId(),mockDAOUser().getPassword());
		} catch (PensionerDetailsNotFoundException e) {
			assertTrue(e instanceof PensionerDetailsNotFoundException);
		}
	
	}
	//generateToken
	
	@Test
	public void test2createAuthenticationToken() throws PensionerDetailsNotFoundException{
		
		when(jwtUtil.generateToken(Mockito.anyLong())).thenReturn("token1");
		assertFalse("token".equalsIgnoreCase(jwtUtil.generateToken(mockDAOUser().getUserId())));
	}
	
	//pensionerDetailRegisConformation
	@Test
	public void testpensionerDetailRegisConformation() throws PensionerDetailsInvalidParameters{
		
		when(service.validateDetails(Mockito.any(RegisterModel.class))).thenReturn(true);
		assertNotNull(service.validateDetails(mockRegiter()));
	}
	
	@Test(expected =  PensionerDetailsInvalidParameters.class)
	public void test1pensionerDetailRegisConformation() throws PensionerDetailsInvalidParameters{
		
		when(service.validateDetails(Mockito.any(RegisterModel.class))).thenReturn(false);
		if(!service.validateDetails(mockRegiter())){
			throw new  PensionerDetailsInvalidParameters("invalid values");
		}
	}
	
	//save
	@Test
	public void testsaveUser() throws Exception{
		
		
		doNothing().when(service).save(Mockito.any(RegisterModel.class));
		service.save(mockRegiter());
		
	}
	
	@Test
	public void test1saveUser() throws Exception{
		try {
			doThrow(new Exception("sql")).when(service).save(Mockito.any(RegisterModel.class));
			service.save(mockRegiter());
		} catch (Exception e) {
			assertTrue(e instanceof Exception);
		}
	
	}
	

}
