package br.edu.femass.testes;

import br.edu.femass.dao.PerfumeDao;
import br.edu.femass.model.Perfume;

import java.util.List;

public class PerfumeTest {

    public static void main(String[] args) {
        testarExcluir();
        testarListar();
    }

    private static void testarGravar() {
        Perfume perfume  = new Perfume();
        perfume.setNome("Varsha");
        perfume.setMl(50);
        perfume.setPrecoVenda(119.0);

        try {
            new PerfumeDao().gravar(perfume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testarListar() {
        try {
            List<Perfume> perfumes = new PerfumeDao().listar();
            for (Perfume a: perfumes) {
                System.out.println(a.getId().toString() + "- " +  a.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void testarAlterar() {
        Perfume perfume  = new Perfume();
        perfume.setNome("Xclusive");
        perfume.setMl(50);
        perfume.setPrecoVenda(125.0);
        perfume.setId(1L);

        try {
            new PerfumeDao().alterar(perfume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testarExcluir() {
        Perfume perfume  = new Perfume();
        perfume.setNome("Xclusive");
        perfume.setMl(50);
        perfume.setPrecoVenda(125.0);
        perfume.setId(1L);
        try {
            new PerfumeDao().excluir(perfume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
