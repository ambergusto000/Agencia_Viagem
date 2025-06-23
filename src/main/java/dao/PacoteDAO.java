package dao;

import model.PacoteViagem;
import util.DB;
import javax.swing.JOptionPane;
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

    //Busca por ID
    public PacoteViagem buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM pacotes WHERE id = ?";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PacoteViagem p = new PacoteViagem();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDestino(rs.getString("destino"));
                p.setDuracao(rs.getInt("duracao"));
                p.setPreco(rs.getDouble("preco"));
                p.setTipo(rs.getString("tipo"));
                return p;
            }
            return null;
        }
    }

    public boolean podeExcluirPacote(int idPacote) {
        try (Connection conn = DB.getConnection()) {
            String sql = "SELECT COUNT(*) FROM contratacao_servico WHERE id_pacote = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPacote);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void excluir(int id) {
        if (!podeExcluirPacote(id)) {
            JOptionPane.showMessageDialog(null, "Este pacote está associado a clientes e não pode ser excluído.");
            return;
        }

        try (Connection conn = DB.getConnection()) {
            String sql = "DELETE FROM pacote_viagem WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pacote excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir pacote.");
        }
    }

}