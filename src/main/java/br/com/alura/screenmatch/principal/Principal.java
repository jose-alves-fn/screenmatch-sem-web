package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Principal {

    // Instanciando objetos que serão chamados pela classe
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    // Definindo atributos constantes relativos aos endereço de busca e chave da API
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=45aad444";

    // Definindo o método usado para buscar as informações da série, temporadas, etc
    public void exibeMenu() {
//        System.out.println("Digite o nome da série que deseja: ");
//        String nomeSerie = leitura.nextLine();
//        String json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
//        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
//        System.out.println(dadosSerie);
//
//        // Criando uma lista para receber as informações de cada temporada
//        List<DadosTemporada> temporadas = new ArrayList<>();
//
//        // Populando o objeto dadosTemporada e usando dadosSerie.totalTemporadas() para limitar o for
//        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
//            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
//            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//            temporadas.add(dadosTemporada);
//        }
//
//        // Imprimindo as informações dos episódios por temporada
//        // Para cada temporada, imrpima o conteúdo
//        temporadas.forEach(System.out::println);
//
//        // Usando lambdas para imprimir todos os episodios por nome
//        // Para cada temporada t em temporadas, retorne a lista de episodios.
//        // Para cada episodio e na lista de episodios, imprima o titulo.
//        temporadas.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));


        List<String> nomes = Arrays.asList("liliam", "marco", "josé", "ana paula", "geanny", "nívea");

        // Stream
        nomes.stream()
                .sorted() // Op. intermediária
                .limit(3) // Op. intermediária
                .filter(n -> n.startsWith("a")) // Op. intermediária
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);// Op. final



    }
}
