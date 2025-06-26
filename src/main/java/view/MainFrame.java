package view;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Sistema de Agência de Viagens");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Painel principal com BoxLayout vertical
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // margem externa

        // Criação e adição dos botões
        JButton btnClientes = criarBotao("Gerenciar Clientes", e -> new ClienteView());
        JButton btnPacotes = criarBotao("Gerenciar Pacotes", e -> new PacoteView());
        JButton btnServicos = criarBotao("Gerenciar Serviços", e -> new ServicoView());

        JButton btnListarClientes = criarBotao("Listar Clientes", e -> new TelaListagemClientes());
        JButton btnListarPacotes = criarBotao("Listar Pacotes", e -> new TelaListagemPacotes());
        JButton btnListarServicos = criarBotao("Listar Serviços", e -> new TelaListagemServicos());

        JButton btnConsultaPorCliente = criarBotao("Pacotes por Cliente", e -> {
            try {
                new TelaConsultaPacotesPorCliente();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton btnConsultaPorPacote = criarBotao("Clientes por Pacote", e -> {
            try {
                new TelaConsultaClientesPorPacote();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton btnContratarServicos = criarBotao("Contratar Serviços Adicionais", e -> new TelaAdicionarServicoAdicional());

        // Adicionando os botões ao painel com espaço
        panel.add(btnClientes);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnPacotes);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnServicos);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnListarClientes);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnListarPacotes);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnListarServicos);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnConsultaPorCliente);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnConsultaPorPacote);
        panel.add(Box.createVerticalStrut(10));

        panel.add(btnContratarServicos);

        // Adicionando painel à janela
        add(panel);
        setVisible(true);
    }

    // Método utilitário para criar botões com alinhamento e ação
    private JButton criarBotao(String texto, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        botao.setFocusable(false);
        botao.addActionListener(acao);
        return botao;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
