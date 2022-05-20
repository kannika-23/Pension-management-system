package com.pmp.processpension.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pmp.processpension.model.PensionerDetails;
import com.pmp.processpension.exception.PensionDetailsFeignConfiguration;

@FeignClient(name="pensioner-details", configuration = PensionDetailsFeignConfiguration.class)
public interface PensionerDetailsProxy {

	@GetMapping("/pmp/pensioner-details/getbyaadhar/{aadhar}")
	public ResponseEntity<Object> getPensionerDetailbyAadhar(@PathVariable("aadhar") Long aadhar);

	@PostMapping("/pmp/pensioner-details/updatepensionerdetails")
	public ResponseEntity<Object> updatePensionerDetails(@RequestBody PensionerDetails pensionerDetails);
	
	@PostMapping("/pmp/pensioner-details/addpensionerdetails")
	public ResponseEntity<Object> addPensionerDetails(@RequestBody PensionerDetails pensionerDetails);
}
