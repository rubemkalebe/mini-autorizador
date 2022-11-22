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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cartoes")
@Api(value = "CartoesController", description = "Serviços relacionados com manutenção de cartões.", tags = {"CartoesController"})
public class CartoesController {
	
	@Autowired
	private CartoesService cartoesService;
	
	@PostMapping
	@ApiOperation(value = "Cria cartão", notes = "Cria um cartão com o número e senha informados.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "O cartão foi criado com sucesso"),
        @ApiResponse(code = 422, message = "O número informado já está sendo usado para outro cartão")
    })
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
	@ApiOperation(value = "Obtém saldo", notes = "Retorna o saldo do cartão, caso exista o número.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Saldo do cartão encontrado"),
        @ApiResponse(code = 404, message = "Não foi encontrado um cartão com o número informado")
    })
	public ResponseEntity<Object> obtemSaldo(@PathVariable String numeroCartao) {
		Cartao cartao = cartoesService.findByNumero(numeroCartao);
		
		if(cartao == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(cartao.getSaldo(), HttpStatus.OK);
	}

}
