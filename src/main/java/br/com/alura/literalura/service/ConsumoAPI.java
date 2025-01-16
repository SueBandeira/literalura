package br.com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
  public String obterDados(String endereco) {
    HttpClient client = HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .build();

    HttpResponse<String> response;

    try {
      System.out.println("Requisitando URL: " + endereco);
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() != 200) {
        throw new RuntimeException("Erro ao consumir API: status " + response.statusCode());
      }
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Erro ao enviar requisição: " + e.getMessage(), e);
    }

    System.out.println("Resposta da API: " + response.body());
    return response.body();
  }
}
