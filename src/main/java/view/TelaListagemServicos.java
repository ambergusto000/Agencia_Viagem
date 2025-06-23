package view;

import dao.ServicoDAO;
import model.ServicoAdicional;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagemServicos extends JFrame {
    public TelaListagemServicos() {
        setTitle("Listagem de Serviços");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] colunas = {"ID", "Nome", "Descrição", "Preço"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);

        try {
            List<ServicoAdicional> servicos = new ServicoDAO().listar();
            for (ServicoAdicional s : servicos) {
                model.addRow(new Object[]{
                        s.getId(),
                        s.getNome(),
                        s.getDescricao(),
                        s.getPreco()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar serviços: " + e.getMessage());
        }

        add(new JScrollPane(tabela));
        setVisible(true);
    }
}
