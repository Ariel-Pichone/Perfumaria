package br.edu.femass.testes;

import br.edu.femass.dao.CompraDao;
import br.edu.femass.dao.PerfumeDao;
import br.edu.femass.model.Compra;
import br.edu.femass.model.Perfume;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class CompraTest {

    public static void main(String[] args) {
        testarListar();
    }

    private static void testarGravar() {
        Compra compra  = new Compra();
        compra.setDataCompra(LocalDate.now());
        try {
            new CompraDao().gravar(compra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testarListar() {
        try {
            List<Compra> compras = new CompraDao().listar();
            for (Compra a: compras) {
                System.out.println(a.getNrPedido().toString() + "- " +  a.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testarAlterar() {
        Compra compra  = new Compra();
        compra.setDataCompra(LocalDate.now());
        compra.setNrPedido(1L);

        try {
            new CompraDao().alterar(compra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testarExcluir() {
        Compra compra  = new Compra();
        compra.setDataCompra(LocalDate.now());
        compra.setNrPedido(1L);
        try {
            new CompraDao().excluir(compra);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
