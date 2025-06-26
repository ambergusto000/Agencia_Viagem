package view;

import dao.ContratacaoServicoDAO;
import dao.PacoteDAO;
import model.Cliente;
import model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

public class TelaConsultaClientesPorPacote extends JFrame {

    private JComboBox<PacoteViagem> comboPacotes;
    private JTextArea areaResultado;

    public TelaConsultaClientesPorPacote() throws SQLException {
        setTitle("Consulta de Clientes por Pacote");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lbl = new JLabel("Selecione o pacote:");
        comboPacotes = new JComboBox<>();
        JButton btnBuscar = new JButton("Buscar Clientes");
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setLineWrap(true);
        areaResultado.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(areaResultado);

        carregarPacotes();

        // Layout organizado
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        add(lbl, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        add(comboPacotes, gbc);

        gbc.gridx = 2; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        add(btnBuscar, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 10, 10); gbc.fill = GridBagConstraints.BOTH; gbc.weighty = 1.0;
        add(scroll, gbc);

        btnBuscar.addActionListener((ActionEvent e) -> {
            PacoteViagem pacote = (PacoteViagem) comboPacotes.getSelectedItem();
            if (pacote == null) return;

            try {
                ContratacaoServicoDAO dao = new ContratacaoServicoDAO();
                List<Cliente> clientes = dao.listarClientesPorPacote(pacote.getId());

                StringBuilder sb = new StringBuilder();
                for (Cliente c : clientes) {
                    sb.append("👤 Nome: ").append(c.getNome())
                            .append(" | 📧 Email: ").append(c.getEmail()).append("\n");
                }

                areaResultado.setText(sb.length() > 0 ? sb.toString() : "Nenhum cliente contratou este pacote.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    private void carregarPacotes() throws SQLException {
        PacoteDAO dao = new PacoteDAO();
        List<PacoteViagem> pacotes = dao.listar();
        for (PacoteViagem p : pacotes) {
            comboPacotes.addItem(p);
        }
    }
}
