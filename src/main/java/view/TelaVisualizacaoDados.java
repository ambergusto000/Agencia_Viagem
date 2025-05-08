package view;

import dao.ClienteDAO;
import dao.PacoteDAO;
import dao.ServicoDAO;
import model.Cliente;
import model.PacoteViagem;
import model.ServicoAdicional;
import util.DB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TelaVisualizacaoDados extends JFrame {
    private DefaultTableModel modelClientes;
    private DefaultTableModel modelPacotes;
    private DefaultTableModel modelServicos;
    private JTable tabelaClientes;
    private JTable tabelaPacotes;
    private JTable tabelaServicos;

    public TelaVisualizacaoDados() {
        setTitle("Dados Cadastrados");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Clientes com pacotes e serviços
        modelClientes = new DefaultTableModel(
                new String[]{"ID", "Nome", "Tipo", "Telefone", "Email", "Pacotes e Serviços"}, 0);
        tabelaClientes = new JTable(modelClientes);
        JButton btnExcluirCliente = new JButton("Excluir Cliente Selecionado");
        btnExcluirCliente.addActionListener(e -> excluirClienteSelecionado());
        JPanel painelClientes = new JPanel(new BorderLayout());
        painelClientes.add(new JScrollPane(tabelaClientes), BorderLayout.CENTER);
        painelClientes.add(btnExcluirCliente, BorderLayout.SOUTH);
        carregarClientes();
        tabbedPane.addTab("Clientes", painelClientes);

        // Pacotes
        modelPacotes = new DefaultTableModel(
                new String[]{"ID", "Nome", "Destino", "Duração", "Preço", "Tipo"}, 0);
        tabelaPacotes = new JTable(modelPacotes);
        JButton btnExcluirPacote = new JButton("Excluir Pacote Selecionado");
        btnExcluirPacote.addActionListener(e -> excluirPacoteSelecionado());
        JPanel painelPacotes = new JPanel(new BorderLayout());
        painelPacotes.add(new JScrollPane(tabelaPacotes), BorderLayout.CENTER);
        painelPacotes.add(btnExcluirPacote, BorderLayout.SOUTH);
        carregarPacotes();
        tabbedPane.addTab("Pacotes", painelPacotes);

        // Serviços
        modelServicos = new DefaultTableModel(
                new String[]{"ID", "Nome", "Descrição", "Preço"}, 0);
        tabelaServicos = new JTable(modelServicos);
        JButton btnExcluirServico = new JButton("Excluir Serviço Selecionado");
        btnExcluirServico.addActionListener(e -> excluirServicoSelecionado());
        JPanel painelServicos = new JPanel(new BorderLayout());
        painelServicos.add(new JScrollPane(tabelaServicos), BorderLayout.CENTER);
        painelServicos.add(btnExcluirServico, BorderLayout.SOUTH);
        carregarServicos();
        tabbedPane.addTab("Serviços", painelServicos);

        // Adicionando botão para contratar serviços
        JButton btnContratarServico = new JButton("Contratar Serviço");
        btnContratarServico.addActionListener(e -> {
            try {
                Connection conn = DB.getConnection();
                new TelaContratarServico(conn);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao abrir tela de contratação: " + ex.getMessage());
            }
        });

        // Painel para os botões globais
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnContratarServico);

        // Adiciona o painel de botões ao layout
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.NORTH);

        setVisible(true);
    }

    private void carregarClientes() {
        modelClientes.setRowCount(0); // Limpa a tabela
        try (Connection conn = DB.getConnection()) {
            ClienteDAO daoC = new ClienteDAO();
            Map<Cliente, List<String>> dados = daoC.listarClientesComPacotes();
            Map<Integer, Set<String>> servicosPorCliente = getServicosPorCliente(conn); // Usando Set para evitar duplicatas

            for (Map.Entry<Cliente, List<String>> entry : dados.entrySet()) {
                Cliente c = entry.getKey();
                List<String> pacotes = entry.getValue();
                Set<String> servicos = servicosPorCliente.getOrDefault(c.getId(), new HashSet<>());

                // Convertendo Set para List para usar String.join
                List<String> servicosList = new ArrayList<>(servicos);
                String pacotesStr = String.join(", ", pacotes);
                String servicosStr = String.join(", ", servicosList);
                String pacotesEServicos = (pacotesStr.isEmpty() ? "" : pacotesStr + (servicosStr.isEmpty() ? "" : ", ")) + servicosStr;

                modelClientes.addRow(new Object[]{
                        c.getId(), c.getNome(), c.getTipo(), c.getTelefone(), c.getEmail(), pacotesEServicos
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
    }

    private Map<Integer, Set<String>> getServicosPorCliente(Connection conn) throws SQLException {
        Map<Integer, Set<String>> servicosPorCliente = new HashMap<>();
        String sql = "SELECT DISTINCT c.id AS cliente_id, s.nome AS servico " +
                "FROM clientes c " +
                "JOIN contratos co ON c.id = co.cliente_id " +
                "JOIN contrato_servico cs ON co.id = cs.contrato_id " +
                "JOIN servicos s ON cs.servico_id = s.id";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                String servico = rs.getString("servico");
                servicosPorCliente.computeIfAbsent(clienteId, k -> new HashSet<>()).add(servico);
            }
        }
        return servicosPorCliente;
    }

    private void carregarPacotes() {
        modelPacotes.setRowCount(0); // Limpa a tabela
        try {
            PacoteDAO daoP = new PacoteDAO();
            List<PacoteViagem> pacotes = daoP.listarTodos();
            for (PacoteViagem p : pacotes) {
                modelPacotes.addRow(new Object[]{
                        p.getId(), p.getNome(), p.getDestino(), p.getDuracao(), p.getPreco(), p.getTipo()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pacotes: " + ex.getMessage());
        }
    }

    private void carregarServicos() {
        modelServicos.setRowCount(0); // Limpa a tabela
        try {
            ServicoDAO daoS = new ServicoDAO();
            List<ServicoAdicional> servicos = daoS.listarTodos();
            for (ServicoAdicional s : servicos) {
                modelServicos.addRow(new Object[]{
                        s.getId(), s.getNome(), s.getDescricao(), s.getPreco()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar serviços: " + ex.getMessage());
        }
    }

    private void excluirClienteSelecionado() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int id = (int) tabelaClientes.getValueAt(linhaSelecionada, 0);
            try {
                new ClienteDAO().excluir(id);
                carregarClientes(); // Recarrega a tabela
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
        }
    }

    private void excluirPacoteSelecionado() {
        int linhaSelecionada = tabelaPacotes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int id = (int) tabelaPacotes.getValueAt(linhaSelecionada, 0);
            try {
                new PacoteDAO().excluir(id);
                carregarPacotes(); // Recarrega a tabela
                JOptionPane.showMessageDialog(this, "Pacote excluído com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir pacote: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um pacote para excluir.");
        }
    }

    private void excluirServicoSelecionado() {
        int linhaSelecionada = tabelaServicos.getSelectedRow();
        if (linhaSelecionada >= 0) {
            int id = (int) tabelaServicos.getValueAt(linhaSelecionada, 0);
            try {
                new ServicoDAO().excluir(id);
                carregarServicos(); // Recarrega a tabela
                JOptionPane.showMessageDialog(this, "Serviço excluído com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir serviço: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um serviço para excluir.");
        }
    }
}