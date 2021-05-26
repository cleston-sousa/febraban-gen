package br.net.gits.febraban.services.exceptions;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4619839568788474815L;

	public EntityNotFoundException(String mensagem) {
		super(mensagem);
	}

	public EntityNotFoundException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
