package sgefam2;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DespesaPanel extends JPanel {

    private MainFrame main;

    private JTextField tfDescricao;
    private JTextField tfValor;
    private JTextField tfData;

    private JComboBox<Usuario> cbUsuario;
    private JComboBox<Categoria> cbCategoria;

    private DespesaDAO dao;
    private UsuarioDAO usuarioDAO;
    private CategoriaDAO categoriaDAO;

    public DespesaPanel(MainFrame main) {

        this.main = main;

        this.dao = new DespesaDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.categoriaDAO = new CategoriaDAO();

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(6, 6, 6, 6);

        // =========================
        // TÍTULO
        // =========================

        c.gridx = 0;
        c.gridy = 0;

        add(
                new JLabel("Cadastro de Despesa"),
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
                new JLabel("Valor (ex: 350.00):"),
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
        // CATEGORIA
        // =========================

        c.gridy++;

        add(
                new JLabel("Categoria:"),
                c
        );

        cbCategoria = new JComboBox<>();

        c.gridy++;

        add(
                cbCategoria,
                c
        );

        // =========================
        // BOTÃO SALVAR
        // =========================

        JButton bSalvar =
                new JButton("Salvar");

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
    // CARREGA USUÁRIOS E CATEGORIAS DO MYSQL
    // =====================================================

    public void refresh() {

        // =========================
        // USUÁRIOS
        // =========================

        cbUsuario.removeAllItems();

        List<Usuario> usuarios =
                usuarioDAO.listar();

        for (Usuario usuario : usuarios) {

            cbUsuario.addItem(usuario);
        }

        // =========================
        // CATEGORIAS
        // =========================

        cbCategoria.removeAllItems();

        List<Categoria> categorias =
                categoriaDAO.listar();

        for (Categoria categoria : categorias) {

            cbCategoria.addItem(categoria);
        }
    }

    // =====================================================
    // SALVAR DESPESA
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

            Categoria categoria =
                    (Categoria) cbCategoria.getSelectedItem();

            // =========================
            // VALIDAÇÃO
            // =========================

            if (descricao.isEmpty()
                    || valorTexto.isEmpty()
                    || data.isEmpty()
                    || usuario == null
                    || categoria == null) {

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
            // OBJETO DESPESA
            // =========================

            Despesa despesa = new Despesa(
                    0,
                    valor,
                    data,
                    descricao,
                    usuario,
                    categoria
            );

            // =========================
            // BANCO DE DADOS
            // =========================

            dao.inserir(despesa);

            JOptionPane.showMessageDialog(
                    this,
                    "Despesa adicionada com sucesso!"
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
                    + "Exemplo: 350.00",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}