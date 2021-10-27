package com.mercadolibre.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mercadolibre.challenge.business.AdnValidatorBusiness;
import com.mercadolibre.challenge.entity.dto.MutantRequestDTO;
import com.mercadolibre.challenge.entity.dto.StatsDTO;

@Controller
@RequestMapping("/")
public class AdnValidatorService {

	@Autowired
	private AdnValidatorBusiness adnValidatorBusiness;

	@PostMapping("/mutant")
	public ResponseEntity<Boolean> mutant(@RequestBody MutantRequestDTO mutantRequestDTO) {
		return new ResponseEntity<>(
				adnValidatorBusiness.isMutant(mutantRequestDTO) ? HttpStatus.OK : HttpStatus.FORBIDDEN);
	}

	@GetMapping("/stats")
	public ResponseEntity<StatsDTO> getStats() {
		return new ResponseEntity<>(adnValidatorBusiness.getStats(), HttpStatus.OK);
	}

}
