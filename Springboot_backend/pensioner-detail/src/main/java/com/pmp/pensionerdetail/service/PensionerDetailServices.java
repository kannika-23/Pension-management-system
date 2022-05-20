package com.pmp.pensionerdetail.service;

import java.util.Map;

import com.pmp.pensionerdetail.exception.PensionerDetailsAlreadyFoundException;
import com.pmp.pensionerdetail.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerdetail.model.PensionerDetails;

public interface PensionerDetailServices {
	
	public String addPensionerDetailsinRepo(PensionerDetails pensionerDetails) throws PensionerDetailsAlreadyFoundException;
	
	public void updatePensionerDetails(PensionerDetails pensionerDetails) throws PensionerDetailsNotFoundException;

	public Map<String, Object> getDetails(Long aadhar) throws PensionerDetailsNotFoundException;
	
}
