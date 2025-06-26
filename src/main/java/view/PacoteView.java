package view;

import dao.PacoteDAO;
import model.PacoteViagem;

import javax.swing.*;
import java.awt.*;

public class PacoteView extends JFrame {
    public PacoteView() {
        setTitle("Cadastro de Pacotes");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel principal com margem
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de formulário
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        JTextField txtId = new JTextField(10);
        JTextField txtNome = new JTextField(20);
        JTextField txtDestino = new JTextField(20);
        JTextField txtDuracao = new JTextField(10);
        JTextField txtPreco = new JTextField(10);
        JTextField txtTipo = new JTextField(15);

        // Adicionando os campos com labels
        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("ID (para busca/exclusão):"), gbc);
        gbc.gridx = 1;
        form.add(txtId, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        form.add(txtNome, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Destino:"), gbc);
        gbc.gridx = 1;
        form.add(txtDestino, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Duração (dias):"), gbc);
        gbc.gridx = 1;
        form.add(txtDuracao, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        form.add(txtPreco, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        form.add(txtTipo, gbc);

        painel.add(form, BorderLayout.CENTER);

        // Painel de botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnBuscar = new JButton("Buscar por ID");
        JButton btnExcluir = new JButton("Excluir por ID");

        botoes.add(btnSalvar);
        botoes.add(btnBuscar);
        botoes.add(btnExcluir);

        painel.add(botoes, BorderLayout.SOUTH);

        add(painel);
        setVisible(true);

        // Ações
        btnSalvar.addActionListener(e -> {
            try {
                PacoteViagem p = new PacoteViagem();
                p.setNome(txtNome.getText());
                p.setDestino(txtDestino.getText());
                p.setDuracao(Integer.parseInt(txtDuracao.getText()));
                p.setPreco(Double.parseDouble(txtPreco.getText()));
                p.setTipo(txtTipo.getText());

                new PacoteDAO().adicionar(p);
                JOptionPane.showMessageDialog(this, "Pacote cadastrado com sucesso!");
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
                    JOptionPane.showMessageDialog(this, "Pacote encontrado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Pacote não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                new PacoteDAO().excluir(id);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        });
    }
}
