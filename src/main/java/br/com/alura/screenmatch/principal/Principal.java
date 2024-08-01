package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

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
        // Para cada temporada, imrpima o conteúdo
        temporadas.forEach(System.out::println);

        // Usando lambdas para imprimir todos os episodios por nome
        // Para cada temporada t em temporadas, retorne a lista de episodios.
        // Para cada episodio e na lista de episodios, imprima o titulo.
        temporadas.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));


        // Gerando uma lista com os dados de todos os episódios

        // Converte a lista de temporadas (temporadas) em um stream.
        // Para cada temporada, cria um stream dos seus episódios.
        // "Achata" esses streams de episódios em um único stream contínuo de episódios, usando flatMap.
        // Coleta todos os episódios em uma única lista editavel (dadosEpisodios).

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodio().stream())
                .collect(Collectors.toList());

        // Verificando os episodios com melhor avaliacao
        // Atentar para a lógica invertida do filter (precisamos de true, por isso inverte-se)
        // Valores equals "N/A"serão removidos do stream()\// ! é o operador de negação que inverte o resultado da expressão.
        // !e.avaliacao().equalsIgnoreCase("N/A") será true se a avaliação não for "N/A".
        // !e.avaliacao().equalsIgnoreCase("N/A") será false se a avaliação for "N/A".
        System.out.println("\nTop 5 episódios em avaliação:");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);






















//        List<String> nomes = Arrays.asList("liliam", "marco", "josé", "ana paula", "geanny", "nívea");
//
//        // Stream
//        nomes.stream()
//                .sorted() // Op. intermediária
//                .limit(3) // Op. intermediária
//                .filter(n -> n.startsWith("a")) // Op. intermediária
//                .map(n -> n.toUpperCase())
//                .forEach(System.out::println);// Op. final



    }
}
