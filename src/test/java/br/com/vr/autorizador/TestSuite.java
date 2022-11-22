package br.com.vr.autorizador;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import br.com.vr.autorizador.controller.CartoesControllerTest;
import br.com.vr.autorizador.controller.TransacoesControllerTest;

@SelectClasses({
	CartoesControllerTest.class,
	TransacoesControllerTest.class
})
@Suite
public class TestSuite {

}