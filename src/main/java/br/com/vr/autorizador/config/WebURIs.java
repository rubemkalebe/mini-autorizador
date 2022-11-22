package br.com.vr.autorizador.config;

public final class WebURIs {

	private WebURIs() {
		throw new IllegalStateException("Utility class");
	}

    public static final String[] PUBLIC_URI = {    		
    		"/v2/api-docs",
            "/swagger-ui.html",
            "/configuration/ui",
    		"/webjars/**",
    		"/swagger-resources/**",
    		"/configuration/security"
    };

    public static final String[] PROTECTED_URI = {
    		"/cartoes/**",
    		"/transacoes",
    		
	};
    
}
