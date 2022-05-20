package com.pmp.pensionerdetail;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmp.pensionerdetail.exception.PensionerDetailsAlreadyFoundException;
import com.pmp.pensionerdetail.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerdetail.model.PensionerDetails;
import com.pmp.pensionerdetail.model.PensionerDetailsBank;
import com.pmp.pensionerdetail.modelrepo.PensionerDetailsBankRepo;
import com.pmp.pensionerdetail.modelrepo.PensionerDetailsRepo;
import com.pmp.pensionerdetail.service.PensionerDetailServicesImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class PensionerDetailsServiceImplTest {
	
	@Mock
	private PensionerDetailsRepo repo;
	
	@Mock
	private PensionerDetailsBankRepo bankrepo;
	
	private static final long TEST_LONG = 1L;
	
	@InjectMocks
	PensionerDetailServicesImpl pensionerDetailsService = new PensionerDetailServicesImpl();
	
	private PensionerDetails mockPensionerDetails(){
		PensionerDetails pensionerDetails = new PensionerDetails();
		pensionerDetails.setAadhar(123456789987L);
		return pensionerDetails;
	}
	
	private PensionerDetailsBank mockPensionerDetailsBank(){
		PensionerDetailsBank pensionerDetailsBank= new PensionerDetailsBank();
		pensionerDetailsBank.setBankId(1001L);
		return pensionerDetailsBank;
	}
	
	//getDetails
	@Test
	public void testgetDetails() {
		
		when( repo.getByAadhar(Mockito.anyLong()) ).thenReturn(mockPensionerDetails());
		Assert.assertEquals(123456789987L, repo.getByAadhar(Mockito.anyLong()).getAadhar());

	}
	
	@Test
	public void testgetDetailsBank(){
		
		when( bankrepo.getByBankId(Mockito.anyLong()) ).thenReturn(mockPensionerDetailsBank());
		Assert.assertEquals(1001L, bankrepo.getByBankId(Mockito.anyLong()).getBankId());
	
	}

	@Test(expected = PensionerDetailsNotFoundException.class)
	public void test1getDetails() throws PensionerDetailsNotFoundException {
		
		when( repo.getByAadhar(Mockito.anyLong()) ).thenReturn(null);
		if(repo.save(mockPensionerDetails()) == null){
			throw new PensionerDetailsNotFoundException("not unique");
		}
		
	}
	
	//addPensionerDetailsinRepo
	@Test
	public void testaddPensionerDetailsinRepo() {

		when( repo.save((Mockito.any(PensionerDetails.class))) ).thenReturn(mockPensionerDetails());
		assertNotNull(repo.save(mockPensionerDetails()));

	}
	
	  @Test(expected = PensionerDetailsAlreadyFoundException.class)
		public void test1addPensionerDetailsinRepo() throws PensionerDetailsAlreadyFoundException{
	    	
		when( repo.save((Mockito.any(PensionerDetails.class))) ).thenReturn(null);
			if(repo.save(mockPensionerDetails()) == null){
				throw new PensionerDetailsAlreadyFoundException("not unique");
			}
			
		}

	//updatePensionerDetails
	@Test
	public void testupdatePensionerDetails(){

		when( repo.updateSalaryEarnedandAllowancesandBankIdandAccountNumber(
				mockPensionerDetails().getSalaryEarned(), 
				mockPensionerDetails().getAllowances(),
				mockPensionerDetails().getBankId(), 
				mockPensionerDetails().getAccountNumber(),
				mockPensionerDetails().getAadhar())
				).thenReturn(1);

		assertNotNull(repo.updateSalaryEarnedandAllowancesandBankIdandAccountNumber(TEST_LONG,TEST_LONG,TEST_LONG,TEST_LONG,TEST_LONG));

	}

	@Test(expected = PensionerDetailsNotFoundException.class)
	public void test1updatePensionerDetails() throws PensionerDetailsNotFoundException{

	
		when( repo.updateSalaryEarnedandAllowancesandBankIdandAccountNumber(
				mockPensionerDetails().getSalaryEarned(), 
				mockPensionerDetails().getAllowances(),
				mockPensionerDetails().getBankId(), 
				mockPensionerDetails().getAccountNumber(),
				mockPensionerDetails().getAadhar())
				).thenReturn(0);
		
		if(repo.updateSalaryEarnedandAllowancesandBankIdandAccountNumber(TEST_LONG ,TEST_LONG ,TEST_LONG ,TEST_LONG,TEST_LONG) != 1){
			throw new PensionerDetailsNotFoundException("not found");
		}
	}

}
