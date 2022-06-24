package br.edu.femass.dao;

import br.edu.femass.model.Compra;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompraDao extends DaoPostgres implements Dao<Compra>{

    @Override
    public List<Compra> listar() throws Exception {
        String sql = "select * from compra order by nr_pedido";
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
    public void gravar(Compra value) throws Exception {
        String sql = "INSERT INTO compra (data_compra) VALUES (?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setDate(1 , Date.valueOf(value.getDataCompra()));

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setNrPedido(rs.getLong(1));
    }

    @Override
    public void alterar(Compra value) throws Exception {
        String sql = "UPDATE compra set data_compra = ? where nr_pedido = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setDate(1, Date.valueOf(value.getDataCompra()));
        ps.setLong(2, value.getNrPedido());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Compra value) throws Exception {
        String sql = "DELETE from compra where nr_pedido = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getNrPedido());
        ps.executeUpdate();
    }
}
