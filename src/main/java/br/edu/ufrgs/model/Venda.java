package br.edu.ufrgs.model;

public class Venda{
    private Vendedor vendedor;
    private String vendaId;
    private double valorVenda;

    public Venda(Vendedor vendedor, String vendaId, double valorVenda) {
        this.vendedor = vendedor;
        this.vendaId = vendaId;
        this.valorVenda = valorVenda;
    }

  public Vendedor getVendedor() {
    return vendedor;
  }

    public String getVendaId() {
        return vendaId;
    }

  public double getValorVenda() {
    return valorVenda;
  }

}
