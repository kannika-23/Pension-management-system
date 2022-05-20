package com.pmp.processpension.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmp.processpension.exception.PensionerDetailNotMatchingException;
import com.pmp.processpension.exception.PensionerDetailsInvalidParameters;
import com.pmp.processpension.model.PensionCharges;
import com.pmp.processpension.model.Pensioner;
import com.pmp.processpension.model.PensionerDetails;
import com.pmp.processpension.model.PensionerDetailsBank;
import com.pmp.processpension.proxy.PensionerDetailsProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProcessPensionServiceImpl implements  ProcessPensionService{
	
	@Autowired
	private PensionerDetailsProxy proxy;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	   //to get details via proxy
		public Pensioner getDetails(long aadhar) {
			ResponseEntity<Object> responseDetails =  proxy.getPensionerDetailbyAadhar(aadhar);
			Map<String, Object> pensionDetailsMap = (HashMap<String,Object>)responseDetails.getBody();
			PensionerDetails pensionerDetailsFromRD = mapper.convertValue(pensionDetailsMap.get("pensionerdetails"), PensionerDetails.class);
			PensionerDetailsBank pensionerDetailsBankFromRD = mapper.convertValue(pensionDetailsMap.get("pensionerdetailsbank"), PensionerDetailsBank.class);
			Pensioner pensioner = setpensionerDetailstopensioner(pensionerDetailsFromRD ,pensionerDetailsBankFromRD);
			return pensioner;
		}
		
		//to validate entered details for conformation
		public boolean validateDetails(Pensioner pensioner) throws PensionerDetailsInvalidParameters {
			
			log.info("process pension - validateDetails");
			
			PensionerDetails pensionerDetails = setpensionertopensionerDetail(pensioner);
			if (!((Object)pensionerDetails.getAccountNumber()).getClass().getSimpleName().equals("Long")) 
				throw new PensionerDetailsInvalidParameters("Entered value is not valid type - aadhar");
			if(!((Object)pensionerDetails.getAllowances()).getClass().getSimpleName().equals("Long")) 
				throw new PensionerDetailsInvalidParameters("Entered value is not valid type - allowances ");
			if(!((Object)pensionerDetails.getBankId()).getClass().getSimpleName().equals("Long")) 
				throw new PensionerDetailsInvalidParameters("Entered value is not valid type - bankId");
			if(!((Object)pensionerDetails.getDob()).getClass().getSimpleName().equals("Date")) 
				throw new PensionerDetailsInvalidParameters("Entered value is not valid type - dob");
			if(!((Object)pensionerDetails.getName()).getClass().getSimpleName().equals("String")) 
				throw new PensionerDetailsInvalidParameters("Entered value is not valid type - name");
			if(!((Object)pensionerDetails.getPan()).getClass().getSimpleName().equals("String")) 
				throw new PensionerDetailsInvalidParameters("Entered value is not valid type - pan");
			if(!((Object)pensionerDetails.getSalaryEarned()).getClass().getSimpleName().equals("Long")) 
				throw new PensionerDetailsInvalidParameters("Entered value is not valid type - salary");
			if(!((Object)pensionerDetails.isTypeSefFamily()).getClass().getSimpleName().equals("Boolean")) 
				throw new PensionerDetailsInvalidParameters("Entered value is not valid type - boolean");
			
			return true;
		}
		
		//to check the details with proxy
		public boolean matchPensionerDetailwithdb( Pensioner pensioner) throws PensionerDetailNotMatchingException {
			
			Map<String, Object> pensionDetailsMap = getdetailsviaProxy(pensioner);
			PensionerDetails pensionerDetailsFromRD = mapper.convertValue(pensionDetailsMap.get("pensionerdetails"), PensionerDetails.class);
			PensionerDetails pensionerDetails = setpensionertopensionerDetail(pensioner);

			log.info("process pension - in validate");
			if(pensionerDetailsFromRD.getAccountNumber() != pensionerDetails.getAccountNumber()) 
				throw new PensionerDetailNotMatchingException("Check entered Account Number");
			if(pensionerDetailsFromRD.getAllowances() != pensionerDetails.getAllowances())
				throw new PensionerDetailNotMatchingException("Check entered Allowance");
			if(pensionerDetailsFromRD.getBankId() != pensionerDetails.getBankId())
				throw new PensionerDetailNotMatchingException("Check entered Bank Details");
			if(!pensionerDetailsFromRD.getDob().equals(pensionerDetails.getDob()))
				throw new PensionerDetailNotMatchingException("Check entered Date of Birth");
			if(!pensionerDetailsFromRD.getName().equals(pensionerDetails.getName()))
				throw new PensionerDetailNotMatchingException("Check entered Name");
			if(!pensionerDetailsFromRD.getPan().equals(pensionerDetails.getPan()))
				throw new PensionerDetailNotMatchingException("Check entered PAN Number");
			if(pensionerDetailsFromRD.getSalaryEarned() != pensionerDetails.getSalaryEarned()) 
				throw new PensionerDetailNotMatchingException("Check entered Salary Details");
			if(pensionerDetailsFromRD.isTypeSefFamily() != pensionerDetails.isTypeSefFamily())
				throw new PensionerDetailNotMatchingException("Check entered Account Number");
			
			return true;
		}
		
		//to calculate  pensioner charges
		@Override
		public PensionCharges calculatePensionCharges(Pensioner pensioner){

			  log.info("process pension - Calculatepensioncharges");
			  
			   Map<String, Object> pensionDetailsMap = getdetailsviaProxy(pensioner);
			   PensionerDetails pensionerDetails = mapper.convertValue(pensionDetailsMap.get("pensionerdetails"), PensionerDetails.class);
			   PensionerDetailsBank pensionerDetailsBank = mapper.convertValue(pensionDetailsMap.get("pensionerdetailsbank"), PensionerDetailsBank.class);
			   
			   PensionCharges pensioncharges = new PensionCharges();
			   Long calculatedAmount = 0L;
			   Long bankCharges = 0L;   
					
			   bankCharges = (pensionerDetailsBank.isTypPubPri())? 500L : 550L; 
			   calculatedAmount = (pensionerDetails.isTypeSefFamily()) ?
					   		Math.round(0.8 * pensionerDetails.getSalaryEarned()) + pensionerDetails.getAllowances() :
						   Math.round(0.5 * pensionerDetails.getSalaryEarned()) + pensionerDetails.getAllowances();
			   
			   pensioncharges.setAadhar(pensionerDetails.getAadhar());
			   pensioncharges.setPensionerbc(bankCharges);
			   pensioncharges.setPensioneramt(calculatedAmount);
			   return pensioncharges;
			
		}
		
	
	//to add pensioner via proxy
	@Override
	public void addPensionerDetails(Pensioner pensioner) {
		log.info("process pension - addPensioner");
		proxy.addPensionerDetails(setpensionertopensionerDetail(pensioner));
	}

	
	//to update pensioner details
	public ResponseEntity<Object> updatePensioner(Pensioner pensioner) {
		log.info("process pension - updatePensioner");
		proxy.updatePensionerDetails(setpensionertopensionerDetail(pensioner)); 
		return proxy.getPensionerDetailbyAadhar(pensioner.getAadhar());
	}
	
	
	private PensionerDetails setpensionertopensionerDetail(Pensioner pensioner) {
		
		PensionerDetails pensionerDetails = new PensionerDetails();
		pensionerDetails.setAadhar(pensioner.getAadhar());
		pensionerDetails.setAccountNumber(pensioner.getAccountnumber());
		pensionerDetails.setAllowances(pensioner.getAllowances());
		pensionerDetails.setDob(pensioner.getDob());
		pensionerDetails.setName(pensioner.getUsername());
		pensionerDetails.setPan(pensioner.getPan());
		pensionerDetails.setSalaryEarned(pensioner.getSalaryearned());
		pensionerDetails.setTypeSefFamily(pensioner.isTypselffam());
		switch(pensioner.getBankName()){
		case "axis":pensionerDetails.setBankId(1001L); break;
		case "sbi":pensionerDetails.setBankId(1002L); break;
		case "hdfc":pensionerDetails.setBankId(1003L); break;
		case "cb":pensionerDetails.setBankId(1004L); break;
		}
		
		return pensionerDetails;
	}
	
	private Pensioner setpensionerDetailstopensioner(PensionerDetails pensionerDetails, PensionerDetailsBank pensionerDetailsBank ){

		Pensioner pensioner = new Pensioner();
		pensioner.setAadhar(pensionerDetails.getAadhar());
		pensioner.setAccountnumber(pensionerDetails.getAccountNumber());
		pensioner.setAllowances(pensionerDetails.getAllowances());
		pensioner.setDob(pensionerDetails.getDob());
		pensioner.setPan(pensionerDetails.getPan());
		pensioner.setSalaryearned(pensionerDetails.getSalaryEarned());
		pensioner.setTypselffam(pensionerDetails.isTypeSefFamily());
		pensioner.setUsername(pensionerDetails.getName());
	
		switch((int)pensionerDetails.getBankId()){
		case 1001:pensioner.setBankName(pensionerDetailsBank.getBankName());pensioner.setTypepubpri(pensionerDetailsBank.isTypPubPri()); break;
		case 1002:pensioner.setBankName(pensionerDetailsBank.getBankName());pensioner.setTypepubpri(pensionerDetailsBank.isTypPubPri()); break;
		case 1003:pensioner.setBankName(pensionerDetailsBank.getBankName());pensioner.setTypepubpri(pensionerDetailsBank.isTypPubPri()); break;
		case 1004:pensioner.setBankName(pensionerDetailsBank.getBankName());pensioner.setTypepubpri(pensionerDetailsBank.isTypPubPri()); break;
		}
		return pensioner;
		
	}
	
	private Map<String, Object> getdetailsviaProxy(Pensioner pensioner){
		  ResponseEntity<Object> responseDetails  = proxy.getPensionerDetailbyAadhar(pensioner.getAadhar());	
		  Map<String, Object> pensionDetailsMap = (HashMap<String,Object>)responseDetails.getBody();
		return pensionDetailsMap;
		
	}
}
