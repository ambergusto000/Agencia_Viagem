package view;

import dao.ClienteDAO;
import dao.PacoteDAO;
import dao.ServicoDAO;
import model.Cliente;
import model.PacoteViagem;
import model.ServicoAdicional;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class TelaVisualizacaoDados extends JFrame {
    public TelaVisualizacaoDados() {
        setTitle("Dados Cadastrados");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Clientes com pacotes
        DefaultTableModel modelClientes = new DefaultTableModel(
                new String[]{"ID", "Nome", "Tipo", "Telefone", "Email", "Pacotes"}, 0);
        JTable tabelaClientes = new JTable(modelClientes);
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
        tabbedPane.addTab("Clientes", new JScrollPane(tabelaClientes));

        // Pacotes
        DefaultTableModel modelPacotes = new DefaultTableModel(
                new String[]{"ID", "Nome", "Destino", "Duração", "Preço", "Tipo"}, 0);
        JTable tabelaPacotes = new JTable(modelPacotes);
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
        tabbedPane.addTab("Pacotes", new JScrollPane(tabelaPacotes));

        // Serviços
        DefaultTableModel modelServicos = new DefaultTableModel(
                new String[]{"ID", "Nome", "Descrição", "Preço"}, 0);
        JTable tabelaServicos = new JTable(modelServicos);
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
        tabbedPane.addTab("Serviços", new JScrollPane(tabelaServicos));

        add(tabbedPane);
        setVisible(true);
    }
}
