package br.com.vr.autorizador.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vr.autorizador.exception.NumeroDeCartaoEmUsoException;
import br.com.vr.autorizador.model.Cartao;

@Service
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
		cartao.setSenha(passwordEncoder.encode(cartao.getSenha())); // criptografa senha antes de persistir
		
		return cartoesRepository.save(cartao);
	}

	@Override
	public Cartao findByNumero(String numero) {
		return cartoesRepository.findByNumero(numero);
	}

}
