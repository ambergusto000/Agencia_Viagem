package view;

import dao.ClienteDAO;
import dao.ContratacaoServicoDAO;
import dao.PacoteDAO;
import dao.ServicoDAO;
import model.Cliente;
import model.PacoteViagem;
import model.ServicoAdicional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class TelaAdicionarServicoAdicional extends JFrame {
    private JComboBox<Cliente> comboClientes;
    private JComboBox<PacoteViagem> comboPacotes;
    private JComboBox<ServicoAdicional> comboServicos;

    public TelaAdicionarServicoAdicional() {
        setTitle("Adicionar Serviço Adicional");
        setSize(400, 250);
        setLayout(new GridLayout(5, 1));

        comboClientes = new JComboBox<>();
        comboPacotes = new JComboBox<>();
        comboServicos = new JComboBox<>();

        try {
            carregarClientes();
            carregarPacotes();
            carregarServicos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
        }

        add(new JLabel("Cliente:"));
        add(comboClientes);

        add(new JLabel("Pacote:"));
        add(comboPacotes);

        add(new JLabel("Serviço Adicional:"));
        add(comboServicos);

        JButton btnAdicionar = new JButton("Adicionar Serviço");
        btnAdicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Cliente cliente = (Cliente) comboClientes.getSelectedItem();
                    PacoteViagem pacote = (PacoteViagem) comboPacotes.getSelectedItem();
                    ServicoAdicional servico = (ServicoAdicional) comboServicos.getSelectedItem();

                    ContratacaoServicoDAO dao = new ContratacaoServicoDAO();
                    dao.adicionarServicoAdicional(cliente.getId(), pacote.getId(), servico.getId());

                    JOptionPane.showMessageDialog(null, "Serviço adicional adicionado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao adicionar serviço: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        add(btnAdicionar);
        setVisible(true);
    }

    private void carregarClientes() throws SQLException {
        ClienteDAO dao = new ClienteDAO();
        for (Cliente c : dao.listar()) {
            comboClientes.addItem(c);
        }
    }

    private void carregarPacotes() throws SQLException {
        PacoteDAO dao = new PacoteDAO();
        for (PacoteViagem p : dao.listar()) {
            comboPacotes.addItem(p);
        }
    }

    private void carregarServicos() throws SQLException {
        ServicoDAO dao = new ServicoDAO();
        for (ServicoAdicional s : dao.listar()) {
            comboServicos.addItem(s);
        }
    }
}
