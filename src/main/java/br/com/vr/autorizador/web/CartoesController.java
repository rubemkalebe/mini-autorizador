package br.com.vr.autorizador.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.dto.request.NovoCartaoRequest;
import br.com.vr.autorizador.exception.NumeroDeCartaoEmUsoException;
import br.com.vr.autorizador.model.Cartao;
import br.com.vr.autorizador.service.CartoesService;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/cartoes")
@Api(value = "CartoesController", description = "Serviços relacionados com manutenção de cartões.", tags = {"CartoesController"})
public class CartoesController {
	
	@Autowired
	private CartoesService cartoesService;
	
	@PostMapping
	public ResponseEntity<Object> criarCartao(@RequestBody @Valid NovoCartaoRequest request) {
		Cartao cartao = Cartao.builder()
				.numero(request.getNumeroCartao())
				.senha(request.getSenha())
				.build();
		
		try {
			cartao = cartoesService.create(cartao);
		} catch (NumeroDeCartaoEmUsoException e) {
			return new ResponseEntity<>(request, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(request, HttpStatus.CREATED);
	}
	
	@GetMapping("/{numeroCartao}")
	public ResponseEntity<Object> obtemSaldo(@PathVariable String numeroCartao) {
		Cartao cartao = cartoesService.findByNumero(numeroCartao);
		
		if(cartao == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(cartao.getSaldo(), HttpStatus.OK);
	}

}
