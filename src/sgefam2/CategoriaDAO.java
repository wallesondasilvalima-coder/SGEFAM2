
package sgefam2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    // CADASTRAR
    public void inserir(Categoria categoria) {

        String sql = "INSERT INTO categoria (nome_categoria) VALUES (?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();

            System.out.println("Categoria cadastrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar categoria:");
            e.printStackTrace();
        }
    }

    // LISTAR
    public List<Categoria> listar() {

        List<Categoria> lista = new ArrayList<>();

        String sql = "SELECT id_categoria, nome_categoria "
                   + "FROM categoria ORDER BY id_categoria";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Categoria categoria = new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nome_categoria")
                );

                lista.add(categoria);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar categorias:");
            e.printStackTrace();
        }

        return lista;
    }

    // ATUALIZAR
    public void atualizar(Categoria categoria) {

        String sql = "UPDATE categoria "
                   + "SET nome_categoria = ? "
                   + "WHERE id_categoria = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());

            stmt.executeUpdate();

            System.out.println("Categoria atualizada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao atualizar categoria:");
            e.printStackTrace();
        }
    }

    // EXCLUIR
    public void excluir(int id) {

        String sql = "DELETE FROM categoria WHERE id_categoria = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Categoria excluída com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao excluir categoria:");
            e.printStackTrace();
        }
    }
}
