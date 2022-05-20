package com.pmp.pensionerauth.service;

import com.pmp.pensionerauth.exception.PensionerDetailsInvalidParameters;
import com.pmp.pensionerauth.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerauth.model.Pensioner;
import com.pmp.pensionerauth.model.RegisterModel;

public interface PensionerDetailsService {
	
	public void save(RegisterModel register) throws Exception;
	public void findUser(Long userId, String password) throws PensionerDetailsNotFoundException;
	public boolean validateDetails(RegisterModel register) throws PensionerDetailsInvalidParameters;
	

}
