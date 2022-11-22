package br.com.vr.autorizador.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
public class NovoCartaoRequest {

	private String numeroCartao;
	
	private String senha;
	
}
