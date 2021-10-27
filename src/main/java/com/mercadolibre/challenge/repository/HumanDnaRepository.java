package com.mercadolibre.challenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mercadolibre.challenge.entity.HumanDna;

public interface HumanDnaRepository extends MongoRepository<HumanDna, String>{

}
