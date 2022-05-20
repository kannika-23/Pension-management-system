package com.pmp.pensionerdetail.controller;

import org.springframework.web.bind.annotation.RestController;

import com.pmp.pensionerdetail.exception.PensionerDetailsAlreadyFoundException;
import com.pmp.pensionerdetail.exception.PensionerDetailsInvalidParametersException;
import com.pmp.pensionerdetail.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerdetail.model.PensionerDetails;
import com.pmp.pensionerdetail.service.PensionerDetailServices;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
public class PensionerDetailController {
	
	@Autowired
	private PensionerDetailServices service;
	
	//to fetch requested pensionerdetails
	@GetMapping("/pmp/pensioner-details/getbyaadhar/{aadhar}")
	public ResponseEntity<Object> getPensionerDetailbyAadhar(@PathVariable Long aadhar) throws PensionerDetailsNotFoundException  {
		log.info("inside pensionerDetail - get details by aadhar");

		HttpHeaders header = new HttpHeaders();
		header.add("responded","pensioner-details");
		
		Map<String, Object> response = service.getDetails(aadhar);
		
		return new ResponseEntity<Object>(response,header,HttpStatus.ACCEPTED);
	}
	
	//to adduser 
	@PostMapping("/pmp/pensioner-details/addpensionerdetails")
	public ResponseEntity<Object> addPensionerDetails(@RequestBody @Valid PensionerDetails pensionerDetails, 
			BindingResult results) throws PensionerDetailsAlreadyFoundException, PensionerDetailsInvalidParametersException{
		log.info("inside pensionerDetail - add Pensioner details");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("responded","pensioner-details");
		
		if(results.hasErrors()) {
			log.debug("inside pensionerDetail - add Pensioner details - hasError");
			throw new PensionerDetailsInvalidParametersException("Entered invalid parameters");
		}
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("msg", service.addPensionerDetailsinRepo(pensionerDetails));
		
		return new ResponseEntity<Object>(response,headers,HttpStatus.CREATED);
	}
	
	//toupdate
	@PostMapping("/pmp/pensioner-details/updatepensionerdetails")
	public ResponseEntity<Object> updatePensionerDetails(@RequestBody @Valid PensionerDetails pensionerDetails,
			BindingResult results) throws  PensionerDetailsNotFoundException, PensionerDetailsInvalidParametersException{
		log.info("inside pensionerDetail - update Pensioner details");
		
		HttpHeaders headers = new HttpHeaders();
		Map<String, Object> res = new HashMap<String, Object>();
		
		if(results.hasErrors()) {
			log.debug("inside pensionerDetail - update Pensioner details - hasError");
			throw new PensionerDetailsInvalidParametersException("Entered invalid parameters");
		}
		
		headers.add("responded","pensioner-details");
		service.updatePensionerDetails(pensionerDetails);	
		
		res.put("msg", "updated");
		return new ResponseEntity<Object>(res,headers,HttpStatus.ACCEPTED);
		
	}

}
