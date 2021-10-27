package com.mercadolibre.challenge.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsDTO {

	private Long count_mutant_dna;
	private Integer count_human_dna;
	private String  ratio;

}