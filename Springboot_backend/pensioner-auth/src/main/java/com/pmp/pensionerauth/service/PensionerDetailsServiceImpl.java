package com.pmp.pensionerauth.service;

import java.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pmp.pensionerauth.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerauth.model.DAOUser;
import com.pmp.pensionerauth.model.Pensioner;
import com.pmp.pensionerauth.model.RegisterModel;
import com.pmp.pensionerauth.modelrepo.DAOUserR;
import com.pmp.pensionerauth.proxy.ProcessPensionProxy;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmp.pensionerauth.exception.PensionerDetailsInvalidParameters;

@Service
@Slf4j
public class PensionerDetailsServiceImpl implements PensionerDetailsService{
	
	@Autowired
	private DAOUserR userDao;
	
	@Autowired
	private ProcessPensionProxy proxy;
	
	public void save(RegisterModel register) throws Exception {
		log.info("in service - wP");

		ObjectMapper mapper = new ObjectMapper();
		Pensioner pensioner = mapper.convertValue(register.getPensioner(), Pensioner.class);
		DAOUser newUser = mapper.convertValue(register.getDaoUser(), DAOUser.class);
		proxy.addPensioner(pensioner);
		
		log.info("After proxy");
		try{
			userDao.save(newUser);
		}catch(Exception ex){
			throw new Exception("sql");
		}
		
	}

	public void findUser(Long userId, String password) throws PensionerDetailsNotFoundException {
		log.info("in service - findUser");
		DAOUser user = userDao.findByUserIdAndPassword(userId,password);
		if (user == null) {
			throw new PensionerDetailsNotFoundException("check Credentials");
		}
	}


	public boolean validateDetails(RegisterModel register) throws PensionerDetailsInvalidParameters {
		log.info("in service - validateDetails");
		
		ObjectMapper mapper = new ObjectMapper();
		Pensioner pensioner = mapper.convertValue(register.getPensioner(), Pensioner.class);
		
		LocalDate localdate = pensioner.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		LocalDate dt = LocalDate.of(1975, 12, 31);
		
		if(!localdate.isBefore(dt))
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - dob");
		if (!((Object)pensioner.getAadhar()).getClass().getSimpleName().equals("Long")) 
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - aadhar");
		if(!((Object)pensioner.getAllowances()).getClass().getSimpleName().equals("Long")) 
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - allowances ");
		if(!((Object)pensioner.getBankName()).getClass().getSimpleName().equals("String")) 
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - bankId");
		if(!((Object)pensioner.getDob()).getClass().getSimpleName().equals("Date")) 
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - dob");
		if(!((Object)pensioner.getUsername()).getClass().getSimpleName().equals("String")) 
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - name");
		if(!((Object)pensioner.getPan()).getClass().getSimpleName().equals("String")) 
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - pan");
		if(!((Object)pensioner.getSalaryearned()).getClass().getSimpleName().equals("Long")) 
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - salary");
		if(!((Object)pensioner.isTypepubpri()).getClass().getSimpleName().equals("Boolean")) 
			throw new PensionerDetailsInvalidParameters("Entered value is not valid type - boolean");
		
		return true;
		
	}

}
