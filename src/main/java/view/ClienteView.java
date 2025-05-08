package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ClienteView extends JFrame {
    public ClienteView() {
        setTitle("Cadastro de Clientes");
        setSize(400, 350);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(null);

        JTextField txtId = new JTextField(); // Campo para busca/exclusão
        JTextField txtNome = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtCpf = new JTextField();
        JTextField txtPassaporte = new JTextField();
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnExcluir = new JButton("Excluir por ID");

        add(new JLabel("ID (para busca/exclusão):")); add(txtId);
        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("Tipo (nacional/estrangeiro):")); add(txtTipo);
        add(new JLabel("CPF:")); add(txtCpf);
        add(new JLabel("Passaporte:")); add(txtPassaporte);
        add(new JLabel("Telefone:")); add(txtTelefone);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel()); add(btnSalvar);
        add(new JLabel()); add(btnBuscar);
        add(new JLabel()); add(btnExcluir);

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

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ClienteDAO dao = new ClienteDAO();
                Cliente c = dao.buscarPorId(id);
                if (c != null) {
                    txtNome.setText(c.getNome());
                    txtTipo.setText(c.getTipo());
                    txtCpf.setText(c.getCpf());
                    txtPassaporte.setText(c.getPassaporte());
                    txtTelefone.setText(c.getTelefone());
                    txtEmail.setText(c.getEmail());
                    JOptionPane.showMessageDialog(this, "Cliente encontrado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente não encontrado.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar cliente: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido.");
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ClienteDAO dao = new ClienteDAO();
                dao.excluir(id);
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                txtNome.setText("");
                txtTipo.setText("");
                txtCpf.setText("");
                txtPassaporte.setText("");
                txtTelefone.setText("");
                txtEmail.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido.");
            }
        });

        setVisible(true);
    }
}