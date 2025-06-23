package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemClientes extends JFrame {
    public TelaListagemClientes() {
        setTitle("Listagem de Clientes");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] colunas = {"ID", "Nome", "Tipo", "Documento", "Telefone", "E-mail"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);

        try {
            List<Cliente> clientes = new ClienteDAO().listar();
            for (Cliente c : clientes) {
                String doc = c.getTipo().equalsIgnoreCase("nacional") ? c.getCpf() : c.getPassaporte();
                model.addRow(new Object[]{
                        c.getId(),
                        c.getNome(),
                        c.getTipo(),
                        doc,
                        c.getTelefone(),
                        c.getEmail()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar clientes: " + e.getMessage());
        }

        add(new JScrollPane(tabela));
        setVisible(true);
    }
}
