package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Instanciando objeto da classe de consumo de API
		ConsumoAPI consumoAPI = new ConsumoAPI();

		// Instanciando objeto da classe de conversão do json
		// Detalhe: o método obterDados é genérico, o que é ótimo para receber objetos distintos para desserializar
		ConverteDados conversor = new ConverteDados();

		// Consumindo e executando o conversor de dados de serie
		String json = consumoAPI.obterDados("http://www.omdbapi.com/?t=gilmore+girls&apikey=45aad444");
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

		// Consumindo e executando o conversor de dados de episodios
		json = consumoAPI.obterDados("http://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=45aad444");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		// Consumindo e executando o conversor de dados de temporadas
























//		String cafe = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json");
//		System.out.println(cafe);
















	}
}
