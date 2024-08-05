package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    // **** Definindo o método usado para buscar as informações da série, temporadas, etc
    public void exibeMenu() {
        System.out.println("Digite o nome da série que deseja: ");
        String nomeSerie = leitura.nextLine();
        String json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        // **** Criando uma lista para receber as informações de cada temporada
        List<DadosTemporada> temporadas = new ArrayList<>();

        // **** Populando o objeto dadosTemporada e usando dadosSerie.totalTemporadas() para limitar o for
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        // **** Imprimindo as informações dos episódios por temporada
        // Para cada temporada, imrpima o conteúdo
        temporadas.forEach(System.out::println);

        // **** Usando lambdas para imprimir todos os episodios por nome
        // Para cada temporada t em temporadas, retorne a lista de episodios.
        // Para cada episodio e na lista de episodios, imprima o titulo.
        temporadas.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));

        // **** Gerando uma lista com os dados de todos os episódios
        // Converte a lista de temporadas (temporadas) em um stream.
        // Para cada temporada, cria um stream dos seus episódios.
        // "Achata" esses streams de episódios em um único stream contínuo de episódios, usando flatMap.
        // Coleta todos os episódios em uma única lista editavel (dadosEpisodios).
        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodio().stream())
                .collect(Collectors.toList());

        // **** Verificando os episodios com melhor avaliacao
        // Atentar para a lógica invertida do filter (precisamos de true, por isso inverte-se)
        // Valores equals "N/A"serão removidos do stream()\// ! é o operador de negação que inverte o resultado da expressão.
        // !e.avaliacao().equalsIgnoreCase("N/A") será true se a avaliação não for "N/A".
        // !e.avaliacao().equalsIgnoreCase("N/A") será false se a avaliação for "N/A".
        System.out.println("\nTop 10 episódios em avaliação:");
        dadosEpisodios.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Primeiro filtro (sem os N/A)" + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenando pelo maior valor" + e))
                .limit(10)
                .peek(e-> System.out.println("Pegando os 10 maiores valores" + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Subindo para upperCase"))
                .forEach(System.out::println);

        // **** Lista de episodios com dados personalizados
        // Usa a interface List para por meio de uma classe Episodio, instanciar um objeto episodio que vai receber
        // uma lista temporadas, essa por sua vez vai ser transformada em uma stream()
        // Aplica-se um processo de flatmap na lista temporadas, onde cada t na lista, vai ter um episodio recebendo uma stream
        // Para cada dado d do episodiocontido em temporadas, um construtor da clase episodio vai ser chamado, passando dois parametros, sendo que um
        // sera advindo de t.temporada que vem da classe dados Temporada, e outro vem de outra classe, no caso Dados episodio
        // ao final, para terminar a stream, se joga numa lista

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodio().stream()
                        .map(d -> new Episodio(t.temporada(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        // **** Verificando um trecho do título para localizar um episodio
        // A operaçao final findFirst precisa ter seu retorno incluido em alguma variável
        // Optional é um objeto container que pode ou não cpnter um valor não nulo

        System.out.println("Digite o episódio ou parte dele: ");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if (episodioBuscado.isPresent()) { // Verificando se há um objeto dentro do container
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada()); // .get() para acessar o conteúdo do container Optional
        } else {
            System.out.println("Episódio não encontrado!");
        }

//        // **** Criando uma visualização de episódios por meio de escolha da data pelo usuário
//
//        System.out.println("A partir de qual ano deseja ver os episódios?");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        // Defininco o recebimento da data
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        // Construindo um formatador para tratar a data tipo BR
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() + ", " +
//                                "Episódio: " + e.getTitulo() + ", " +
//                                "Data de lançamento: " + e.getDataLancamento().format(formatador)
//                ));

        // **** Criando métricas (avaliação por temporada), usando um Map para receber a coleta
        // As tempradas teram um inteiro como identificador (chave)
        // As avaliações médias terão um Double como valor
        // Durante  a operação groupingBy, o stream é percorrido e cada episódio é classificado na coleção com base no valor retornado por getTemporada.
        // para cada objeto Episodio no stream, o método getTemporada deve ser chamado
        // Durante a operação averagingDouble, o stream é percorrido e a avaliação de cada episódio é usada para calcular a média.
        // para cada objeto Episodio no stream, o método getAvaliacao deve ser chamado.

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                //.filter(e -> e.getTemporada() != null && e.getAvaliacao() != null)
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        // **** Criando métricas para dados, utilizando classes específicas para isso
        // DoubleSummaryStatistics e IntSummaryStatistics são exemplos

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println("Média de avaliações dos episódios: " + est.getAverage() +
                "\nQuantidade de avaliações: " + est.getCount() +
                "\nAvaliação máxima: " + est.getMax() +
                "\nAvaliação mínima: " + est.getMin());


























    }
}
