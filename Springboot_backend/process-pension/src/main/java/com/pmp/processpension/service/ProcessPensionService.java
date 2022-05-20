package com.pmp.processpension.service;

import org.springframework.http.ResponseEntity;

import com.pmp.processpension.exception.PensionerDetailNotMatchingException;
import com.pmp.processpension.exception.PensionerDetailsInvalidParameters;
import com.pmp.processpension.model.PensionCharges;
import com.pmp.processpension.model.Pensioner;

public interface ProcessPensionService {

	public Pensioner getDetails(long aadhar);

	public boolean validateDetails(Pensioner pensioner) throws PensionerDetailsInvalidParameters;

	public boolean matchPensionerDetailwithdb( Pensioner pensioner) throws PensionerDetailNotMatchingException;

	public PensionCharges calculatePensionCharges(Pensioner pensioner);

	public ResponseEntity<Object> updatePensioner(Pensioner pensioner);

	public void addPensionerDetails(Pensioner pensioner);
	

}
