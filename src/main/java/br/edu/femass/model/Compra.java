package br.edu.femass.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Compra {
    private Long nrPedido;
    private LocalDate dataCompra;

    @Override
    public String toString() {
        return "NR: " + nrPedido + " Data: (" + dataCompra + ')';
    }
}
