package br.com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String titulo;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "autor_id")
  private Autor autor;

  @ElementCollection(targetClass = Idiomas.class)
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "livro_idiomas", joinColumns = @JoinColumn(name = "livro_id"))
  @Column(name = "idioma")
  private List<String> idiomas = new ArrayList<>();

  private Integer downloadsLivro;

  public List<Idiomas> getIdiomas(List<String> idiomas) {
    List<Idiomas> idioma = new ArrayList<>();

      idiomas.forEach(i -> {
        try {
          idioma.add(Idiomas.valueOf(i.toUpperCase()));
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage());
        };
      });

    return idioma;
  }

  public Livro(DadosLivro dados) {
    this.titulo = dados.titulo();
    this.autor = new Autor(dados.autor().getFirst());
    this.idiomas = this.getIdiomas(dados.idioma());
    this.downloadsLivro = dados.downloadsLivro();
  }

  public Livro (String nome, DadosLivro dados) {
    this.titulo = nome;
    this.autor = (Autor) dados.autor();
    this.idiomas = dados.idioma();
    this.downloadsLivro = dados.downloadsLivro();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Autor getAutor() {
    return autor;
  }

  public void setAutor(Autor autor) {
    this.autor = autor;
  }

  public Livro(List<Idiomas> idiomas) {
    this.idiomas = idiomas;
  }

  public Integer getDownloadsLivro() {
    return downloadsLivro;
  }

  public void setDownloadsLivro(Integer downloadsLivro) {
    this.downloadsLivro = downloadsLivro;
  }

  public Livro () {}

//  public Livro(String titulo, Autor autor, List<Idiomas> idioma, Integer downloadsLivro) {
//    this.titulo = titulo;
//    this.autor = autor;
//    this.idiomas = idiomas;
//    this.downloadsLivro = downloadsLivro;
//  }

  @Override
  public String toString() {
    return
            " titulo='" + titulo + '\'' +
            ", autor=" + autor +
            ", idioma=" + idiomas +
            ", downloadsLivro='" + downloadsLivro;
  }

}
