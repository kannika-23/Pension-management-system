package com.pmp.processpension.model;

import java.time.LocalDate;
import java.util.Date;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PensionerDetails {

	private long id;	
	private long aadhar;
	private String name;	
	//not validated
	private Date dob;	
	private String pan;	
	private long salaryEarned;	
	private long allowances;
	//default false
	private boolean typeSefFamily;	
	private long bankId;
	private long accountNumber;	
	
}

