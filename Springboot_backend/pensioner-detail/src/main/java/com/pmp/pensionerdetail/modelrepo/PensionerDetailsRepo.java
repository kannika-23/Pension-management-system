package com.pmp.pensionerdetail.modelrepo;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pmp.pensionerdetail.model.PensionerDetails;

@Repository
@EnableJpaRepositories
public interface PensionerDetailsRepo extends JpaRepository<PensionerDetails, Long> {
	
	Logger logger = LoggerFactory.getLogger(PensionerDetailsRepo.class);

	public PensionerDetails getByAadhar(Long aadhar);

	public PensionerDetails getByAccountNumber(long accountNumber);

	public PensionerDetails getByPan(String pan);

	@Transactional
	@Modifying
	@Query("update PensionerDetails set salaryEarned =:salaryEarned , allowances =:allowances , bankId =:bankId,accountNumber =:accountNumber where aadhar =:aadhar")
	public int updateSalaryEarnedandAllowancesandBankIdandAccountNumber(@Param ("salaryEarned") @NotNull long salaryEarned,
			@Param ("allowances")  @NotNull long allowances, @Param ("bankId") long bankId, @Param ("accountNumber") long accountNumber, @Param("aadhar") long aadhar);
	

}
