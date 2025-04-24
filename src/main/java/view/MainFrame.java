package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Agência de Viagens");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnClientes = new JButton("Clientes");
        JButton btnPacotes = new JButton("Pacotes");
        JButton btnServiços = new JButton("Serviços");
        JButton btnListar = new JButton("Listar");

        JPanel panel = new JPanel();
        panel.add(btnClientes);
        panel.add(btnPacotes);
        panel.add(btnServiços);
        panel.add(btnListar);
        add(panel);

        btnClientes.addActionListener(e -> new ClienteView());
        btnPacotes.addActionListener(e -> new PacoteView());
        btnServiços.addActionListener(e -> new ServicoView());
        btnListar.addActionListener(e -> new TelaVisualizacaoDados()); // <- esse é o botão que abre a tela de visualização

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
