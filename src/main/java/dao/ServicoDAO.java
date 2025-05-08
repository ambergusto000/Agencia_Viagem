package dao;

import model.ServicoAdicional;
import util.DB;
import java.sql.*;
import java.util.*;

public class ServicoDAO {
    public void adicionar(ServicoAdicional s) throws SQLException {
        String sql = "INSERT INTO servicos (nome, descricao, preco) VALUES (?, ?, ?)";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getNome());
            stmt.setString(2, s.getDescricao());
            stmt.setDouble(3, s.getPreco());
            stmt.executeUpdate();
        }
    }

    public List<ServicoAdicional> listar() throws SQLException {
        List<ServicoAdicional> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conn = DB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ServicoAdicional s = new ServicoAdicional();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescricao(rs.getString("descricao"));
                s.setPreco(rs.getDouble("preco"));
                lista.add(s);
            }
        }
        return lista;
    }

    public List<ServicoAdicional> listarTodos() throws SQLException {
        List<ServicoAdicional> lista = new ArrayList<>();
        String sql = "SELECT * FROM servicos";
        try (Connection conn = DB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ServicoAdicional s = new ServicoAdicional();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescricao(rs.getString("descricao"));
                s.setPreco(rs.getDouble("preco"));
                lista.add(s);
            }
        }
        return lista;
    }

    // Nova função: Busca por ID
    public ServicoAdicional buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM servicos WHERE id = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ServicoAdicional s = new ServicoAdicional();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setDescricao(rs.getString("descricao"));
                s.setPreco(rs.getDouble("preco"));
                return s;
            }
            return null;
        }
    }

    //Exclusão por ID
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM servicos WHERE id = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}