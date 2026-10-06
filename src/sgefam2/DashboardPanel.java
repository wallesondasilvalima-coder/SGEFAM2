
package sgefam2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private MainFrame main;

    private JLabel lblReceitas;
    private JLabel lblDespesas;
    private JLabel lblSaldo;

    private DefaultTableModel modelMov;

    private DashboardDAO dao;

    public DashboardPanel(MainFrame main) {

        this.main = main;
        this.dao = new DashboardDAO();

        setLayout(new BorderLayout(15, 15));

        // =========================
        // CARDS
        // =========================

        JPanel top = new JPanel(
                new GridLayout(1, 3, 15, 15)
        );

        lblReceitas = new JLabel("Receitas: R$ 0,00");
        lblDespesas = new JLabel("Despesas: R$ 0,00");
        lblSaldo = new JLabel("Saldo: R$ 0,00");

        lblReceitas.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        lblDespesas.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        lblSaldo.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        top.add(createCard("Receitas", lblReceitas));
        top.add(createCard("Despesas", lblDespesas));
        top.add(createCard("Saldo", lblSaldo));

        add(top, BorderLayout.NORTH);

        // =========================
        // ÚLTIMAS MOVIMENTAÇÕES
        // =========================

        JPanel painelMovimentacoes = new JPanel(
                new BorderLayout(5, 5)
        );

        JLabel titulo = new JLabel(
                "Últimas Movimentações"
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        titulo.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 5, 5, 5
                )
        );

        painelMovimentacoes.add(
                titulo,
                BorderLayout.NORTH
        );

        modelMov = new DefaultTableModel(
                new String[]{
                    "Data",
                    "Tipo",
                    "Descrição",
                    "Valor",
                    "Usuário"
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

        JTable table = new JTable(modelMov);

        table.setRowHeight(28);

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        table.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        painelMovimentacoes.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        add(
                painelMovimentacoes,
                BorderLayout.CENTER
        );
    }

    private JPanel createCard(
            String titulo,
            JLabel label
    ) {

        JPanel painel = new JPanel(
                new BorderLayout()
        );

        painel.setBorder(
                BorderFactory.createTitledBorder(
                        titulo
                )
        );

        label.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 5, 15, 5
                )
        );

        painel.add(
                label,
                BorderLayout.CENTER
        );

        return painel;
    }

    public void refresh() {

        // =========================
        // VALORES
        // =========================

        double totalReceitas =
                dao.totalReceitas();

        double totalDespesas =
                dao.totalDespesas();

        double saldo =
                totalReceitas - totalDespesas;

        lblReceitas.setText(
                String.format(
                        java.util.Locale.forLanguageTag("pt-BR"),
                        "R$ %,.2f",
                        totalReceitas
                )
        );

        lblDespesas.setText(
                String.format(
                        java.util.Locale.forLanguageTag("pt-BR"),
                        "R$ %,.2f",
                        totalDespesas
                )
        );

        lblSaldo.setText(
                String.format(
                        java.util.Locale.forLanguageTag("pt-BR"),
                        "R$ %,.2f",
                        saldo
                )
        );

        // =========================
        // TABELA
        // =========================

        modelMov.setRowCount(0);

        for (Object[] movimento
                : dao.listarUltimasMovimentacoes()) {

            modelMov.addRow(movimento);
        }
    }
}