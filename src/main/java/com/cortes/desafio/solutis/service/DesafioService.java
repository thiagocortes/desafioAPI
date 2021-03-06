package com.cortes.desafio.solutis.service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cortes.desafio.solutis.entity.Desafio;
import com.cortes.desafio.solutis.repository.DesafioRepository;

@Service
public class DesafioService {
	
	private static final String EXP_REG_REMOV_ESP = "[^\\p{ASCII}]";
	private static final List<Character> VOGAIS =  Arrays.asList('A', 'E', 'I', 'O', 'U');
	private static final int VOGAL_REPEAT = 2;
	private static final String MSG_DEFAULT = "Não existe vogal que atende a regra ou não existem vogais";
	
	
	@Autowired
	private DesafioRepository desafioRepository;
	
	//Listar todos os registros gravados
	public Iterable<Desafio> findAllByOrderByIdDesc(){
		return desafioRepository.findAllByOrderByIdDesc();
	}
	
	//Salvar registro
	public Desafio save(Desafio desafio){
		return desafioRepository.save(desafio);
	}
	/**
	 * Obter Vogal que suceda uma consoante e não se repita e grave o histório
	 * @param request String
	 * @return Desafio
	 */
	public Desafio findVogal(String request){
		
		long inicioProcesso = Calendar.getInstance().getTimeInMillis();
		
		Map<Character, Integer> vogaisCount = preencherMap(request);
		
		String vogal = getVogal(vogaisCount);
		
		long finalProcesso = Calendar.getInstance().getTimeInMillis();
		
		long tempoTotal = finalProcesso - inicioProcesso;
		
		Desafio desafio = new Desafio(request, vogal, tempoTotal);
		
		return save(desafio);
			
	}
	/**
	 * Carregar o LinkedHashMap com as vogais em ordem de inserção
	 * @param request String
	 * @return Map de vogais
	 */
	private Map<Character, Integer> preencherMap(String requestParam){
		Map<Character, Integer> vogaisCount = new LinkedHashMap<Character, Integer>();
		
		String request = removerAcentos(requestParam.toUpperCase());
		
		if(request.length() <= 2) {
			//Para satisfazer a regra a string deve ter pelo menos 3 caracteres
			vogaisCount.put(request.charAt(0), 0);
		}else if(request.length() == 3) {
			Character first = request.charAt(0);
			Character second = request.charAt(1);
			Character third = request.charAt(2);
			if(isVogal(first) && !isVogal(second) && isVogal(third) && !Objects.equals(first, third)) {
				vogaisCount.put(third, 2);
			}
		}else {
			//Caso a primeira letra seja uma vogal atribui ao Map
			if(isVogal(request.charAt(0))) {
				vogaisCount.put(request.charAt(0), 1);
			}
			//Buscar todas as vogais que sucedem uma consoante e que essa consoante seja precedida de uma vogal
			for(int i = 2; i < request.length(); i++) {
				
				Character first = request.charAt(i - 2);
				Character second = request.charAt(i - 1);
				Character third = request.charAt(i);
				
				if(	isVogal(third) && !isVogal(second) && isVogal(first) && !Objects.equals(first, third)) {
					if( !vogaisCount.containsKey(request.charAt(i)) ) {
						vogaisCount.put(request.charAt(i), 1);
					}else if( vogaisCount.containsKey(request.charAt(i)) ) {
						vogaisCount.put(request.charAt(i), vogaisCount.get(request.charAt(i)) + 1);
					}
				}
			}
			
			verificarRepeticao(vogaisCount, request);
		}
		return vogaisCount;
	}
	/**
	 * Verificar qual das vogais se repetem
	 * @param vogaisCount
	 * @param request
	 */
	private void verificarRepeticao(Map<Character, Integer> vogaisCount, String request) {

		for(int i = 0; i < request.length(); i++) {
			// Caso a vogal se repita ela será incrementada mais de uma vez.
			if( isVogal(request.charAt(i)) && vogaisCount.containsKey(request.charAt(i))) {
				vogaisCount.put(request.charAt(i), vogaisCount.get(request.charAt(i)) + 1);
			}
		}
	}
	/**
	 * Obtem avogal que atende a regra de negócio
	 * @param vogaisCount Map<Character, Integer>
	 * @return String
	 */
	private String getVogal(Map<Character, Integer> vogaisCount) {
		String retorno = MSG_DEFAULT;
		
		for(Character key : vogaisCount.keySet()) {
			//No método preencherMap() é verificado se a vogal sucede uma consoante e preeenhce o map com valor 1. 
			//No método verificarRepeticao() é verificado se a vogal se repete, caso não se repita ela é incrementada apenas uma vez, por isso a comparação com o valor 2.
			if(vogaisCount.get(key) == VOGAL_REPEAT) {
				retorno = ""+key;
				break;
			}
		}
		
		return retorno;
	}
	//Verifica se uma letra é uma vogal
	private boolean isVogal(Character charac) {
		String letra = removerAcentos(""+charac);
		return VOGAIS.contains(letra.charAt(0));
	}
	//Caso exista caracteres especiais, devem ser tratados
	private String removerAcentos(String str) {
	    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll(EXP_REG_REMOV_ESP, "");
	}	
	

}
