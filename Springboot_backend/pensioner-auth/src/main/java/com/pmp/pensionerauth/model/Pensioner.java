package com.pmp.pensionerauth.model;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Pensioner {
	
	 private long aadhar;
	 private String username;
	 private Date dob;
	 private  boolean typselffam;
	 private String pan ;
	 private  long salaryearned;
	 private long allowances;
	 private String bankName;
	 private boolean typepubpri;
	 private long accountnumber;

}
