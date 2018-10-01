package com.cortes.desafio.solutis.repository;

import org.springframework.data.repository.CrudRepository;

import com.cortes.desafio.solutis.entity.Desafio;

public interface DesafioRepository extends CrudRepository<Desafio, Long>{
	
	public Iterable<Desafio> findAllByOrderByIdDesc();

}
