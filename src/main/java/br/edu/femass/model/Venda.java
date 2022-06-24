package br.edu.femass.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Venda {
    private Long nrPedido;
    private LocalDate dataVenda;

    @Override
    public String toString() {
        return "NR: " + nrPedido + " Data: (" + dataVenda + ')';
    }
}
