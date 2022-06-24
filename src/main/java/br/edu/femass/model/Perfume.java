package br.edu.femass.model;

import lombok.Data;

@Data
public class Perfume {
    private Long id;
    private String nome;
    private Integer ml;
    private Integer estoque;
    private Double precoVenda;

    @Override
    public String toString() {
        return this.id + "-" + this.nome.toUpperCase() + ", " + this.ml + "ml - R$ " + this.precoVenda + " - " + this.estoque + "un";
    }
}
