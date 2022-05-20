package com.pmp.pensionerdetail.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PensionerDetailsBank implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long bankId;
	
	@NotEmpty
	@Column(unique = true)
	private String bankName;
	
	@NotEmpty
	private boolean typPubPri;

}
