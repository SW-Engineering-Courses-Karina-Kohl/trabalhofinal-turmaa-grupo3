package br.edu.ufrgs.infrastructure;

import br.edu.ufrgs.model.Venda;
import br.edu.ufrgs.model.Vendedor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorVendasCsv {

    public List<Venda> lerVendas(String caminhoArquivo) throws IOException{

        List<Venda> vendas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                Vendedor vendedor = new Vendedor(dados[2], Integer.parseInt(dados[1]));
                Venda venda = new Venda(vendedor, dados[0], Double.parseDouble(dados[3]));
                vendas.add(venda);
            }

        } 

        return vendas;
    }
}