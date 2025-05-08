package view;

import dao.ClienteDAO;
import dao.PacoteDAO;
import dao.ServicoDAO;
import model.Cliente;
import model.PacoteViagem;
import model.ServicoAdicional;
import util.DB; // Import para obter a conexão

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

        // Clientes com pacotes
        modelClientes = new DefaultTableModel(
                new String[]{"ID", "Nome", "Tipo", "Telefone", "Email", "Pacotes"}, 0);
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
                Connection conn = DB.getConnection(); // Obtém a conexão
                new TelaContratarServico(conn); // Abre a tela de contratar serviço
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
        try {
            ClienteDAO daoC = new ClienteDAO();
            Map<Cliente, List<String>> dados = daoC.listarClientesComPacotes();
            for (Map.Entry<Cliente, List<String>> e : dados.entrySet()) {
                Cliente c = e.getKey();
                String pacotes = String.join(", ", e.getValue());
                modelClientes.addRow(new Object[]{
                        c.getId(), c.getNome(), c.getTipo(), c.getTelefone(), c.getEmail(), pacotes
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + ex.getMessage());
        }
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