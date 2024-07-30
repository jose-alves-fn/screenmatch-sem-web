// Interface que permite quem a implementa, usar um método com generics, capaz de desserializar o json e popular
// diferentes tipos de objetos de acordo com a classe. É preciso informar a classe no parametro do chamamento do método.

package br.com.alura.screenmatch.service;

public interface IConverteDados {

    // Como não está especificado o que retornará, o cabeçalho deixa T como genérico
    // Na hora do chamamento do método, é que se define o retono pelo meio do parametro Class<T>
    <T> T obterDados(String json, Class<T> classe);

}

