package com.mercadolibre.challenge.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercadolibre.challenge.entity.HumanDna;
import com.mercadolibre.challenge.entity.dto.MutantRequestDTO;
import com.mercadolibre.challenge.entity.dto.StatsDTO;
import com.mercadolibre.challenge.exception.InvalidDataException;
import com.mercadolibre.challenge.repository.HumanDnaRepository;

@RunWith(MockitoJUnitRunner.class)
public class AdnValidatorBusinessTest {

	String[] dnaNotMutantTest = { "ATGCGT", "TAGTTC", "TTGCGG", "AGTAGG", "CACCTA", "TCACTG" };

	@Mock
	HumanDnaRepository humanDnaRepository;

	@InjectMocks
	AdnValidatorBusiness adnValidatorBusiness;

	@Test
	public void isMutant() {
		// prepare mocks

		MutantRequestDTO requestDto = MutantRequestDTO.builder()
				.dna(new String[] { "ATGCGT", "TAGTTC", "TTGTGG", "AGTAGG", "CCCCTA", "TCACTG" }).build();

		when(humanDnaRepository.insert(any(HumanDna.class))).thenReturn(HumanDna.builder().build());

		// execute

		boolean result = adnValidatorBusiness.isMutant(requestDto);

		// asserts

		verify(humanDnaRepository).insert(any(HumanDna.class));

		assertTrue(result);

	}

	@Test
	public void isNotMutant() {
		// prepare mocks

		MutantRequestDTO requestDto = MutantRequestDTO.builder()
				.dna(new String[] { "ATGCGT", "TAGCGC", "TTGTGG", "AGTAGG", "CACCTA", "TCACTG" }).build();

		when(humanDnaRepository.insert(any(HumanDna.class))).thenReturn(HumanDna.builder().build());

		// execute

		boolean result = adnValidatorBusiness.isMutant(requestDto);

		// asserts

		verify(humanDnaRepository).insert(any(HumanDna.class));

		assertFalse(result);

	}

	@Test(expected = InvalidDataException.class)
	public void isMutantInvalidData() {
		// prepare mocks

		MutantRequestDTO requestDto = MutantRequestDTO.builder().dna(new String[] { "ATGCGTR", "TAGTTC" }).build();

		// execute

		adnValidatorBusiness.isMutant(requestDto);

	}

	@Test
	public void getStats() {
		// prepare mocks

		Integer totalHumans = 6;
		Long totalMutants = 3L;

		List<HumanDna> data = new ArrayList<>();
		data.add(HumanDna.builder().isMutant(true).build());
		data.add(HumanDna.builder().isMutant(true).build());
		data.add(HumanDna.builder().isMutant(true).build());
		data.add(HumanDna.builder().isMutant(false).build());
		data.add(HumanDna.builder().isMutant(false).build());
		data.add(HumanDna.builder().isMutant(false).build());

		when(humanDnaRepository.findAll()).thenReturn(data);

		// execute

		StatsDTO result = adnValidatorBusiness.getStats();

		// asserts

		verify(humanDnaRepository).findAll();

		assertNotNull(result);
		assertEquals(totalHumans, result.getCount_human_dna());
		assertEquals(totalMutants, result.getCount_mutant_dna());
		assertEquals("0,5", result.getRatio());

	}

}
