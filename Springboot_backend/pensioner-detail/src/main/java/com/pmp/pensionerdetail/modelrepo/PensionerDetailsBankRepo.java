package com.pmp.pensionerdetail.modelrepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.pmp.pensionerdetail.model.PensionerDetailsBank;

@EnableJpaRepositories
public interface PensionerDetailsBankRepo extends JpaRepository<PensionerDetailsBank, Long>{
	
	Logger logger = LoggerFactory.getLogger(PensionerDetailsBankRepo.class);
	
	public PensionerDetailsBank getByBankId(long bankId);

}
