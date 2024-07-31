package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
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
        System.out.println("Digite o nome da série que deseja: ");
        String nomeSerie = leitura.nextLine();
        String json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        // Criando uma lista para receber as informações de cada temporada
        List<DadosTemporada> temporadas = new ArrayList<>();

        // Populando o objeto dadosTemporada e usando dadosSerie.totalTemporadas() para limitar o for
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        // Imprimindo as informações dos episódios por temporada
        for (DadosTemporada temporada : temporadas) {
            System.out.println(temporada);
        }

// 		temporadas.forEach(System.out::println);
//      http://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=45aad444



    }
}
