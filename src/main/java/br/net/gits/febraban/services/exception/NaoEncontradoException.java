package br.net.gits.febraban.services.exception;

public class NaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = -4619839568788474815L;

	public NaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public NaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
