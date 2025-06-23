package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ClienteView extends JFrame {
    public ClienteView() {
        setTitle("Cadastro de Clientes");
        setSize(400, 400);
        setLayout(new GridLayout(10, 2));
        setLocationRelativeTo(null);

        // Campos de entrada
        JTextField txtId = new JTextField();
        JTextField txtNome = new JTextField();
        String[] tipos = {"Nacional", "Estrangeiro"};
        JComboBox<String> comboTipoCliente = new JComboBox<>(tipos);
        JTextField txtDocumento = new JTextField(); // campo único para CPF ou Passaporte
        JTextField txtTelefone = new JTextField();
        JTextField txtEmail = new JTextField();

        // Botões
        JButton btnSalvar = new JButton("Salvar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnExcluir = new JButton("Excluir por ID");

        // Adicionando os componentes ao layout
        add(new JLabel("ID (para busca/exclusão):")); add(txtId);
        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("Tipo (Nacional/Estrangeiro):")); add(comboTipoCliente);
        add(new JLabel("CPF ou Passaporte:")); add(txtDocumento);
        add(new JLabel("Telefone:")); add(txtTelefone);
        add(new JLabel("Email:")); add(txtEmail);
        add(new JLabel()); add(btnSalvar);
        add(new JLabel()); add(btnBuscar);
        add(new JLabel()); add(btnExcluir);

        // Ação do botão Salvar com validações
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String documento = txtDocumento.getText();
            String tipoCliente = (String) comboTipoCliente.getSelectedItem();

            if (!camposObrigatoriosPreenchidos(nome, email)) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }

            if (!isEmailValido(email)) {
                JOptionPane.showMessageDialog(this, "E-mail inválido.");
                return;
            }

            if ("Nacional".equalsIgnoreCase(tipoCliente) && !isCpfValido(documento)) {
                JOptionPane.showMessageDialog(this, "CPF inválido. Deve conter 11 dígitos numéricos.");
                return;
            }

            if ("Estrangeiro".equalsIgnoreCase(tipoCliente) && !isPassaporteValido(documento)) {
                JOptionPane.showMessageDialog(this, "Passaporte inválido. Deve conter de 6 a 9 caracteres alfanuméricos.");
                return;
            }

            Cliente c = new Cliente();
            c.setNome(nome);
            c.setEmail(email);
            c.setTipo(tipoCliente);
            c.setTelefone(txtTelefone.getText());

            if ("Nacional".equalsIgnoreCase(tipoCliente)) {
                c.setCpf(documento);
                c.setPassaporte("");
            } else {
                c.setCpf("");
                c.setPassaporte(documento);
            }

            try {
                new ClienteDAO().adicionar(c);
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar cliente: " + ex.getMessage());
            }
        });

        // Ação do botão Buscar
        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ClienteDAO dao = new ClienteDAO();
                Cliente c = dao.buscarPorId(id);
                if (c != null) {
                    txtNome.setText(c.getNome());
                    comboTipoCliente.setSelectedItem(c.getTipo());
                    txtDocumento.setText("Nacional".equalsIgnoreCase(c.getTipo()) ? c.getCpf() : c.getPassaporte());
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

        // Ação do botão Excluir
        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ClienteDAO dao = new ClienteDAO();
                dao.excluir(id);
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                txtNome.setText("");
                txtDocumento.setText("");
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

    // Validações auxiliares
    private boolean isCpfValido(String cpf) {
        return cpf.matches("\\d{11}");
    }

    private boolean isPassaporteValido(String passaporte) {
        return passaporte.matches("[A-Za-z0-9]{6,9}");
    }

    private boolean isEmailValido(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }


    private boolean camposObrigatoriosPreenchidos(String nome, String email) {
        return !nome.trim().isEmpty() && !email.trim().isEmpty();
    }
}
