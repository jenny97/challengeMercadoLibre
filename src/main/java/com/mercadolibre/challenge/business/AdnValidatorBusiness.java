package com.mercadolibre.challenge.business;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mercadolibre.challenge.entity.HumanDna;
import com.mercadolibre.challenge.entity.dto.AnalyzeDTO;
import com.mercadolibre.challenge.entity.dto.MutantRequestDTO;
import com.mercadolibre.challenge.entity.dto.StatsDTO;
import com.mercadolibre.challenge.exception.InvalidDataException;
import com.mercadolibre.challenge.repository.HumanDnaRepository;

@Service
public class AdnValidatorBusiness {
	
	private static final String INVALID_DATA = "Por favor verifique los datos y su longitud.";
	private static DecimalFormat df = new DecimalFormat("#.##");

	@Autowired
	HumanDnaRepository humanDnaRepository;
		
	public Boolean isMutant(MutantRequestDTO mutantRequestDTO) {

		List<String> adnList = Arrays.asList(mutantRequestDTO.getDna());

		this.validateData(adnList);
		
		List<List<String>> adn = adnList.stream()
				.map(s -> Arrays.asList(s.split("")))
				.collect(Collectors.toList());

		int quantitySequence = 0;
		boolean isMutant = false;

		AnalyzeDTO analyzeDiagonal = AnalyzeDTO.builder().build();
		AnalyzeDTO analyzeReverseDiagonal = AnalyzeDTO.builder().build();

		mainloop:
		for(int i = 0 ; i < adn.size(); i++) {

			AnalyzeDTO analyzeRow = AnalyzeDTO.builder().build();
			AnalyzeDTO analyzeColumn = AnalyzeDTO.builder().build();

			for(int j = 0 ; j < adn.size(); j++) {

				quantitySequence = quantitySequence + analyze(analyzeRow, adn.get(i).get(j));
				quantitySequence = quantitySequence + analyze(analyzeColumn, adn.get(j).get(i));

				if(i == j) {
					quantitySequence = quantitySequence + analyze(analyzeDiagonal, adn.get(i).get(j));
				}

				if(j == (adn.size() - 1) - i) {
					quantitySequence = quantitySequence + analyze(analyzeReverseDiagonal, adn.get(i).get(j));
				}

				if(quantitySequence > 1) {
					isMutant = true;
					break mainloop;
				}				
			}			
		}		

		humanDnaRepository.insert(HumanDna.builder()
				.dna(adnList)
				.isMutant(isMutant)
				.build());

		return isMutant;
	}
	
	public StatsDTO getStats() {
		List<HumanDna> humanDnaList = humanDnaRepository.findAll();
		Long countMutants = humanDnaList.stream().filter(x -> x.isMutant()).count();		
		int total = humanDnaList.size();

		return StatsDTO.builder()
				.count_human_dna(total)
				.count_mutant_dna(countMutants)
				.ratio(df.format(Float.valueOf(countMutants) / Float.valueOf(total)))
				.build();
	}
	
	private void validateData(List<String> dna) {
		Pattern pattern = Pattern.compile("^[ACGT]{" + dna.size() * dna.size() + "}$");
	    Matcher matcher = pattern.matcher(String.join("", dna));
	    Optional.of(matcher.find())
	    	.filter(r -> r)
	    	.orElseThrow(() -> new InvalidDataException(INVALID_DATA));
	}

	private int analyze(AnalyzeDTO analyzeDTO, String currentValue) {
		if(currentValue.equals(analyzeDTO.getPreviousValue()) ) {
			analyzeDTO.setQuantityCoincidence(analyzeDTO.getQuantityCoincidence() + 1);
			if(analyzeDTO.getQuantityCoincidence() == 4) {
				System.out.print("Secuencia " + analyzeDTO.getPreviousValue()  );
				analyzeDTO.setQuantityCoincidence(0);
				analyzeDTO.setPreviousValue("");
				return 1;					
			}
		}else {
			analyzeDTO.setPreviousValue(currentValue);
			analyzeDTO.setQuantityCoincidence(1);	
		}

		return 0;
	}
}
