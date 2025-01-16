package br.com.alura.literalura.service;

public interface IConverteDados {
  <T> T obterDados(String json, Class<T> classe);// esse "t"s que dizer que é generics
}
