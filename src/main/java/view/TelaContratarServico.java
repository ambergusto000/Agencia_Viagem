package view;

import dao.ClienteDAO;
import dao.ContratacaoServicoDAO;
import dao.PacoteDAO;
import dao.ServicoDAO;
import model.Cliente;
import model.ContratacaoServico;
import model.PacoteViagem;
import model.ServicoAdicional;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaContratarServico extends JFrame {
    private JComboBox<Cliente> cbCliente;
    private JComboBox<PacoteViagem> cbPacote;
    private JComboBox<ServicoAdicional> cbServico;
    private JButton btnContratar;

    public TelaContratarServico() {
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
            List<Cliente> clientes = clienteDAO.listar();
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
                try {
                    ContratacaoServicoDAO dao = new ContratacaoServicoDAO();
                    dao.adicionarServicoAdicional(cliente.getId(), pacote.getId(), servico.getId());
                    JOptionPane.showMessageDialog(this, "Serviço contratado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao contratar serviço: " + ex.getMessage());
                    ex.printStackTrace();
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
}
