package view;
import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ClienteView extends JFrame {
    public ClienteView() {
        setTitle("Cadastro de Clientes");
        setSize(400, 300);
        setLayout(new GridLayout(8, 2));
        setLocationRelativeTo(null);

        JTextField txtNome = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtPassaporte = new JTextField();
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("Tipo (nacional/estrangeiro):")); add(txtTipo);
        add(new JLabel("CPF:")); add(txtCpf);
        add(new JLabel("Passaporte:")); add(txtPassaporte);
        add(new JLabel("Telefone:")); add(txtTelefone);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel()); add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            Cliente c = new Cliente();
            c.setNome(txtNome.getText());
            c.setTipo(txtTipo.getText());
            c.setCpf(txtCpf.getText());
            c.setPassaporte(txtPassaporte.getText());
            c.setTelefone(txtTelefone.getText());
            c.setEmail(txtEmail.getText());
            try {
                new ClienteDAO().adicionar(c);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar cliente.");
            }
        });

        setVisible(true);
    }
}
