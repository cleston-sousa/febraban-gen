package br.net.gits.febraban.services.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 7550823308452313156L;

	public NegocioException(String mensagem) {
		super(mensagem);
	}

	public NegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
