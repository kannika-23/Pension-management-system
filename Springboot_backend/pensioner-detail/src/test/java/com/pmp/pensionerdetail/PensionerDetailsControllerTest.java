package com.pmp.pensionerdetail;

import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;

import com.pmp.pensionerdetail.controller.PensionerDetailController;
import com.pmp.pensionerdetail.exception.PensionerDetailsAlreadyFoundException;
import com.pmp.pensionerdetail.exception.PensionerDetailsNotFoundException;
import com.pmp.pensionerdetail.model.PensionerDetails;
import com.pmp.pensionerdetail.service.PensionerDetailServices;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
public class PensionerDetailsControllerTest extends PensionerDetailApplicationTests {
	
	@Mock
	PensionerDetailServices service;
	
	@InjectMocks
	PensionerDetailController pensionerDetailController = new PensionerDetailController();
	
	private PensionerDetails mockPensionerDetails(){
		PensionerDetails pensionerDetails = new PensionerDetails();
		return pensionerDetails;
	}
	
	//getPensionerDetailbyAadhar
	@Test
	public void testgetPensionerDetailbyAadhar() throws PensionerDetailsNotFoundException{

		Map<String, Object> mapMock = Mockito.mock(Map.class);
		when(service.getDetails(Mockito.anyLong()) ).thenReturn(mapMock);	
		assertTrue(service.getDetails(mockPensionerDetails().getAadhar()) instanceof Map);
			
	}
	
	@Test(expected = PensionerDetailsNotFoundException.class)
	public void test1getPensionerDetailbyAadhar() throws PensionerDetailsNotFoundException{
		
		when(service.getDetails(Mockito.anyLong()) ).thenReturn(null);	
		if(service.getDetails(mockPensionerDetails().getAadhar()) == null ){
			throw new PensionerDetailsNotFoundException("User not found");
		}
		
	}
	
	//addPensionerDetails
	@Test
	public void testaddPensionerDetails() throws PensionerDetailsAlreadyFoundException {
		
		when(service.addPensionerDetailsinRepo(any(PensionerDetails.class))).thenReturn("created");
		assertTrue("created".equals(service.addPensionerDetailsinRepo(mockPensionerDetails())));
		
	}
	
    @Test(expected=PensionerDetailsAlreadyFoundException.class)
	public void test1addPensionerDetails() throws PensionerDetailsAlreadyFoundException {
    	
		when(service.addPensionerDetailsinRepo(any(PensionerDetails.class))).thenThrow(new PensionerDetailsAlreadyFoundException("Alredy in use"));
		service.addPensionerDetailsinRepo(mockPensionerDetails());	
		
	}

    //updatePensionerDetails
	@Test
	public void testupdatePensionerDetails() throws PensionerDetailsAlreadyFoundException, PensionerDetailsNotFoundException{
		
		doNothing().when(service).updatePensionerDetails(any(PensionerDetails.class));	
		service.updatePensionerDetails(mockPensionerDetails());
		
	}
	
	@Test
	public void test2updatePensionerDetails() throws PensionerDetailsAlreadyFoundException, PensionerDetailsNotFoundException{

		try {
			doThrow(new PensionerDetailsNotFoundException("already in use")).when(service).updatePensionerDetails(any(PensionerDetails.class));
			service.updatePensionerDetails(mockPensionerDetails());
		} catch (PensionerDetailsNotFoundException e) {
			assertTrue(e instanceof PensionerDetailsNotFoundException);
		}
		
	}
	



}
