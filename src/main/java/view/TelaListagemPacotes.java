package view;

import dao.PacoteDAO;
import model.PacoteViagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemPacotes extends JFrame {
    public TelaListagemPacotes() {
        setTitle("Listagem de Pacotes");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] colunas = {"ID", "Nome", "Destino", "Duração", "Preço", "Tipo"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);

        try {
            List<PacoteViagem> pacotes = new PacoteDAO().listarTodos();
            for (PacoteViagem p : pacotes) {
                model.addRow(new Object[]{
                        p.getId(),
                        p.getNome(),
                        p.getDestino(),
                        p.getDuracao(),
                        p.getPreco(),
                        p.getTipo()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar pacotes: " + e.getMessage());
        }

        add(new JScrollPane(tabela));
        setVisible(true);
    }
}
