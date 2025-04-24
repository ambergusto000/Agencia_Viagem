package dao;

import model.Cliente;
import util.DB;

import java.sql.*;
import java.util.*;

public class ClienteDAO {

    public void adicionar(Cliente c) throws SQLException {
        String sql = "INSERT INTO clientes (nome, tipo, cpf, passaporte, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getTipo());

            // CPF e Passaporte podem ser nulos
            if (c.getCpf() == null || c.getCpf().isBlank()) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, c.getCpf());
            }

            if (c.getPassaporte() == null || c.getPassaporte().isBlank()) {
                stmt.setNull(4, Types.VARCHAR);
            } else {
                stmt.setString(4, c.getPassaporte());
            }

            stmt.setString(5, c.getTelefone());
            stmt.setString(6, c.getEmail());

            stmt.executeUpdate();
        }
    }

    public Map<Cliente, List<String>> listarClientesComPacotes() throws SQLException {
        String sql = "SELECT c.id, c.nome, c.tipo, c.cpf, c.passaporte, c.telefone, c.email, p.nome AS pacote " +
                "FROM clientes c " +
                "LEFT JOIN contratos ct ON c.id = ct.cliente_id " +
                "LEFT JOIN pacotes p ON ct.pacote_id = p.id";

        Map<Integer, Cliente> clientesMap = new HashMap<>();
        Map<Cliente, List<String>> resultado = new HashMap<>();

        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                Cliente cliente = clientesMap.get(id);
                if (cliente == null) {
                    cliente = new Cliente();
                    cliente.setId(id);
                    cliente.setNome(rs.getString("nome"));
                    cliente.setTipo(rs.getString("tipo"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setPassaporte(rs.getString("passaporte"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setEmail(rs.getString("email"));
                    clientesMap.put(id, cliente);
                    resultado.put(cliente, new ArrayList<>());
                }
                String pacote = rs.getString("pacote");
                if (pacote != null) {
                    resultado.get(cliente).add(pacote);
                }
            }
        }
        return resultado;
    }

    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTipo(rs.getString("tipo"));
                c.setCpf(rs.getString("cpf"));
                c.setPassaporte(rs.getString("passaporte"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }
        }

        return lista;
    }
}
