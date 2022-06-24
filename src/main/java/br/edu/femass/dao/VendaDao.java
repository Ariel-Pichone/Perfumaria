package br.edu.femass.dao;

import br.edu.femass.model.Venda;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VendaDao extends DaoPostgres implements Dao<Venda>{

    @Override
    public List<Venda> listar() throws Exception {
        String sql = "select * from venda order by nr_pedido";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Venda> vendas = new ArrayList<Venda>();
        while (rs.next()) {
            Venda venda = new Venda();
            venda.setNrPedido(rs.getLong("nr_pedido"));
            venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
            vendas.add(venda);
        }
        return vendas;
    }

    @Override
    public void gravar(Venda value) throws Exception {
        String sql = "INSERT INTO venda (data_venda) VALUES (?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setDate(1 , Date.valueOf(value.getDataVenda()));

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setNrPedido(rs.getLong(1));
    }

    @Override
    public void alterar(Venda value) throws Exception {
        String sql = "UPDATE venda set data_venda = ? where nr_pedido = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setDate(1, Date.valueOf(value.getDataVenda()));
        ps.setLong(2, value.getNrPedido());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Venda value) throws Exception {
        String sql = "DELETE from venda where nr_pedido = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getNrPedido());
        ps.executeUpdate();
    }
}
