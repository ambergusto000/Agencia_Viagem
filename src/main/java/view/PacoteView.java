package view;
import dao.PacoteDAO;
import model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class PacoteView extends JFrame {
    public PacoteView() {
        setTitle("Cadastro de Pacotes");
        setSize(400, 300);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(null);

        JTextField txtNome = new JTextField();
        JTextField txtDestino = new JTextField();
        JTextField txtDuracao = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtTipo = new JTextField();
        JButton btnSalvar = new JButton("Salvar");

        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("Destino:")); add(txtDestino);
        add(new JLabel("Duração (dias):")); add(txtDuracao);
        add(new JLabel("Preço:")); add(txtPreco);
        add(new JLabel("Tipo:")); add(txtTipo);
        add(new JLabel()); add(btnSalvar);

        btnSalvar.addActionListener(e -> {
            PacoteViagem p = new PacoteViagem();
            p.setNome(txtNome.getText());
            p.setDestino(txtDestino.getText());
            p.setDuracao(Integer.parseInt(txtDuracao.getText()));
            p.setPreco(Double.parseDouble(txtPreco.getText()));
            p.setTipo(txtTipo.getText());
            try {
                new PacoteDAO().adicionar(p);
                JOptionPane.showMessageDialog(this, "Pacote cadastrado!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar pacote.");
            }
        });

        setVisible(true);
    }
}
