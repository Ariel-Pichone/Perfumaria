package br.edu.femass.dao;

import br.edu.femass.model.Perfume;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PerfumeDao extends DaoPostgres implements Dao<Perfume>{

    @Override
    public List<Perfume> listar() throws Exception {
        String sql = "select * from perfume order by nome";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ResultSet rs = ps.executeQuery();

        List<Perfume> perfumes = new ArrayList<Perfume>();
        while (rs.next()) {
            Perfume perfume = new Perfume();
            perfume.setNome(rs.getString("nome"));
            perfume.setMl(rs.getInt("ml"));
            perfume.setEstoque(rs.getInt("estoque"));
            perfume.setPrecoVenda(rs.getDouble("preco_venda"));
            perfume.setId(rs.getLong("id"));
            perfumes.add(perfume);
        }
        return perfumes;
    }
    @Override
    public void gravar(Perfume value) throws Exception {
        String sql = "INSERT INTO perfume (nome, ml, preco_venda) VALUES (?,?,?)";
        PreparedStatement ps = getPreparedStatement(sql, true);
        ps.setString(1, value.getNome());
        ps.setInt(2, value.getMl());
        ps.setDouble(3, value.getPrecoVenda());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        value.setId(rs.getLong(1));
    }

    @Override
    public void alterar(Perfume value) throws Exception {
        String sql = "UPDATE perfume set nome = ?, ml = ?, preco_venda = ? where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setString(1, value.getNome());
        ps.setInt(2, value.getMl());
        ps.setDouble(3, value.getPrecoVenda());
        ps.setLong(4, value.getId());
        ps.executeUpdate();
    }

    @Override
    public void excluir(Perfume value) throws Exception {
        String sql = "DELETE from perfume where id = ?";
        PreparedStatement ps = getPreparedStatement(sql, false);
        ps.setLong(1, value.getId());
        ps.executeUpdate();
    }
}
