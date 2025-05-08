package view;

import model.Cliente;
import model.ContratacaoServico;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class TelaContratarServico extends JFrame {
    private JComboBox<Cliente> cbCliente;
    private JComboBox<Pacote> cbPacote;
    private JComboBox<Servico> cbServico;
    private JButton btnContratar;

    public TelaContratarServico(Connection conn) {
        super("Contratar Serviço");

        cbCliente = new JComboBox<>();
        cbPacote = new JComboBox<>();
        cbServico = new JComboBox<>();
        btnContratar = new JButton("Contratar");

        // Carrega os dados dos DAOs
        ClienteDAO clienteDAO = new ClienteDAO(conn);
        PacoteDAO pacoteDAO = new PacoteDAO(conn);
        ServicoDAO servicoDAO = new ServicoDAO(conn);

        List<Cliente> clientes = clienteDAO.listarTodos();
        List<Pacote> pacotes = pacoteDAO.listarTodos();
        List<Servico> servicos = servicoDAO.listarTodos();

        for (Cliente c : clientes) cbCliente.addItem(c);
        for (Pacote p : pacotes) cbPacote.addItem(p);
        for (Servico s : servicos) cbServico.addItem(s);

        btnContratar.addActionListener((ActionEvent e) -> {
            Cliente cliente = (Cliente) cbCliente.getSelectedItem();
            Pacote pacote = (Pacote) cbPacote.getSelectedItem();
            Servico servico = (Servico) cbServico.getSelectedItem();

            if (cliente != null && pacote != null && servico != null) {
                ContratacaoServico contratacao = new ContratacaoServico();
                contratacao.setClienteId(cliente.getId());
                contratacao.setPacoteId(pacote.getId());
                contratacao.setServicoId(servico.getId());
                contratacao.setDataContratacao(LocalDate.now());

                ContratacaoServicoDAO dao = new ContratacaoServicoDAO(conn);
                try {
                    dao.contratarServico(contratacao);
                    JOptionPane.showMessageDialog(this, "Serviço contratado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao contratar serviço: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        setLayout(new GridLayout(4, 2, 10, 10));
        add(new JLabel("Cliente:")); add(cbCliente);
        add(new JLabel("Pacote:"));  add(cbPacote);
        add(new JLabel("Serviço:")); add(cbServico);
        add(new JLabel(""));         add(btnContratar);

        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
