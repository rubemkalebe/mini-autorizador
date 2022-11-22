package br.com.vr.autorizador.service;

import br.com.vr.autorizador.exception.NumeroDeCartaoEmUsoException;
import br.com.vr.autorizador.model.Cartao;

public interface CartoesService {

	Cartao create(Cartao cartao) throws NumeroDeCartaoEmUsoException;

	Cartao findByNumero(String numero);
	
}
