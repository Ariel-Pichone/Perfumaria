package br.edu.femass.model;

import lombok.Data;

@Data
public class DetalheVenda {
    private Long pedido;
    private Long perfume;
    private Integer quantidade;
    private Double precoVenda;

    @Override
    public String toString() {
        return this.perfume + "- qtd: " + quantidade + ", R$ " + precoVenda;
    }
}
