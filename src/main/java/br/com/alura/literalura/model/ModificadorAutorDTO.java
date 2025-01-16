package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModificadorAutorDTO(@JsonAlias("authors") List<String> autores) {
  /*O DTO serve para que os dados sejam transferidos de maneira correta, pode ser bidirecional*/
}
