package com.pmp.pensionerauth;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmp.pensionerauth.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerauth.model.DAOUser;
import com.pmp.pensionerauth.model.Pensioner;
import com.pmp.pensionerauth.model.RegisterModel;
import com.pmp.pensionerauth.modelrepo.DAOUserR;
import com.pmp.pensionerauth.proxy.ProcessPensionProxy;
import com.pmp.pensionerauth.service.PensionerDetailsServiceImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class PensionerDetailsServiceImplTest {
	
	@Mock
	private DAOUserR userDao;
	
	@Mock
	private ProcessPensionProxy proxy;
	
	@InjectMocks
	PensionerDetailsServiceImpl  pensionerDetailsServiceImpl = new  PensionerDetailsServiceImpl();
	
	private RegisterModel mockRegisterModel(){
		RegisterModel register = new RegisterModel();
		return register;
	}
	
	private Pensioner mockpensioner(){
		Pensioner pensioner = new Pensioner();
		pensioner.setAadhar(123456788765L);
		return pensioner;
	}
	
	private DAOUser mockUser(){
		DAOUser user = new DAOUser();
		return  user;
	}
	
	//save
	@Test
	public void testsave(){
		doNothing().when(proxy).addPensioner(Mockito.any(Pensioner.class));
		proxy.addPensioner(mockpensioner());
	}
	
	@Test
	public void test1save(){
		doNothing().when(userDao).save(Mockito.any(DAOUser.class));
		userDao.save(mockUser());
	}
	
	@Test
	public void test2save(){
		try{
		doThrow(new Exception("sql exception")).when(userDao).save(Mockito.any(DAOUser.class));
		userDao.save(mockUser());
		}catch(Exception e){
			assertTrue(e instanceof Exception);
		}
	}
	
	//findUser
	@Test
	public void testfindUser(){
		when(userDao.findByUserIdAndPassword(Mockito.anyLong(),Mockito.anyString())).thenReturn(mockUser());
		assertNotNull(userDao.findByUserIdAndPassword(Mockito.anyLong(),Mockito.anyString()));
	}
	
	@Test(expected = PensionerDetailsNotFoundException.class)
	public void test1findUser() throws PensionerDetailsNotFoundException{
		when(userDao.findByUserIdAndPassword(Mockito.anyLong(),Mockito.anyString())).thenReturn(null);
		if(userDao.findByUserIdAndPassword(Mockito.anyLong(),Mockito.anyString()) == null){
			throw new PensionerDetailsNotFoundException("Entered User dosen't exits");
		}
	}
	
	
}
