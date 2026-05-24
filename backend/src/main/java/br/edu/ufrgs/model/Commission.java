package br.edu.ufrgs.model;

public class Commission extends Seller {

    private double salesTotal;
    private double commission;

    public Commission(String name, int selleriD, double salesTotal, double comission) {
        super(name, selleriD);
        this.salesTotal = salesTotal;
        this.commission = comission;

    }

    public double getSalesTotal() {
        return this.salesTotal;

    }

    public double getCommission() {
        return this.commission;

    }
}
