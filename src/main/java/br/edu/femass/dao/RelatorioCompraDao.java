package br.edu.femass.dao;

import br.edu.femass.model.Compra;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RelatorioCompraDao extends DaoPostgres implements Dao<Compra>{

    @Override
    public List<Compra> listar() throws Exception {
        String sql = "select * from compra where data_compra = current_date order by nr_pedido";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Compra> compras = new ArrayList<Compra>();
        while (rs.next()) {
            Compra compra = new Compra();
            compra.setNrPedido(rs.getLong("nr_pedido"));
            compra.setDataCompra(rs.getDate("data_compra").toLocalDate());
            compras.add(compra);
        }
        return compras;
    }

    @Override
    public void gravar(Compra value) throws Exception {}

    @Override
    public void alterar(Compra value) throws Exception {}

    @Override
    public void excluir(Compra value) throws Exception {}
}
