package com.pmp.processpension.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.pmp.processpension.exception.PensionerDetailNotMatchingException;
import com.pmp.processpension.exception.PensionerDetailsInvalidParameters;
import com.pmp.processpension.model.PensionCharges;
import com.pmp.processpension.model.Pensioner;
import com.pmp.processpension.service.ProcessPensionService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@RestController
public class ProcessPensionController {
	
	@Autowired
	private ProcessPensionService service;
	
	//to get details
	@GetMapping("/pmp/process-pension/getdetails/{aadhar}")
	public ResponseEntity<Object> getPensionerDetail(@PathVariable long aadhar){
		log.info("inside process pension - get details by aadhar");

		HttpHeaders header = new HttpHeaders();
		header.add("responded","pensioner-process");
		
		Pensioner pensioner = service.getDetails(aadhar);
		
		return new ResponseEntity<Object>(pensioner,header,HttpStatus.ACCEPTED);
	}
	
	//to verify entered details
	@PostMapping("pmp/process-pension/getpensionconformationdetails")
	public ResponseEntity<Object> processConformationDetails(@RequestBody Pensioner pensioner ) throws PensionerDetailsInvalidParameters{
		
		log.info("inside processPension - processConformationDetails");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("responded","pensioner-process");
		
		service.validateDetails(pensioner);
		
		return new ResponseEntity<Object>(pensioner,headers,HttpStatus.ACCEPTED);
	}
	
	//to calculate pension 
	@PostMapping("pmp/process-pension/getpension")
	public ResponseEntity<Object> process(@RequestBody  Pensioner pensioner ) throws PensionerDetailNotMatchingException {
		
		log.info("inside processPension - process");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("responded","pensioner-process");
		
		PensionCharges	pensionCharges	= new PensionCharges();	
		if(service.matchPensionerDetailwithdb(pensioner)) {
			pensionCharges = service.calculatePensionCharges(pensioner);
		}
		
		return new ResponseEntity<Object>(pensionCharges,headers,HttpStatus.ACCEPTED);	 
	}
	
	//to add pensioner
	@PostMapping("/pmp/process-pension/addpensioner")
	public void addPensioner(@RequestBody Pensioner pensioner){
		log.info("inside process pension - add pensioner");
		
		HttpHeaders header = new HttpHeaders();
		header.add("responded","pensioner-process");
		
		service.addPensionerDetails(pensioner);
		
	}
	
	//to update user
	@PostMapping("pmp/process-pension/updatepensionerdetails")
	public ResponseEntity<Object> updatePensionerDetails(@RequestBody  Pensioner pensioner) {
		
		log.info("inside processPension - updatePensionerDetails");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("responded","pensioner-process");
	    
		return service.updatePensioner(pensioner);
	}

}
