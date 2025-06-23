package view;

import dao.ClienteDAO;
import dao.ContratacaoServicoDAO;
import model.Cliente;
import model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TelaConsultaPacotesPorCliente extends JFrame {
    private JComboBox<Cliente> comboClientes;
    private JTextArea areaResultado;

    public TelaConsultaPacotesPorCliente() {
        setTitle("Consulta de Pacotes por Cliente");
        setSize(400, 300);
        setLayout(new BorderLayout());

        comboClientes = new JComboBox<>();
        carregarClientes();

        JButton btnBuscar = new JButton("Buscar Pacotes");
        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Selecione o cliente:"), BorderLayout.NORTH);
        topPanel.add(comboClientes, BorderLayout.CENTER);
        topPanel.add(btnBuscar, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> {
            try {
                Cliente cliente = (Cliente) comboClientes.getSelectedItem();
                if (cliente == null) return;

                ContratacaoServicoDAO dao = new ContratacaoServicoDAO();
                List<PacoteViagem> pacotes = dao.listarPacotesPorCliente(cliente.getId());

                StringBuilder sb = new StringBuilder();
                for (PacoteViagem p : pacotes) {
                    sb.append("Destino: ").append(p.getDestino())
                            .append(" | Preço: R$").append(p.getPreco());

                    List<String> servicos = dao.listarServicosPorClienteEPacote(cliente.getId(), p.getId());
                    if (!servicos.isEmpty()) {
                        sb.append(" | Serviços: ").append(String.join(", ", servicos));
                    }

                    sb.append("\n");
                }

                areaResultado.setText(sb.length() > 0 ? sb.toString() : "Nenhum pacote contratado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar pacotes: " + ex.getMessage());
            }
        });

        setVisible(true);
    }

    private void carregarClientes() {
        ClienteDAO dao = new ClienteDAO();
        try {
            List<Cliente> clientes = dao.listar();
            for (Cliente c : clientes) comboClientes.addItem(c);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
        }
    }
}
