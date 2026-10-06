
package sgefam2;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReceitaDAO {

    public void inserir(Receita receita) {

        String sql = "INSERT INTO receita "
                   + "(valor, data_receita, descricao, id_usuario) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setDouble(1, receita.getValor());
            stmt.setString(2, receita.getData());
            stmt.setString(3, receita.getDescricao());
            stmt.setInt(4, receita.getUsuario().getId());

            stmt.executeUpdate();

            System.out.println("Receita cadastrada!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
