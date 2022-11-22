package br.com.vr.autorizador.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.autorizador.dto.request.TransacaoRequest;
import br.com.vr.autorizador.exception.CartaoInexistenteException;
import br.com.vr.autorizador.exception.SaldoInsuficienteException;
import br.com.vr.autorizador.exception.SenhaInvalidaException;
import br.com.vr.autorizador.service.CartoesService;
import br.com.vr.autorizador.values.TransacaoEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/transacoes")
@Api(value = "TransacoesController", description = "Serviços relacionados com as transações dos cartões.", tags = {"TransacoesController"})
public class TransacoesController {
	
	@Autowired
	private CartoesService cartoesService;
	
	@PostMapping
	@ApiOperation(value = "Executa transação", notes = "Debita o saldo do cartão, caso seja possível.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Caso a transação tenha sido executada com sucesso"),
        @ApiResponse(code = 422, message = "Caso a transação não tenha sido executada")
    })
	public ResponseEntity<Object> executaTransacao(@RequestBody @Valid TransacaoRequest request) {
		try {
			cartoesService.debita(request);
		} catch (CartaoInexistenteException e) {
			return new ResponseEntity<>(TransacaoEnum.CARTAO_INEXISTENTE.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (SenhaInvalidaException e) {
			return new ResponseEntity<>(TransacaoEnum.SENHA_INVALIDA.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (SaldoInsuficienteException e) {
			return new ResponseEntity<>(TransacaoEnum.SALDO_INSUFICIENTE.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return new ResponseEntity<>(TransacaoEnum.OK.toString(), HttpStatus.CREATED);
	}

}
