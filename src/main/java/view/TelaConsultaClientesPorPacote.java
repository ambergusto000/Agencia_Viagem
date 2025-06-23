package view;

import dao.ContratacaoServicoDAO;
import dao.PacoteDAO;
import model.Cliente;
import model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaConsultaClientesPorPacote extends JFrame {
    private JComboBox<PacoteViagem> comboPacotes;
    private JTextArea areaResultado;

    public TelaConsultaClientesPorPacote() {
        setTitle("Consulta de Clientes por Pacote");
        setSize(400, 300);
        setLayout(new BorderLayout());

        comboPacotes = new JComboBox<>();
        carregarPacotes();

        JButton btnBuscar = new JButton("Buscar Clientes");
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Selecione o pacote:"), BorderLayout.NORTH);
        topPanel.add(comboPacotes, BorderLayout.CENTER);
        topPanel.add(btnBuscar, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            try {
                PacoteViagem pacote = (PacoteViagem) comboPacotes.getSelectedItem();
                if (pacote == null) return;

                ContratacaoServicoDAO dao = new ContratacaoServicoDAO();
                List<Cliente> clientes = dao.listarClientesPorPacote(pacote.getId());

                StringBuilder sb = new StringBuilder();
                for (Cliente c : clientes) {
                    sb.append("Nome: ").append(c.getNome())
                            .append(" | Email: ").append(c.getEmail());

                    List<String> servicos = dao.listarServicosPorClienteEPacote(c.getId(), pacote.getId());
                    if (!servicos.isEmpty()) {
                        sb.append(" | Serviços: ").append(String.join(", ", servicos));
                    }

                    sb.append("\n");
                }

                areaResultado.setText(sb.length() > 0 ? sb.toString() : "Nenhum cliente contratou este pacote.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar clientes: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    private void carregarPacotes() {
        PacoteDAO dao = new PacoteDAO();
        try {
            List<PacoteViagem> pacotes = dao.listarTodos();
            for (PacoteViagem p : pacotes) comboPacotes.addItem(p);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pacotes: " + e.getMessage());
        }
    }
}
