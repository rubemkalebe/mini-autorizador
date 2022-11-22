package br.com.vr.autorizador.values;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum TransacaoEnum {

	OK,
	SALDO_INSUFICIENTE,
	SENHA_INVALIDA,
	CARTAO_INEXISTENTE;
	
}
