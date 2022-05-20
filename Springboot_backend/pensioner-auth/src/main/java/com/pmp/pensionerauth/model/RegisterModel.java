package com.pmp.pensionerauth.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterModel {
	
	private Pensioner pensioner;
	private DAOUser daoUser;

}
