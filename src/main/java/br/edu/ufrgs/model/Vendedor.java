package br.edu.ufrgs.model;

/**
 * Classe que representa um vendedor.
 * Não há métodos setters, pois os atributos são definidos no construtor e não devem ser alterados posteriormente.
 */

public class Vendedor{
    private String nome;
    private int vendedor_id;

    public Vendedor(String nome, int vendedor_id) {
        this.nome = nome;
        this.vendedor_id = vendedor_id;
    }

    public String getNome() {
        return nome;
    }

    public int getVendedor_id() {
        return vendedor_id;
    }

}