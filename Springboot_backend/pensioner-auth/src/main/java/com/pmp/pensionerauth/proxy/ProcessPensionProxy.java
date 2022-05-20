package com.pmp.pensionerauth.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pmp.pensionerauth.exception.PensionDetailsFeignConfiguration;
import com.pmp.pensionerauth.model.Pensioner;

@FeignClient(name="process-pension",configuration=PensionDetailsFeignConfiguration.class)
public interface ProcessPensionProxy {
	
	@PostMapping("/pmp/process-pension/addpensioner")
	public void addPensioner(@RequestBody Pensioner pensioner);

}
