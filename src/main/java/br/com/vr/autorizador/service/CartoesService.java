package br.com.vr.autorizador.service;

import javax.validation.Valid;

import br.com.vr.autorizador.dto.request.TransacaoRequest;
import br.com.vr.autorizador.exception.CartaoInexistenteException;
import br.com.vr.autorizador.exception.NumeroDeCartaoEmUsoException;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import br.com.vr.autorizador.model.Cartao;

public interface CartoesService {

	Cartao create(Cartao cartao) throws NumeroDeCartaoEmUsoException;

	Cartao findByNumero(String numero);

	Cartao debita(@Valid TransacaoRequest request) throws CartaoInexistenteException, SenhaInvalidaException, SaldoInsuficienteException;
	
	boolean validaSenha(Cartao cartao, String senha);
	
}
