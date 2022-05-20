package com.pmp.processpension;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pmp.processpension.model.PensionerDetails;
import com.pmp.processpension.model.PensionerDetailsBank;
import com.pmp.processpension.proxy.PensionerDetailsProxy;
import com.pmp.processpension.service.ProcessPensionServiceImpl;

public class ProcessPensionServiceImplTest {
	
	@Mock
	private PensionerDetailsProxy proxy;
	
	final static long AADHAR_LONG = 1l;
	
	@InjectMocks
	ProcessPensionServiceImpl processPensionServiceImpl = new ProcessPensionServiceImpl();
	
	private PensionerDetails mockPensionerDetails(){
		PensionerDetails pensionerDetails = new PensionerDetails();
		pensionerDetails.setAadhar(123456789987L);
		pensionerDetails.setAadhar(123456788765L);
		pensionerDetails.setAllowances(15000L);
		pensionerDetails.setSalaryEarned(100000L);
		pensionerDetails.setTypeSefFamily(true);
		return pensionerDetails;
	}
	
	private PensionerDetailsBank mockPensionerDetailsBank(){
		PensionerDetailsBank pensionerDetailsBank = new PensionerDetailsBank();
		pensionerDetailsBank.setTypPubPri(true);
		return pensionerDetailsBank;
	}
	
	
	private ResponseEntity<Object> mockresponse(){
		ResponseEntity<Object> mockresponse1 =  new ResponseEntity<Object>("mocking",HttpStatus.ACCEPTED);
		return mockresponse1;
	}
	
	//getDetails
	@Test
	public void testgetDetails(){
		ResponseEntity<Object> mockresponse =  new ResponseEntity<Object>("mocking",HttpStatus.ACCEPTED);
		when(proxy.getPensionerDetailbyAadhar(Mockito.anyLong())).thenReturn(mockresponse);
		Assert.assertTrue(mockresponse.hasBody());
	}
	
	//calculate charges
	@Test
	public void testcalculatePensionCharges(){
		long bankcharges = (mockPensionerDetailsBank().isTypPubPri())? 500L : 550L;
		Assert.assertEquals(bankcharges, 500L);
	}
	
	@Test
	public void test1calculatePensionCharges(){
		long bankcharges = (!mockPensionerDetailsBank().isTypPubPri())? 500L : 550L;
		Assert.assertEquals(bankcharges, 550L);
	}
	
	@Test
	public void test2calculatePensionCharges(){
		long calculatedAmount = (mockPensionerDetails().isTypeSefFamily()) ?
			   		Math.round(0.8 * mockPensionerDetails().getSalaryEarned()) + mockPensionerDetails().getAllowances() :
				   Math.round(0.5 * mockPensionerDetails().getSalaryEarned()) + mockPensionerDetails().getAllowances();
		Assert.assertEquals(calculatedAmount, 95000L);
	}
	
	@Test
	public void test3calculatePensionCharges(){
		long calculatedAmount = (!mockPensionerDetails().isTypeSefFamily()) ?
			   		Math.round(0.8 * mockPensionerDetails().getSalaryEarned()) + mockPensionerDetails().getAllowances() :
				   Math.round(0.5 * mockPensionerDetails().getSalaryEarned()) + mockPensionerDetails().getAllowances();
		Assert.assertEquals(calculatedAmount, 65000L);
	}
	
	//addPensionerDetails
	@Test
	public void testaddPensionerDetails(){
		when(proxy.addPensionerDetails(Mockito.any(PensionerDetails.class))).thenReturn(mockresponse());
		assertNotNull(proxy.addPensionerDetails(mockPensionerDetails()));
	}
	
	//update
	@Test
	public void testupdatePensionerDetails(){
		when(proxy.updatePensionerDetails(Mockito.any(PensionerDetails.class))).thenReturn(mockresponse());
		assertNotNull(proxy.updatePensionerDetails(mockPensionerDetails()));
	}

}
