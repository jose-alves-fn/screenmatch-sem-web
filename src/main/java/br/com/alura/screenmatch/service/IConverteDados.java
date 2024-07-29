package br.com.alura.screenmatch.service;

public interface IConverteDados {

    // Como não está especificado o que retornará, o cabeçalho deixa T como genérico
    // Na hora do chamamento do método, é que se define o retono pelo meio do parametro Class<T>
    <T> T obterDados(String json, Class<T> classe);

}
