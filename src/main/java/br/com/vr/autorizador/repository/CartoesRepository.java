package br.com.vr.autorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vr.autorizador.model.Cartao;

public interface CartoesRepository extends JpaRepository<Cartao, Integer> {

	Cartao findByNumero(String numero);
	
}
