package br.com.vr.autorizador;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import br.com.vr.autorizador.controller.CartoesControllerTest;

@SelectClasses({
	CartoesControllerTest.class
})
@Suite
public class TestSuite {

}