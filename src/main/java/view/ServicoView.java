package view;
import dao.ServicoDAO;
import model.ServicoAdicional;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ServicoView extends JFrame {
    public ServicoView() {
        setTitle("Cadastro de Serviços");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);

        JTextField txtNome = new JTextField();
        JTextField txtDescricao = new JTextField();
        JTextField txtPreco = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("Descrição:")); add(txtDescricao);
        add(new JLabel("Preço:")); add(txtPreco);
        add(new JLabel()); add(btnSalvar);

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

        setVisible(true);
    }
}
