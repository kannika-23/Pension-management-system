package com.pmp.pensionerdetail.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class PensionerDetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Min(100000000000L)
	@Max(999999999999L)
	//@Column(unique = true)
	private long aadhar;
	
	@Size(min=3, max=20)
	@NotEmpty(message= "Name is null")
	private String name;
	
	@Past
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Size(max=10,min=10)
	@Pattern(regexp="[A-Z]{5}[0-9]{4}[A-Z]{1}")
	@Column(unique=true)
	private String pan;
	
	@Min(50000)
	@Max(500000)
	@NotNull
	private long salaryEarned;
	
	@Min(5000)
	@Max(200000)
	@NotNull
	private long allowances;
	
	@NotNull
	private boolean typeSefFamily;
	
	@Min(1000)
	@Max(1010)
	private long bankId;

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	
	@Min(1000000000L)
	@Max(9999999999999L)
	private long accountNumber;
	
	
}
