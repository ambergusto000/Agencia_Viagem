package view;

import dao.PacoteDAO;
import model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PacoteView extends JFrame {
    private JTextField txtId, txtNome, txtDestino, txtDuracao, txtPreco, txtTipo;
    private JButton btnSalvar, btnBuscar, btnExcluir;

    public PacoteView() {
        setTitle("Cadastro de Pacotes");
        setSize(400, 400);
        setLayout(new GridLayout(8, 2));
        setLocationRelativeTo(null);

        txtId = new JTextField();
        txtNome = new JTextField();
        txtDestino = new JTextField();
        txtDuracao = new JTextField();
        txtPreco = new JTextField();
        txtTipo = new JTextField();

        btnSalvar = new JButton("Salvar");
        btnBuscar = new JButton("Buscar por ID");
        btnExcluir = new JButton("Excluir por ID");

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
            String nome = txtNome.getText();
            String destino = txtDestino.getText();
            String duracaoStr = txtDuracao.getText();
            String precoStr = txtPreco.getText();
            String tipo = txtTipo.getText();

            if (nome.isEmpty() || destino.isEmpty() || duracaoStr.isEmpty() || precoStr.isEmpty() || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.");
                return;
            }

            try {
                int duracao = Integer.parseInt(duracaoStr);
                double preco = Double.parseDouble(precoStr);

                PacoteViagem p = new PacoteViagem();
                p.setNome(nome);
                p.setDestino(destino);
                p.setDuracao(duracao);
                p.setPreco(preco);
                p.setTipo(tipo);

                new PacoteDAO().adicionar(p);
                JOptionPane.showMessageDialog(this, "Pacote salvo com sucesso!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duração deve ser número inteiro e preço deve ser decimal.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar pacote: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                PacoteViagem p = new PacoteDAO().buscarPorId(id);
                if (p != null) {
                    txtNome.setText(p.getNome());
                    txtDestino.setText(p.getDestino());
                    txtDuracao.setText(String.valueOf(p.getDuracao()));
                    txtPreco.setText(String.valueOf(p.getPreco()));
                    txtTipo.setText(p.getTipo());
                    JOptionPane.showMessageDialog(this, "Pacote encontrado.");
                } else {
                    JOptionPane.showMessageDialog(this, "Pacote não encontrado.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao buscar pacote: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                new PacoteDAO().excluir(id);
                JOptionPane.showMessageDialog(this, "Pacote excluído com sucesso!");

                txtNome.setText("");
                txtDestino.setText("");
                txtDuracao.setText("");
                txtPreco.setText("");
                txtTipo.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir pacote: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}
