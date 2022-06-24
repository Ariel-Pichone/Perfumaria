package br.edu.femass.dao;

import br.edu.femass.model.DetalheVenda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetalheVendaDao extends DaoPostgres implements Dao<DetalheVenda>{

    @Override
    public List<DetalheVenda> listar() throws Exception {
        String sql = "select * from detalhe_venda order by pedido";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<DetalheVenda> detalheVendas = new ArrayList<DetalheVenda>();
        while (rs.next()) {
            DetalheVenda detalheVenda = new DetalheVenda();
            detalheVenda.setPedido(rs.getLong("pedido"));
            detalheVenda.setPerfume(rs.getLong("perfume"));
            detalheVenda.setQuantidade(rs.getInt("quantidade"));
            detalheVenda.setPrecoVenda(rs.getDouble("preco_venda"));
            detalheVendas.add(detalheVenda);
        }
        return detalheVendas;
    }
    @Override
    public void gravar(DetalheVenda value) throws Exception {
        String sql = "INSERT INTO detalhe_venda (pedido, perfume, quantidade, preco_venda) VALUES (?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setLong(1, value.getPedido());
        ps.setLong(2, value.getPerfume());
        ps.setInt(3, value.getQuantidade());
        ps.setDouble(4, value.getPrecoVenda());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setPedido(rs.getLong(1));
    }

    @Override
    public void alterar(DetalheVenda value) throws Exception {
        String sql = "UPDATE detalhe_venda set pedido = ?, perfume = ?, quantidade = ?, preco_venda where pedido = ?" +
                " and perfume = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getPedido());
        ps.setLong(2, value.getPerfume());
        ps.setInt(3, value.getQuantidade());
        ps.setDouble(4, value.getPrecoVenda());
        ps.setLong(5, value.getPedido());
        ps.setLong(6, value.getPerfume());

        ps.executeUpdate();
    }

    @Override
    public void excluir(DetalheVenda value) throws Exception {
        String sql = "DELETE from detalhe_venda where pedido = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getPedido());
        ps.executeUpdate();
    }
}
