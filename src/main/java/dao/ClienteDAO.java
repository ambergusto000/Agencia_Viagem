package dao;

import model.Cliente;
import util.DB;

import java.sql.*;
import java.util.*;

public class ClienteDAO {

    // Inserir novo cliente
    public void adicionar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nome, tipo, cpf, passaporte, telefone, email) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTipo());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getPassaporte());
            stmt.setString(5, cliente.getTelefone());
            stmt.setString(6, cliente.getEmail());
            stmt.executeUpdate();
        }
    }

    // Buscar cliente por ID
    public Cliente buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTipo(rs.getString("tipo"));
                c.setCpf(rs.getString("cpf"));
                c.setPassaporte(rs.getString("passaporte"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                return c;
            }
        }
        return null;
    }

    // Excluir cliente por ID
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Listar todos os clientes
    public List<Cliente> listar() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
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

    // Listar clientes com pacotes contratados
    public Map<Cliente, List<String>> listarClientesComPacotes() {
        Map<Cliente, List<String>> resultado = new HashMap<>();
        String sql = "SELECT cl.id, cl.nome, cl.tipo, cl.telefone, cl.email, p.destino " +
                "FROM clientes cl " +
                "JOIN contratacao_servico cs ON cl.id = cs.id_cliente " +
                "JOIN pacote_viagem p ON p.id = cs.id_pacote";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String tipo = rs.getString("tipo");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String destinoPacote = rs.getString("destino");

                Cliente clienteTemp = new Cliente(nome, email, "", tipo);
                clienteTemp.setId(id);
                clienteTemp.setTelefone(telefone);

                Cliente existente = null;
                for (Cliente c : resultado.keySet()) {
                    if (c.getId() == id) {
                        existente = c;
                        break;
                    }
                }

                if (existente != null) {
                    resultado.get(existente).add(destinoPacote);
                } else {
                    List<String> pacotes = new ArrayList<>();
                    pacotes.add(destinoPacote);
                    resultado.put(clienteTemp, pacotes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }
}
