/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sgefam2;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private MainFrame main;
    private JTextField tfLogin;
    private JPasswordField pfSenha;

    private UsuarioDAO dao;

    public LoginPanel(MainFrame main) {

        this.main = main;
        this.dao = new UsuarioDAO();

        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        JLabel title = new JLabel(
            "<html><h1>SGEFAM</h1>"
            + "<small>Sistema de Gestão Financeira Familiar</small></html>"
        );

        c.gridy = 0;
        c.insets = new Insets(10, 10, 20, 10);

        add(title, c);

        c.insets = new Insets(5, 5, 5, 5);

        // LOGIN
        c.gridy++;
        add(new JLabel("Login:"), c);

        tfLogin = new JTextField(20);

        c.gridy++;
        add(tfLogin, c);

        // SENHA
        c.gridy++;
        add(new JLabel("Senha:"), c);

        pfSenha = new JPasswordField(20);

        c.gridy++;
        add(pfSenha, c);

        // BOTÃO ENTRAR
        JButton bEntrar = new JButton("Entrar");

        bEntrar.addActionListener(e -> doLogin());

        c.gridy++;
        add(bEntrar, c);
    }

    // =====================================================
    // LOGIN
    // =====================================================

    private void doLogin() {

        String login = tfLogin.getText().trim();
        String senha = new String(pfSenha.getPassword());

        if (login.isEmpty() || senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Informe o login e a senha."
            );

            return;
        }

        // Consultar usuário no banco
        Usuario usuario = dao.autenticar(login, senha);

        if (usuario != null) {

            // Guardar usuário que fez login
            main.usuarioLogado = usuario;

            JOptionPane.showMessageDialog(
                this,
                "Bem-vindo(a), " + usuario.getNome() + "!"
            );

            // Atualizar Dashboard
            main.dashboardPanel.refresh();

            // Ir para Dashboard
            main.showCard("dashboard");

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Login ou senha incorretos.",
                "Erro",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // LIMPAR CAMPOS
    // =====================================================

    public void clearFields() {

        tfLogin.setText("");
        pfSenha.setText("");
    }
}
