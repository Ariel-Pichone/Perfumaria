package br.edu.femass.dao;

import br.edu.femass.model.Saldo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SaldoVendaDao extends DaoPostgres implements Dao<Saldo>{

    @Override
    public List<Saldo> listar() throws Exception {
        String sql = "select\n" +
                "    '0' as pedido,\n" +
                "    SUM (dv.quantidade*dv.preco_venda) as total\n" +
                "from venda c inner join detalhe_venda dv on c.nr_pedido = dv.pedido\n" +
                "where c.data_venda = current_date\n" +
                "\n" +
                "UNION ALL\n" +
                "\n" +
                "select\n" +
                "    c.nr_pedido as pedido,\n" +
                "    SUM (dv.quantidade*dv.preco_venda) as total\n" +
                "from venda c inner join detalhe_venda dv on c.nr_pedido = dv.pedido\n" +
                "where c.data_venda = current_date\n" +
                "and nr_pedido = 0\n" +
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
