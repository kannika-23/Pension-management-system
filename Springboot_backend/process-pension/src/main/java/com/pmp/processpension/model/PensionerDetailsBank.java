package com.pmp.processpension.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Component
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PensionerDetailsBank {

	private long bankId;
	private String bankName;
	private boolean typPubPri;


}
