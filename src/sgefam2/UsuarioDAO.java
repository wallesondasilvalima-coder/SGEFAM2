package sgefam2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario autenticar(String login, String senha) {

        String sql = "SELECT id_usuario, nome, login, senha, perfil "
                   + "FROM usuario "
                   + "WHERE login = ? AND senha = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nome"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    rs.getString("perfil")
                );
            }

        } catch (Exception e) {

            System.out.println("Erro ao autenticar usuário:");
            e.printStackTrace();
        }

        return null;
    }

    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT id_usuario, nome, login, senha, perfil "
                   + "FROM usuario "
                   + "ORDER BY nome";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nome"),
                    rs.getString("login"),
                    rs.getString("senha"),
                    rs.getString("perfil")
                );

                lista.add(usuario);
            }

        } catch (Exception e) {

            System.out.println("Erro ao listar usuários:");
            e.printStackTrace();
        }

        return lista;
    }
}