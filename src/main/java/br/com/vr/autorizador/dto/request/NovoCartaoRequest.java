package br.com.vr.autorizador.dto.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class NovoCartaoRequest {

	@NotNull
	private String numeroCartao;
	
	@NotNull
	private String senha;
	
}
