package view;

import dao.ServicoDAO;
import model.ServicoAdicional;

import javax.swing.*;
import java.awt.*;

public class ServicoView extends JFrame {
    public ServicoView() {
        setTitle("Cadastro de Serviços");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Painel principal
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
        JTextField txtDescricao = new JTextField(20);
        JTextField txtPreco = new JTextField(10);

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
        form.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        form.add(txtDescricao, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        form.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        form.add(txtPreco, gbc);

        painel.add(form, BorderLayout.CENTER);

        // Botões
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
                ServicoAdicional s = new ServicoAdicional();
                s.setNome(txtNome.getText());
                s.setDescricao(txtDescricao.getText());
                s.setPreco(Double.parseDouble(txtPreco.getText()));

                new ServicoDAO().adicionar(s);
                JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar serviço: " + ex.getMessage());
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ServicoAdicional s = new ServicoDAO().buscarPorId(id);
                if (s != null) {
                    txtNome.setText(s.getNome());
                    txtDescricao.setText(s.getDescricao());
                    txtPreco.setText(String.valueOf(s.getPreco()));
                    JOptionPane.showMessageDialog(this, "Serviço encontrado!");
                } else {
                    JOptionPane.showMessageDialog(this, "Serviço não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnExcluir.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                new ServicoDAO().excluir(id);
                JOptionPane.showMessageDialog(this, "Serviço excluído com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
            }
        });
    }
}
