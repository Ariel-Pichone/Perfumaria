package br.edu.femass.model;

import lombok.Data;

@Data
public class DetalheCompra {
    private Long pedido;
    private Long perfume;
    private Integer quantidade;
    private Double precoCompra;

    @Override
    public String toString() {
        return this.perfume + "- qtd: " + quantidade + ", R$ " + precoCompra;
    }
}
