
package sgefam2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategoriaPanel extends JPanel {

    private DefaultTableModel model;
    private JTable table;
    private JTextField tfNome;

    private CategoriaDAO dao;

    public CategoriaPanel(MainFrame main) {

        dao = new CategoriaDAO();

        setLayout(new BorderLayout(10, 10));

        // Parte superior
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        top.add(new JLabel("Nova categoria:"));

        tfNome = new JTextField(20);
        top.add(tfNome);

        JButton bAdd = new JButton("+ Adicionar");
        bAdd.addActionListener(e -> adicionar());
        top.add(bAdd);

        add(top, BorderLayout.NORTH);

        // Tabela
        model = new DefaultTableModel(
                new String[]{"ID", "Categoria"}, 0
        );

        table = new JTable(model);

        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        // Parte inferior
        JPanel bottom = new JPanel();

        JButton bEditar = new JButton("Editar");
        JButton bExcluir = new JButton("Excluir");

        bEditar.addActionListener(e -> editar());
        bExcluir.addActionListener(e -> excluir());

        bottom.add(bEditar);
        bottom.add(bExcluir);

        add(bottom, BorderLayout.SOUTH);

        // Carregar categorias do banco
        refreshTable();
    }

    // =====================================================
    // ATUALIZAR TABELA
    // =====================================================

    public void refreshTable() {

        model.setRowCount(0);

        for (Categoria c : dao.listar()) {

            model.addRow(new Object[]{
                c.getId(),
                c.getNome()
            });
        }
    }

    // =====================================================
    // ADICIONAR
    // =====================================================

    private void adicionar() {

        String nome = tfNome.getText().trim();

        if (nome.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe o nome da categoria."
            );

            return;
        }

        Categoria categoria = new Categoria(nome);

        dao.inserir(categoria);

        tfNome.setText("");

        refreshTable();
    }

    // =====================================================
    // EDITAR
    // =====================================================

    private void editar() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma categoria."
            );

            return;
        }

        int id = (int) model.getValueAt(row, 0);

        String nomeAtual =
                model.getValueAt(row, 1).toString();

        String novoNome = JOptionPane.showInputDialog(
                this,
                "Novo nome:",
                nomeAtual
        );

        if (novoNome == null) {
            return;
        }

        novoNome = novoNome.trim();

        if (novoNome.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Informe um nome válido."
            );

            return;
        }

        Categoria categoria =
                new Categoria(id, novoNome);

        dao.atualizar(categoria);

        refreshTable();
    }

    // =====================================================
    // EXCLUIR
    // =====================================================

    private void excluir() {

        int row = table.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione uma categoria."
            );

            return;
        }

        int id = (int) model.getValueAt(row, 0);

        String nome =
                model.getValueAt(row, 1).toString();

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja excluir a categoria \"" + nome + "\"?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        dao.excluir(id);

        refreshTable();
    }
}