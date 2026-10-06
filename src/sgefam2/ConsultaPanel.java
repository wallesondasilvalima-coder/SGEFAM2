
package sgefam2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ConsultaPanel extends JPanel {

    private MainFrame main;

    private DefaultTableModel model;
    private JTable table;

    private JTextField tfPer;

    private ConsultaDAO dao;

    public ConsultaPanel(MainFrame main) {

        this.main = main;
        this.dao = new ConsultaDAO();

        setLayout(new BorderLayout(10, 10));

        // =========================
        // PARTE SUPERIOR
        // =========================

        JPanel top = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        top.add(
                new JLabel("Período (YYYY-MM ou ALL):")
        );

        tfPer = new JTextField(10);

        top.add(tfPer);

        JButton bBuscar = new JButton("Buscar");

        top.add(bBuscar);

        add(top, BorderLayout.NORTH);

        // =========================
        // TABELA
        // =========================

        model = new DefaultTableModel(
                new String[]{
                    "Data",
                    "Tipo",
                    "Descrição",
                    "Valor",
                    "Usuário",
                    "Categoria"
                },
                0
        ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        table = new JTable(model);

        table.setRowHeight(28);

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        // =========================
        // BOTÃO BUSCAR
        // =========================

        bBuscar.addActionListener(e -> {

            String periodo =
                    tfPer.getText().trim();

            if (!periodo.isEmpty()
                    && !periodo.equalsIgnoreCase("ALL")
                    && !periodo.matches("\\d{4}-\\d{2}")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Informe o período no formato YYYY-MM.\n"
                        + "Exemplo: 2026-10\n\n"
                        + "Ou digite ALL para mostrar tudo.",
                        "Período inválido",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            refreshTable(periodo);
        });
    }

    public void refreshTable() {
        refreshTable("");
    }

    private void refreshTable(String periodo) {

        model.setRowCount(0);

        for (Object[] movimento
                : dao.consultar(periodo)) {

            model.addRow(movimento);
        }
    }
}