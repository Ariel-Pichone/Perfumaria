package br.edu.femass.dao;

import br.edu.femass.model.Saldo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SaldoCompraDao extends DaoPostgres implements Dao<Saldo>{

    @Override
    public List<Saldo> listar() throws Exception {
        String sql = "select\n" +
                "    '0' as pedido,\n" +
                "    SUM (dc.quantidade*dc.preco_compra) as total\n" +
                "from compra c inner join detalhe_compra dc on c.nr_pedido = dc.pedido\n" +
                "where c.data_compra = current_date\n" +
                "\n" +
                "UNION ALL\n" +
                "\n" +
                "select\n" +
                "    c.nr_pedido as pedido,\n" +
                "    SUM (dc.quantidade*dc.preco_compra) as total\n" +
                "from compra c inner join detalhe_compra dc on c.nr_pedido = dc.pedido\n" +
                "where c.data_compra = current_date\n" +
                "and c.nr_pedido = 0\n" +
                "group by c.nr_pedido";

        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Saldo> saldos = new ArrayList<Saldo>();
        while (rs.next()) {
            Saldo saldo = new Saldo();
            saldo.setPedido(rs.getLong("pedido"));
            saldo.setTotal(rs.getDouble("total"));
            saldos.add(saldo);
        }
        return saldos;
    }

    @Override
    public void gravar(Saldo value) throws Exception {}

    @Override
    public void alterar(Saldo value) throws Exception {}

    @Override
    public void excluir(Saldo value) throws Exception {}
}
