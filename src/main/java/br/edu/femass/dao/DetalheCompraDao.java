package br.edu.femass.dao;

import br.edu.femass.model.DetalheCompra;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetalheCompraDao extends DaoPostgres implements Dao<DetalheCompra>{

    @Override
    public List<DetalheCompra> listar() throws Exception {
        String sql = "select * from detalhe_compra order by pedido";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<DetalheCompra> detalheCompras = new ArrayList<DetalheCompra>();
        while (rs.next()) {
            DetalheCompra detalheCompra = new DetalheCompra();
            detalheCompra.setPedido(rs.getLong("pedido"));
            detalheCompra.setPerfume(rs.getLong("perfume"));
            detalheCompra.setQuantidade(rs.getInt("quantidade"));
            detalheCompra.setPrecoCompra(rs.getDouble("preco_compra"));
            detalheCompras.add(detalheCompra);
        }
        return detalheCompras;
    }
    @Override
    public void gravar(DetalheCompra value) throws Exception {
        String sql = "INSERT INTO detalhe_compra (pedido, perfume, quantidade, preco_compra) VALUES (?,?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setLong(1, value.getPedido());
        ps.setLong(2, value.getPerfume());
        ps.setInt(3, value.getQuantidade());
        ps.setDouble(4, value.getPrecoCompra());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setPedido(rs.getLong(1));
    }

    @Override
    public void alterar(DetalheCompra value) throws Exception {
        String sql = "UPDATE detalhe_compra set pedido = ?, perfume = ?, quantidade = ?, preco_compra where pedido = ?" +
                " and perfume = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getPedido());
        ps.setLong(2, value.getPerfume());
        ps.setInt(3, value.getQuantidade());
        ps.setDouble(4, value.getPrecoCompra());
        ps.setLong(5, value.getPedido());
        ps.setLong(6, value.getPerfume());

        ps.executeUpdate();
    }

    @Override
    public void excluir(DetalheCompra value) throws Exception {
        String sql = "DELETE from detalhe_compra where pedido = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getPedido());
        ps.executeUpdate();
    }
}
