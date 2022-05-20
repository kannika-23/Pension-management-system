package com.pmp.processpension;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.pmp.processpension.controller.ProcessPensionController;
import com.pmp.processpension.exception.PensionerDetailNotMatchingException;
import com.pmp.processpension.exception.PensionerDetailsInvalidParameters;
import com.pmp.processpension.model.PensionCharges;
import com.pmp.processpension.model.Pensioner;
import com.pmp.processpension.service.ProcessPensionService;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class ProcessPensionControllerTest extends ProcessPensionApplicationTests{
	
	@Mock 
	ProcessPensionService service;
	
	@InjectMocks
	ProcessPensionController  processPensionController = new  ProcessPensionController();
	
	private Pensioner mockpensioner(){
		Pensioner pensioner = new Pensioner();
		pensioner.setAadhar(123456788765L);
		return pensioner;
	}
	
	//addPensioner
	@Test
	public void testaddPensioner(){
		doNothing().when(service).addPensionerDetails(any(Pensioner.class));
		service.addPensionerDetails(mockpensioner());
	}
	
	//getPensionerDetails
	@Test
	public void testgetPensionerDetail(){
		when(service.getDetails(anyLong())).thenReturn(mockpensioner());	
		Assert.assertEquals(service.getDetails(anyLong()).getAadhar(),123456788765L);
	}
	
	//processConformationDetails()
	@Test
	public void testprocessConformationDetails() throws PensionerDetailsInvalidParameters{
		when(service.validateDetails(any(Pensioner.class))).thenReturn(true);
		assertTrue(service.validateDetails(mockpensioner()));
	}
	
	@Test(expected = PensionerDetailsInvalidParameters.class)
	public void test1processConformationDetails() throws PensionerDetailsInvalidParameters{	
		when(service.validateDetails(any(Pensioner.class))).thenReturn(false);
		if(service.validateDetails(mockpensioner()) == false){
			throw new PensionerDetailsInvalidParameters("invalid parameters");
		}
	}
	
	//process-validate
	@Test
	public void testprocessmatchPensionerDetailwithdb() throws PensionerDetailNotMatchingException{	
		when(service.matchPensionerDetailwithdb(any(Pensioner.class))).thenReturn(true);	
		assertTrue(service.matchPensionerDetailwithdb(mockpensioner()));
	}
	
	@Test(expected = PensionerDetailNotMatchingException.class)
	public void test1processmatchPensionerDetailwithdb() throws PensionerDetailNotMatchingException{
		when(service.matchPensionerDetailwithdb(any(Pensioner.class))).thenReturn(false);	
		if(!service.matchPensionerDetailwithdb(mockpensioner())){
			throw new PensionerDetailNotMatchingException("invalid parameters");
		}
	}
	
	//process-calculate
	@Test
	public void testprocesscalculate(){
		PensionCharges pensionCharges = new PensionCharges();
		when(service.calculatePensionCharges(any(Pensioner.class))).thenReturn(pensionCharges);
		assertNotNull(service.calculatePensionCharges(mockpensioner()));
	}

	
	@Test
	public void testupdatePensioner() {
		ResponseEntity<Object> response = new ResponseEntity<Object>("updated",HttpStatus.ACCEPTED);	
		when(service.updatePensioner(any(Pensioner.class))).thenReturn(response);	
		assertTrue(response.hasBody());		
					
	}
	
}
