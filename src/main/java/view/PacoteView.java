package view;

import dao.PacoteDAO;
import model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class PacoteView extends JFrame {
    public PacoteView() {
        setTitle("Cadastro de Pacotes");
        setSize(400, 350);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(null);

        JTextField txtId = new JTextField(); // Campo para busca/exclusão
        JTextField txtNome = new JTextField();
        JTextField txtDestino = new JTextField();
        JTextField txtDuracao = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtTipo = new JTextField();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnExcluir = new JButton("Excluir por ID");

        add(new JLabel("ID (para busca/exclusão):")); add(txtId);
        add(new JLabel("Nome:")); add(txtNome);
        add(new JLabel("Destino:")); add(txtDestino);
        add(new JLabel("Duração (dias):")); add(txtDuracao);
        add(new JLabel("Preço:")); add(txtPreco);
        add(new JLabel("Tipo:")); add(txtTipo);
        add(new JLabel()); add(btnSalvar);
        add(new JLabel()); add(btnBuscar);
        add(new JLabel()); add(btnExcluir);

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

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                PacoteDAO dao = new PacoteDAO();
                PacoteViagem p = dao.buscarPorId(id);
                if (p != null) {
                    txtNome.setText(p.getNome());
                    txtDestino.setText(p.getDestino());
                    txtDuracao.setText(String.valueOf(p.getDuracao()));
                    txtPreco.setText(String.valueOf(p.getPreco()));
                    txtTipo.setText(p.getTipo());
                    JOptionPane.showMessageDialog(this, "Pacote encontrado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Pacote não encontrado.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar pacote: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido.");
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                PacoteDAO dao = new PacoteDAO();
                dao.excluir(id);
                JOptionPane.showMessageDialog(this, "Pacote excluído com sucesso!");
                txtNome.setText("");
                txtDestino.setText("");
                txtDuracao.setText("");
                txtPreco.setText("");
                txtTipo.setText("");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir pacote: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido.");
            }
        });

        setVisible(true);
    }
}