package dao;

import model.Cliente;
import model.PacoteViagem;
import model.ContratacaoServico;
import util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratacaoServicoDAO {

    // Contrata um serviço adicional para um cliente e pacote
    public void adicionarServicoAdicional(int idCliente, int idPacote, int idServico) {
        try (Connection conn = DB.getConnection()) {
            // Primeiro, cria (ou busca) um contrato
            int contratoId = obterOuCriarContrato(conn, idCliente, idPacote);

            // Depois, adiciona o serviço ao contrato
            String sql = "INSERT INTO contrato_servico (contrato_id, servico_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, contratoId);
                stmt.setInt(2, idServico);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Garante que o contrato exista, ou cria um novo
    private int obterOuCriarContrato(Connection conn, int idCliente, int idPacote) throws SQLException {
        String select = "SELECT id FROM contratos WHERE cliente_id = ? AND pacote_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idPacote);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }

        String insert = "INSERT INTO contratos (cliente_id, pacote_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idPacote);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Falha ao criar contrato.");
    }

    // Lista os pacotes que um cliente contratou
    public List<PacoteViagem> listarPacotesPorCliente(int idCliente) throws SQLException {
        List<PacoteViagem> pacotes = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.destino, p.duracao, p.preco, p.tipo " +
                "FROM contratos co " +
                "JOIN pacotes p ON co.pacote_id = p.id " +
                "WHERE co.cliente_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PacoteViagem pacote = new PacoteViagem();
                pacote.setId(rs.getInt("id"));
                pacote.setNome(rs.getString("nome"));
                pacote.setDestino(rs.getString("destino"));
                pacote.setDuracao(rs.getInt("duracao"));
                pacote.setPreco(rs.getDouble("preco"));
                pacote.setTipo(rs.getString("tipo"));
                pacotes.add(pacote);
            }
        }
        return pacotes;
    }

    // Lista os clientes que contrataram um pacote
    public List<Cliente> listarClientesPorPacote(int idPacote) throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.id, c.nome, c.tipo, c.cpf, c.passaporte, c.telefone, c.email " +
                "FROM contratos co " +
                "JOIN clientes c ON co.cliente_id = c.id " +
                "WHERE co.pacote_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPacote);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTipo(rs.getString("tipo"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setPassaporte(rs.getString("passaporte"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }
    // Lista os serviços contratados por um cliente em um pacote específico
    public List<String> listarServicosPorClienteEPacote(int idCliente, int idPacote) throws SQLException {
        List<String> servicos = new ArrayList<>();
        String sql = "SELECT s.nome " +
                "FROM contrato_servico cs " +
                "JOIN servicos s ON cs.servico_id = s.id " +
                "JOIN contratos co ON cs.contrato_id = co.id " +
                "WHERE co.cliente_id = ? AND co.pacote_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idPacote);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                servicos.add(rs.getString("nome"));
            }
        }
        return servicos;
    }

}
