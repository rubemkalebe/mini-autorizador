package br.com.vr.autorizador.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vr.autorizador.dto.request.TransacaoRequest;
import br.com.vr.autorizador.exception.CartaoInexistenteException;
import br.com.vr.autorizador.exception.NumeroDeCartaoEmUsoException;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import br.com.vr.autorizador.model.Cartao;

@Service
@Transactional
public class CartoesServiceImpl implements CartoesService {

	@Autowired
	private CartoesRepository cartoesRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Cartao create(Cartao cartao) throws NumeroDeCartaoEmUsoException {
		if(findByNumero(cartao.getNumero()) != null) {
			throw new NumeroDeCartaoEmUsoException();
		}
		
		cartao.setDataCriacao(LocalDateTime.now());
		cartao.setSaldo(BigDecimal.valueOf(500.00)); // todo cartao deve ser criado com saldo inicial de 500.00
		cartao.setSenha(passwordEncoder.encode(cartao.getSenha())); // criptografa senha antes de persistir
		
		return cartoesRepository.save(cartao);
	}

	@Override
	public Cartao findByNumero(String numero) {
		return cartoesRepository.findByNumero(numero);
	}

	@Override
	public Cartao debita(@Valid TransacaoRequest request) throws CartaoInexistenteException, SenhaInvalidaException, SaldoInsuficienteException {
		Cartao cartao = findByNumero(request.getNumeroCartao());
		
		if(cartao == null) {
			throw new CartaoInexistenteException();
		}
		
		if(!validaSenha(cartao, request.getSenha())) {
			throw new SenhaInvalidaException();
		}
		
		if(request.getValor().compareTo(cartao.getSaldo()) == 1) {
			throw new SaldoInsuficienteException();
		}
		
		cartao.setSaldo(cartao.getSaldo().subtract(request.getValor()));
		
		return cartoesRepository.save(cartao);
	}

	@Override
	public boolean validaSenha(Cartao cartao, String senha) {
		return passwordEncoder.matches(senha, cartao.getSenha());
	}

}
