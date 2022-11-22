package br.com.vr.autorizador.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class TransacaoRequest {

	@NotNull
	private String numeroCartao;
	
	@NotNull
	private String senha;
	
	@NotNull
	private BigDecimal valor;
	
}
