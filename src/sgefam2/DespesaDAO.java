package sgefam2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    // CADASTRAR DESPESA
    public void inserir(Despesa despesa) {

        String sql = "INSERT INTO despesa "
                + "(valor, data_despesa, descricao, id_usuario, id_categoria) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDouble(1, despesa.getValor());
            stmt.setString(2, despesa.getData());
            stmt.setString(3, despesa.getDescricao());
            stmt.setInt(4, despesa.getUsuario().getId());
            stmt.setInt(5, despesa.getCategoria().getId());

            stmt.executeUpdate();

            System.out.println("Despesa cadastrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar despesa:");
            e.printStackTrace();
        }
    }

    // LISTAR DESPESAS
    public List<Despesa> listar() {

        List<Despesa> lista = new ArrayList<>();

        String sql = "SELECT d.id_despesa, d.valor, d.data_despesa, "
                + "d.descricao, "
                + "u.id_usuario, u.nome, u.login, u.senha, u.perfil, "
                + "c.id_categoria, c.nome_categoria "
                + "FROM despesa d "
                + "INNER JOIN usuario u "
                + "ON d.id_usuario = u.id_usuario "
                + "INNER JOIN categoria c "
                + "ON d.id_categoria = c.id_categoria "
                + "ORDER BY d.id_despesa";

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

                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nome_categoria")
                );

                Despesa despesa = new Despesa(
                        rs.getInt("id_despesa"),
                        rs.getDouble("valor"),
                        rs.getString("data_despesa"),
                        rs.getString("descricao"),
                        usuario,
                        categoria
                );

                lista.add(despesa);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar despesas:");
            e.printStackTrace();
        }

        return lista;
    }
}
