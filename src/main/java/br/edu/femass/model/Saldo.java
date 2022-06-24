package br.edu.femass.model;

import lombok.Data;

@Data
public class Saldo {
    public Long pedido;
    public Double total;

    @Override
    public String toString() {
        return "Total: R$ " + this.total;
    }
}
