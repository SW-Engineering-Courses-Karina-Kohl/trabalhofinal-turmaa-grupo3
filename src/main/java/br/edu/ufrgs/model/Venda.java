package br.edu.ufrgs.model;

import java.time.*;


public class Venda {
  private Vendedor vendedor;
  private int vendaId;
  private LocalDate dataVenda;
  private double valorVenda;

  public Venda(Vendedor vendedor, int vendaId, LocalDate dataVenda, double valorVenda) {
    this.vendedor = vendedor;
    this.dataVenda = dataVenda;
    this.valorVenda = valorVenda;
  }

  public Vendedor getVendedor() {
    return vendedor;
  }

  public LocalDate getDataVenda() {
    return dataVenda;
  }

  public double getValorVenda() {
    return valorVenda;
  }

}
