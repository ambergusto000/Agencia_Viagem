package view;

import dao.ServicoDAO;
import model.ServicoAdicional;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ServicoView extends JFrame {
    public ServicoView() {
        setTitle("Cadastro de Serviços");
        setSize(400, 300);
        setLayout(new GridLayout(6, 2));
        setLocationRelativeTo(null);

        JTextField txtId = new JTextField(); // Campo para busca/exclusão
        JTextField txtNome = new JTextField();
        JTextField txtDescricao = new JTextField();
        JTextField txtPreco = new JTextField();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnExcluir = new JButton("Excluir por ID");

        add(new JLabel("ID (para busca/exclusão):")); add(txtId);
        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("Descrição:")); add(txtDescricao);
        add(new JLabel("Preço:")); add(txtPreco);
        add(new JLabel()); add(btnSalvar);
        add(new JLabel()); add(btnBuscar);
        add(new JLabel()); add(btnExcluir);

        btnSalvar.addActionListener(e -> {
            ServicoAdicional s = new ServicoAdicional();
            s.setNome(txtNome.getText());
            s.setDescricao(txtDescricao.getText());
            s.setPreco(Double.parseDouble(txtPreco.getText()));
            try {
                new ServicoDAO().adicionar(s);
                JOptionPane.showMessageDialog(this, "Serviço cadastrado!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar serviço.");
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ServicoDAO dao = new ServicoDAO();
                ServicoAdicional s = dao.buscarPorId(id);
                if (s != null) {
                    txtNome.setText(s.getNome());
                    txtDescricao.setText(s.getDescricao());
                    txtPreco.setText(String.valueOf(s.getPreco()));
                    JOptionPane.showMessageDialog(this, "Serviço encontrado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Serviço não encontrado.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar serviço: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido.");
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ServicoDAO dao = new ServicoDAO();
                dao.excluir(id);
                JOptionPane.showMessageDialog(this, "Serviço excluído com sucesso!");
                txtNome.setText("");
                txtDescricao.setText("");
                txtPreco.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir serviço: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido.");
            }
        });

        setVisible(true);
    }
}