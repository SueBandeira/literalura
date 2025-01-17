package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.DadosLivrosResultado;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.LivroRepository;
import br.com.alura.literalura.service.ConsumoAPI;
import br.com.alura.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
  private Scanner leitura = new Scanner(System.in);
  private ConsumoAPI consumoAPI = new ConsumoAPI();
  @Autowired
  private ConverteDados conversor = new ConverteDados();

  private final String ENDERECO = "https://gutendex.com/books?search=";

  private List<DadosLivro> dadosLivroBuscados = new ArrayList<>();

  private LivroRepository repositorio;
  private List<Livro> livros = new ArrayList<>();

  public Principal(LivroRepository repositorio) {
    this.repositorio = repositorio;
  }

  public void exibeMenu () {
    var opcao = -1;
    while (opcao != 0) {
      var menu = """
            ===============================================
                    Escolha o número de sua opção:
            ===============================================
            [1] Buscar livros pelo título
            [2] Listar livros registrados
            [3] Listar autores registrados
            [4] Listar autores vivos em um determinado ano
            [5] Listar livros em um determinado idioma
            
            [0]  Sair
            """;

      System.out.println(menu);
      opcao = leitura.nextInt();
      leitura.nextLine();

      switch (opcao) {
        case 1:
          buscarLivroWeb();
          break;
        case 2:
          listarLivrosBuscados();
          break;
        case 3:
          break;
        case 0:
          System.out.println("Saindo...");
          break;
        default:
          System.out.println("Opção inválida");
      }
    }
  }

  private void buscarLivroWeb()  {
      DadosLivro dados = getDadosLivro();
      //Autor autor = new Autor(dados.autor().get(0).nome(), dados.autor().get(0));
      Livro livro = new Livro(dados);
      repositorio.save(livro);
      System.out.println("Livro salvo: " + livro);
  }

  private DadosLivro getDadosLivro() {
    System.out.println("Digite o nome do livro para busca: ");
    var nomeLivro = leitura.nextLine();
    var json = consumoAPI.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
    DadosLivro dados = conversor.obterDados(json, DadosLivro.class);

    DadosLivrosResultado dadosResultado = conversor.obterDados(json, DadosLivrosResultado.class);

    if (dadosResultado != null && !dadosResultado.results().isEmpty()) {
      DadosLivro dadosLivro = dadosResultado.results().getFirst();
      System.out.println(dadosLivro.toString());
      List<DadosAutor> autores = dadosLivro.autor();
      dadosLivroBuscados.add(dadosLivro);
      return dadosLivro;
    } else {
      System.out.println("Nenhum livro encontrado.");
    }
    return null;
  }

  private void listarLivrosBuscados (){
    livros = repositorio.findAll();
    livros.stream()
        .sorted(Comparator.comparing(livro -> livro.getAutor().getNome()))
        .forEach(System.out::println);
  }

//  //private void listarLivrosBuscados () {
//    dadosLivroBuscados.forEach(System.out::println);
//  }
}
