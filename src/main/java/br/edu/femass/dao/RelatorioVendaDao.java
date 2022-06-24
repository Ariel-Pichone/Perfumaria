package br.edu.femass.dao;

import br.edu.femass.model.Venda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RelatorioVendaDao extends DaoPostgres implements Dao<Venda>{

    @Override
    public List<Venda> listar() throws Exception {
        String sql = "select * from venda where data_venda = current_date order by nr_pedido";
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
    public void gravar(Venda value) throws Exception {}

    @Override
    public void alterar(Venda value) throws Exception {}

    @Override
    public void excluir(Venda value) throws Exception {}
}
