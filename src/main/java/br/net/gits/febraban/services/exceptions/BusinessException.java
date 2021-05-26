package br.net.gits.febraban.services.exceptions;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 7550823308452313156L;

	public BusinessException(String mensagem) {
		super(mensagem);
	}

	public BusinessException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
