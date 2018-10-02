package com.cortes.desafio.solutis.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cortes.desafio.solutis.entity.Desafio;
import com.cortes.desafio.solutis.exception.GenericException;
import com.cortes.desafio.solutis.service.DesafioService;

@RestController
@RequestMapping("desafios")
public class DesafioResource {
	
	@Autowired
	private DesafioService service;
	
	@GetMapping
	public ResponseEntity<Iterable<Desafio>> list(){		
		return ResponseEntity.ok().body(service.findAllByOrderByIdDesc());
	}
	
	@PostMapping
	public ResponseEntity<Desafio> save(@RequestBody String request){
		String requestStr = request.trim();
		if(StringUtils.isEmpty(requestStr)) {
			throw new GenericException("Parametro Obrigatório");
		}
		
		Desafio desafio = service.findVogal(requestStr);
		
		return ResponseEntity.ok().body(desafio);
	}

}
