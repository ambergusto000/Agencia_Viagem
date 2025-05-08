package view;

import dao.ClienteDAO;
import dao.PacoteDAO;
import dao.ServicoDAO;
import dao.ContratacaoServicoDAO;
import model.Cliente;
import model.PacoteViagem;
import model.ServicoAdicional;
import model.ContratacaoServico;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement; // Import adicionado
import java.sql.ResultSet;       // Import adicionado
import java.sql.SQLException;    // Import adicionado
import java.time.LocalDate;
import java.util.List;

public class TelaContratarServico extends JFrame {
    private JComboBox<Cliente> cbCliente;
    private JComboBox<PacoteViagem> cbPacote;
    private JComboBox<ServicoAdicional> cbServico;
    private JButton btnContratar;

    public TelaContratarServico(Connection conn) {
        super("Contratar Serviço");

        cbCliente = new JComboBox<>();
        cbPacote = new JComboBox<>();
        cbServico = new JComboBox<>();
        btnContratar = new JButton("Contratar");

        // Carrega os dados dos DAOs
        ClienteDAO clienteDAO = new ClienteDAO();
        PacoteDAO pacoteDAO = new PacoteDAO();
        ServicoDAO servicoDAO = new ServicoDAO();

        try {
            List<Cliente> clientes = clienteDAO.listarTodos();
            List<PacoteViagem> pacotes = pacoteDAO.listarTodos();
            List<ServicoAdicional> servicos = servicoDAO.listarTodos();

            for (Cliente c : clientes) cbCliente.addItem(c);
            for (PacoteViagem p : pacotes) cbPacote.addItem(p);
            for (ServicoAdicional s : servicos) cbServico.addItem(s);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + ex.getMessage());
        }

        btnContratar.addActionListener(e -> {
            Cliente cliente = (Cliente) cbCliente.getSelectedItem();
            PacoteViagem pacote = (PacoteViagem) cbPacote.getSelectedItem();
            ServicoAdicional servico = (ServicoAdicional) cbServico.getSelectedItem();

            if (cliente != null && pacote != null && servico != null) {
                // Criar um contrato primeiro (simplificado)
                try {
                    int contratoId = criarContrato(cliente.getId(), pacote.getId(), conn);
                    ContratacaoServico contratacao = new ContratacaoServico();
                    contratacao.setContratoId(contratoId); // Agora existe
                    contratacao.setServicoId(servico.getId());
                    contratacao.setDataContratacao(LocalDate.now());

                    ContratacaoServicoDAO dao = new ContratacaoServicoDAO(conn);
                    dao.contratarServico(contratacao);
                    JOptionPane.showMessageDialog(this, "Serviço contratado com sucesso!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao contratar serviço: " + ex.getMessage());
                    ex.printStackTrace();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage());
                }
            }
        });

        setLayout(new GridLayout(4, 2, 10, 10));
        add(new JLabel("Cliente:")); add(cbCliente);
        add(new JLabel("Pacote:")); add(cbPacote);
        add(new JLabel("Serviço:")); add(cbServico);
        add(new JLabel("")); add(btnContratar);

        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private int criarContrato(int clienteId, int pacoteId, Connection conn) throws SQLException {
        String sql = "INSERT INTO contratos (cliente_id, pacote_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, clienteId);
            stmt.setInt(2, pacoteId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Falha ao criar contrato");
        }
    }
}