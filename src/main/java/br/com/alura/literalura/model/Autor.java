package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;
  private Integer anoNascimento;
  private Integer anoMorte;

  public Autor() {}

  public Autor(String nome, DadosAutor dadosAutor) {
    this.nome = nome;
    this.anoNascimento = dadosAutor.anoNascimento();
    this.anoMorte = dadosAutor.anoMorte();
  }

  public Autor(DadosAutor dadosAutor) {
    this.nome = dadosAutor.nome();
    this.anoNascimento = dadosAutor.anoNascimento();
    this.anoMorte = dadosAutor.anoMorte();
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Integer getAnoNascimento() {
    return anoNascimento;
  }

  public void setAnoNascimento(Integer anoNascimento) {
    this.anoNascimento = anoNascimento;
  }

  public Integer getAnoMorte() {
    return anoMorte;
  }

  public void setAnoMorte(Integer anoMorte) {
    this.anoMorte = anoMorte;
  }

  public List<Livro> getLivros() {
    return livros;
  }

  public void setLivros(List<Livro> livros) {
    //livros.forEach(l -> l.setLivro(this));
    this.livros = livros;
  }

  @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Um autor pode ter muitos livros
  private List<Livro> livros = new ArrayList<>();

  @Override
  public String toString() {
    return "Autor = " + nome +
        ", ano de nascimento = '" + anoNascimento + '\'' +
        ", ano de morte = " + anoMorte;
  }
}
