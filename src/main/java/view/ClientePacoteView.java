package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ClientePacoteView extends JFrame {
    public ClientePacoteView() {
        setTitle("Clientes e Pacotes");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] colunas = {"ID", "Nome", "Tipo", "Telefone", "Email", "Pacotes"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);

        try {
            ClienteDAO dao = new ClienteDAO();
            Map<Cliente, List<String>> dados = dao.listarClientesComPacotes();
            for (Map.Entry<Cliente, List<String>> entry : dados.entrySet()) {
                Cliente c = entry.getKey();
                String pacotes = String.join(", ", entry.getValue());
                model.addRow(new Object[]{
                        c.getId(), c.getNome(), c.getTipo(), c.getTelefone(), c.getEmail(), pacotes
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        setVisible(true);
    }
}
