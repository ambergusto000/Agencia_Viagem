package view;

import dao.ClienteDAO;
import dao.ContratacaoServicoDAO;
import model.Cliente;
import model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TelaConsultaPacotesPorCliente extends JFrame {

    private JComboBox<Cliente> comboClientes;
    private JTextArea areaResultado;

    public TelaConsultaPacotesPorCliente() throws SQLException {
        setTitle("Consulta de Pacotes por Cliente");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lbl = new JLabel("Selecione o cliente:");
        comboClientes = new JComboBox<>();
        JButton btnBuscar = new JButton("Buscar Pacotes");
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(areaResultado);

        carregarClientes();

        // Layout organizado
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(lbl, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(comboClientes, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        add(btnBuscar, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 10, 10); gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        add(scroll, gbc);

        btnBuscar.addActionListener((ActionEvent e) -> {
            Cliente cliente = (Cliente) comboClientes.getSelectedItem();
            if (cliente == null) return;

            try {
                ContratacaoServicoDAO dao = new ContratacaoServicoDAO();
                List<PacoteViagem> pacotes = dao.listarPacotesPorCliente(cliente.getId());
                StringBuilder sb = new StringBuilder();

                for (PacoteViagem p : pacotes) {
                    List<String> servicos = dao.listarServicosPorContrato(cliente.getId(), p.getId());
                    sb.append("🗺 Destino: ").append(p.getDestino())
                            .append(" | 💰 Preço: R$").append(p.getPreco())
                            .append("\n   Serviços: ").append(String.join(", ", servicos)).append("\n\n");
                }

                areaResultado.setText(sb.length() > 0 ? sb.toString() : "Este cliente não contratou nenhum pacote.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    private void carregarClientes() throws SQLException {
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> clientes = dao.listar();
        for (Cliente c : clientes) {
            comboClientes.addItem(c);
        }
    }
}
