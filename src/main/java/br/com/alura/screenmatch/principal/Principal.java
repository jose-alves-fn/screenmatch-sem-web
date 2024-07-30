package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {

    // Instanciando um objeto para leitura + objeto para ConsumoAPI e deixando como privado dentro de um atributo de classe
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();

    // Definindo atributos constantes relativos aos endereço de busca e chave da API
    private final String ENDERECO = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&45aad888";

    // Definindo o método usado para buscar as informações da série
    public void exibeMenu(){
        System.out.println("Digite o nome da série que deseja: ");
        String nomeSerie = leitura.nextLine();
        String json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
    }
}
