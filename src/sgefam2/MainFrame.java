package sgefam2;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public Usuario usuarioLogado = null;

    private CardLayout cardLayout = new CardLayout();
    private JPanel contentPanel = new JPanel(cardLayout);

    // Panels
    public LoginPanel loginPanel;
    public DashboardPanel dashboardPanel;
    public CategoriaPanel categoriaPanel;
    public ReceitaPanel receitaPanel;
    public DespesaPanel despesaPanel;
    public ConsultaPanel consultaPanel;

    public MainFrame() {

        setTitle(
                "SGEFAM - Sistema de Gestão Financeira Familiar"
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setSize(1000, 650);

        setLocationRelativeTo(null);

        // =========================
        // CRIAR PANELS
        // =========================

        loginPanel = new LoginPanel(this);

        dashboardPanel = new DashboardPanel(this);

        categoriaPanel = new CategoriaPanel(this);

        receitaPanel = new ReceitaPanel(this);

        despesaPanel = new DespesaPanel(this);

        consultaPanel = new ConsultaPanel(this);

        // =========================
        // ADICIONAR CARDS
        // =========================

        contentPanel.add(
                loginPanel,
                "login"
        );

        contentPanel.add(
                dashboardPanel,
                "dashboard"
        );

        contentPanel.add(
                categoriaPanel,
                "categorias"
        );

        contentPanel.add(
                receitaPanel,
                "receitas"
        );

        contentPanel.add(
                despesaPanel,
                "despesas"
        );

        contentPanel.add(
                consultaPanel,
                "consulta"
        );

        // =========================
        // LAYOUT PRINCIPAL
        // =========================

        getContentPane().setLayout(
                new BorderLayout()
        );

        getContentPane().add(
                createSideMenu(),
                BorderLayout.WEST
        );

        getContentPane().add(
                contentPanel,
                BorderLayout.CENTER
        );

        showCard("login");
    }

    // =====================================================
    // MENU LATERAL
    // =====================================================

    private JPanel createSideMenu() {

        JPanel side = new JPanel();

        side.setPreferredSize(
                new Dimension(220, 0)
        );

        side.setLayout(
                new BorderLayout()
        );

        side.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JLabel logo = new JLabel(
                "<html>"
                + "<h2>SGEFAM</h2>"
                + "<small>Sistema Financeiro</small>"
                + "</html>"
        );

        side.add(
                logo,
                BorderLayout.NORTH
        );

        JPanel buttons = new JPanel();

        buttons.setLayout(
                new GridLayout(8, 1, 5, 5)
        );

        JButton bHome =
                new JButton("Início");

        JButton bReceita =
                new JButton("Receitas");

        JButton bDespesa =
                new JButton("Despesas");

        JButton bCategorias =
                new JButton("Categorias");

        JButton bConsulta =
                new JButton("Consulta");

        JButton bSair =
                new JButton("Sair / Logout");

        JButton bLogin =
                new JButton("Login");

        // =========================
        // INÍCIO
        // =========================

        bHome.addActionListener(e -> {

            if (usuarioLogado != null) {

                showCard("dashboard");

                dashboardPanel.refresh();

            } else {

                showCard("login");
            }
        });

        // =========================
        // RECEITAS
        // =========================

        bReceita.addActionListener(e -> {

            if (ensureLogin()) {

                showCard("receitas");

                receitaPanel.refresh();
            }
        });

        // =========================
        // DESPESAS
        // =========================

        bDespesa.addActionListener(e -> {

            if (ensureLogin()) {

                showCard("despesas");

                despesaPanel.refresh();
            }
        });

        // =========================
        // CATEGORIAS
        // =========================

        bCategorias.addActionListener(e -> {

            if (ensureLogin()) {

                showCard("categorias");

                categoriaPanel.refreshTable();
            }
        });

        // =========================
        // CONSULTA
        // =========================

        bConsulta.addActionListener(e -> {

            if (ensureLogin()) {

                showCard("consulta");

                consultaPanel.refreshTable();
            }
        });

        // =========================
        // LOGOUT
        // =========================

        bSair.addActionListener(e -> {

            usuarioLogado = null;

            loginPanel.clearFields();

            showCard("login");
        });

        // =========================
        // LOGIN
        // =========================

        bLogin.addActionListener(e -> {

            showCard("login");
        });

        // =========================
        // ADICIONAR BOTÕES
        // =========================

        buttons.add(bHome);
        buttons.add(bReceita);
        buttons.add(bDespesa);
        buttons.add(bCategorias);
        buttons.add(bConsulta);
        buttons.add(bLogin);
        buttons.add(bSair);

        side.add(
                buttons,
                BorderLayout.CENTER
        );

        return side;
    }

    // =====================================================
    // TROCAR TELA
    // =====================================================

    public void showCard(String name) {

        cardLayout.show(
                contentPanel,
                name
        );
    }

    // =====================================================
    // VERIFICAR LOGIN
    // =====================================================

    private boolean ensureLogin() {

        if (usuarioLogado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Faça login primeiro.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );

            showCard("login");

            return false;
        }

        return true;
    }
}
