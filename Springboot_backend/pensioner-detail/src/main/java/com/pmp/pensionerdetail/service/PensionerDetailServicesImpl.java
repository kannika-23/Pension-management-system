package com.pmp.pensionerdetail.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmp.pensionerdetail.exception.PensionerDetailsAlreadyFoundException;
import com.pmp.pensionerdetail.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerdetail.model.PensionerDetails;
import com.pmp.pensionerdetail.model.PensionerDetailsBank;
import com.pmp.pensionerdetail.modelrepo.PensionerDetailsBankRepo;
import com.pmp.pensionerdetail.modelrepo.PensionerDetailsRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PensionerDetailServicesImpl implements PensionerDetailServices {
	
	@Autowired
	private PensionerDetailsRepo repo;
	
	@Autowired
	private PensionerDetailsBankRepo bankrepo;

	//to get details from dao
	public Map<String, Object> getDetails(Long aadhar) throws PensionerDetailsNotFoundException{
		log.info("in PensionerDetailServices - getDetailByAadhar {} ", aadhar);
		
		Map<String, Object> response = new HashMap<String, Object>();
		PensionerDetails pensionerDetails = repo.getByAadhar(aadhar);//
		
		if(pensionerDetails != null) {
			log.info("in PensionerDetailServices - getBankById");
			PensionerDetailsBank pensionerDetailsBank = bankrepo.getByBankId(pensionerDetails.getBankId());//

			response.put("pensionerdetails", pensionerDetails);
			response.put("pensionerdetailsbank", pensionerDetailsBank);
			
		}else {
			log.debug("inside pensionerDetail - get details by aadhar - in else condition");
			throw new PensionerDetailsNotFoundException("you have not registered");
		}
			
		return response;
	}
	
	//to add user to dao
	public String addPensionerDetailsinRepo(PensionerDetails pensionerDetails) throws PensionerDetailsAlreadyFoundException {
		
		log.info("in PensionerDetailServices - addPensionerDetailsinRepo");
		
		String msg = "";
		if(repo.getByAadhar(pensionerDetails.getAadhar()) != null) {
			throw new PensionerDetailsAlreadyFoundException("Already in use - Aadhar Number");
		}
		if(repo.getByAccountNumber(pensionerDetails.getAccountNumber()) != null) {
			throw new PensionerDetailsAlreadyFoundException("Already in use - Account NUmber");
		}
		if(repo.getByPan(pensionerDetails.getPan()) != null) {
			throw new PensionerDetailsAlreadyFoundException("Already in use - PAN");
		}
		
		if(repo.save(pensionerDetails) != null) {//
			msg ="registered"; 
		}
		
		log.info("in PensionerDetailServices - addPensionerDetailsinRepo {}", msg);
		return msg;
	}

	//to update details in database
	public void updatePensionerDetails(PensionerDetails pensionerDetails) throws PensionerDetailsNotFoundException {

		log.info("in PensionerDetailServices - updatePensionerDetails");
		
		PensionerDetails pensionerDetailsc = repo.getByAadhar(pensionerDetails.getAadhar());
	
		if(pensionerDetailsc != null) {
			
			int i =	repo.updateSalaryEarnedandAllowancesandBankIdandAccountNumber(
					pensionerDetails.getSalaryEarned(), pensionerDetails.getAllowances(),pensionerDetails.getBankId(), 
					pensionerDetails.getAccountNumber(),pensionerDetailsc.getAadhar());//
			log.info("in PensionerDetailServices - updatePensionerDetails {}", i);
	
		}else {
			throw new PensionerDetailsNotFoundException("you have not registered");
		}
		}
	
	

}
