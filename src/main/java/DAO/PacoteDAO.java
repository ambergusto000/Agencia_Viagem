package DAO;

import model.PacoteViagem;
import util.DB;
import java.sql.*;
import java.util.*;

public class PacoteDAO {
    public void adicionar(PacoteViagem p) throws SQLException {
        String sql = "INSERT INTO pacotes (nome, destino, duracao, preco, tipo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDestino());
            stmt.setInt(3, p.getDuracao());
            stmt.setDouble(4, p.getPreco());
            stmt.setString(5, p.getTipo());
            stmt.executeUpdate();
        }
    }

    public List<PacoteViagem> listar() throws SQLException {
        List<PacoteViagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacotes";
        try (Connection conn = DB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PacoteViagem p = new PacoteViagem();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDestino(rs.getString("destino"));
                p.setDuracao(rs.getInt("duracao"));
                p.setPreco(rs.getDouble("preco"));
                p.setTipo(rs.getString("tipo"));
                lista.add(p);
            }
        }
        return lista;
    }

    public List<PacoteViagem> listarTodos() throws SQLException {
        List<PacoteViagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacotes";
        try (Connection conn = DB.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                PacoteViagem p = new PacoteViagem();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDestino(rs.getString("destino"));
                p.setDuracao(rs.getInt("duracao"));
                p.setPreco(rs.getDouble("preco"));
                p.setTipo(rs.getString("tipo"));
                lista.add(p);
            }
        }
        return lista;
    }
}
