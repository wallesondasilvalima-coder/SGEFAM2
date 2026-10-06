
package sgefam2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReceitaPanel extends JPanel {

    private MainFrame main;

    private JTextField tfDescricao;
    private JTextField tfValor;
    private JTextField tfData;

    private JComboBox<Usuario> cbUsuario;

    private ReceitaDAO dao;
    private UsuarioDAO usuarioDAO;

    public ReceitaPanel(MainFrame main) {

        this.main = main;

        this.dao = new ReceitaDAO();
        this.usuarioDAO = new UsuarioDAO();

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(6, 6, 6, 6);

        // =========================
        // TÍTULO
        // =========================

        c.gridx = 0;
        c.gridy = 0;

        add(
                new JLabel("Cadastro de Receita"),
                c
        );

        // =========================
        // DESCRIÇÃO
        // =========================

        c.gridy++;

        add(
                new JLabel("Descrição:"),
                c
        );

        tfDescricao = new JTextField(20);

        c.gridy++;

        add(
                tfDescricao,
                c
        );

        // =========================
        // VALOR
        // =========================

        c.gridy++;

        add(
                new JLabel("Valor (ex: 1200.50):"),
                c
        );

        tfValor = new JTextField(10);

        c.gridy++;

        add(
                tfValor,
                c
        );

        // =========================
        // DATA
        // =========================

        c.gridy++;

        add(
                new JLabel("Data (YYYY-MM-DD):"),
                c
        );

        tfData = new JTextField(10);

        c.gridy++;

        add(
                tfData,
                c
        );

        // =========================
        // USUÁRIO
        // =========================

        c.gridy++;

        add(
                new JLabel("Usuário:"),
                c
        );

        cbUsuario = new JComboBox<>();

        c.gridy++;

        add(
                cbUsuario,
                c
        );

        // =========================
        // BOTÃO
        // =========================

        JButton bSalvar = new JButton("Salvar");

        bSalvar.addActionListener(
                e -> salvar()
        );

        c.gridy++;

        add(
                bSalvar,
                c
        );
    }

    // =====================================================
    // ATUALIZA A LISTA DE USUÁRIOS DIRETAMENTE DO MYSQL
    // =====================================================

    public void refresh() {

        cbUsuario.removeAllItems();

        List<Usuario> usuarios =
                usuarioDAO.listar();

        for (Usuario usuario : usuarios) {

            cbUsuario.addItem(usuario);
        }
    }

    // =====================================================
    // SALVAR RECEITA
    // =====================================================

    private void salvar() {

        try {

            String descricao =
                    tfDescricao.getText().trim();

            String valorTexto =
                    tfValor.getText().trim();

            String data =
                    tfData.getText().trim();

            Usuario usuario =
                    (Usuario) cbUsuario.getSelectedItem();

            // =========================
            // VALIDAÇÃO
            // =========================

            if (descricao.isEmpty()
                    || valorTexto.isEmpty()
                    || data.isEmpty()
                    || usuario == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Preencha todos os dados."
                );

                return;
            }

            // =========================
            // VALOR
            // =========================

            double valor =
                    Double.parseDouble(valorTexto);

            if (valor <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "O valor deve ser maior que zero."
                );

                return;
            }

            // =========================
            // DATA
            // =========================

            if (!data.matches("\\d{4}-\\d{2}-\\d{2}")) {

                JOptionPane.showMessageDialog(
                        this,
                        "A data deve estar no formato YYYY-MM-DD.\n"
                        + "Exemplo: 2026-10-05"
                );

                return;
            }

            // =========================
            // OBJETO RECEITA
            // =========================

            Receita receita = new Receita(
                    0,
                    valor,
                    data,
                    descricao,
                    usuario
            );

            // =========================
            // BANCO DE DADOS
            // =========================

            dao.inserir(receita);

            JOptionPane.showMessageDialog(
                    this,
                    "Receita adicionada com sucesso!"
            );

            // =========================
            // LIMPAR CAMPOS
            // =========================

            tfDescricao.setText("");
            tfValor.setText("");
            tfData.setText("");

            // Atualiza Dashboard
            main.dashboardPanel.refresh();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Valor inválido.\n"
                    + "Exemplo: 1200.50",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}