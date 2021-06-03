package br.net.gits.febraban.api.controller.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportarArquivoCidadeRequest {

	private MultipartFile arquivo;

}
