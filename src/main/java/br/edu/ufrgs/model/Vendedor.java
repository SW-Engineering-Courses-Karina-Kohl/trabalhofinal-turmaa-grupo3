package br.edu.ufrgs.model;

public class Vendedor {
  private String nome;
  private int vendedorId;
  private double salario;                                   // salário com bônus já aplicado
  private Venda vendas[];                                   // conjunto de vendas feitas pelo vendedor
  
  public Vendedor(String nome, int vendedorId) {
    this.nome = nome;
    this.vendedorId = vendedorId;
  }

  public String getNome() {
    return nome;
  }

  public int getVendedorId() {
    return vendedorId;
  }

  public double getSalario() {
    return salario; 
  }

  public double calculaSalario() {                          // método para calcular salário com bônus já aplicado
  
  }
}
