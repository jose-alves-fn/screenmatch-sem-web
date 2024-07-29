package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ConsumoAPI consumoAPI = new ConsumoAPI();
		String endereco = consumoAPI.obterDados("http://www.omdbapi.com/?t=gilmore+girls&apikey=45aad444");
		System.out.println(endereco);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(endereco, DadosSerie.class);
		System.out.println(dados);

//		String cafe = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json");
//		System.out.println(cafe);
















	}
}
