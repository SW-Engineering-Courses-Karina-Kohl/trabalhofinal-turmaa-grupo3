package br.edu.ufrgs.model;

<<<<<<< HEAD
public class Venda{
    private Vendedor vendedor;
    private String vendaId;
    private double valorVenda;

    public Venda(Vendedor vendedor, String vendaId, double valorVenda) {
        this.vendedor = vendedor;
        this.vendaId = vendaId;
        this.valorVenda = valorVenda;
    }
=======
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
>>>>>>> af0ce1c3d32f30a00e885165d190d2d99cbdd9de

  public Vendedor getVendedor() {
    return vendedor;
  }

<<<<<<< HEAD
    public String getVendaId() {
        return vendaId;
    }
=======
  public LocalDate getDataVenda() {
    return dataVenda;
  }
>>>>>>> af0ce1c3d32f30a00e885165d190d2d99cbdd9de

  public double getValorVenda() {
    return valorVenda;
  }

}
