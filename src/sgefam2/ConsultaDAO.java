
package sgefam2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public List<Object[]> consultar(String periodo) {

        List<Object[]> lista = new ArrayList<>();

        String sql =
                "SELECT data_mov, tipo, descricao, valor, usuario, categoria " +
                "FROM ( " +

                "SELECT r.data_receita AS data_mov, " +
                "'Receita' AS tipo, " +
                "r.descricao AS descricao, " +
                "r.valor AS valor, " +
                "u.nome AS usuario, " +
                "'' AS categoria " +
                "FROM receita r " +
                "INNER JOIN usuario u " +
                "ON r.id_usuario = u.id_usuario " +

                "UNION ALL " +

                "SELECT d.data_despesa AS data_mov, " +
                "'Despesa' AS tipo, " +
                "d.descricao AS descricao, " +
                "d.valor AS valor, " +
                "u.nome AS usuario, " +
                "c.nome_categoria AS categoria " +
                "FROM despesa d " +
                "INNER JOIN usuario u " +
                "ON d.id_usuario = u.id_usuario " +
                "INNER JOIN categoria c " +
                "ON d.id_categoria = c.id_categoria " +

                ") AS movimentacoes ";

        if (!periodo.isEmpty() && !periodo.equalsIgnoreCase("ALL")) {
            sql += "WHERE DATE_FORMAT(data_mov, '%Y-%m') = ? ";
        }

        sql += "ORDER BY data_mov DESC";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (!periodo.isEmpty() && !periodo.equalsIgnoreCase("ALL")) {
                stmt.setString(1, periodo);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String data = rs.getString("data_mov");
                String tipo = rs.getString("tipo");
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                String usuario = rs.getString("usuario");
                String categoria = rs.getString("categoria");

                String valorFormatado;

                if (tipo.equals("Receita")) {
                    valorFormatado = String.format(
                            java.util.Locale.forLanguageTag("pt-BR"),
                            "+ R$ %,.2f",
                            valor
                    );
                } else {
                    valorFormatado = String.format(
                            java.util.Locale.forLanguageTag("pt-BR"),
                            "- R$ %,.2f",
                            valor
                    );
                }

                // Converte YYYY-MM-DD para DD/MM/YYYY
                if (data != null && data.length() == 10) {
                    data = data.substring(8, 10) + "/"
                            + data.substring(5, 7) + "/"
                            + data.substring(0, 4);
                }

                lista.add(new Object[]{
                    data,
                    tipo,
                    descricao,
                    valorFormatado,
                    usuario,
                    categoria
                });
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar movimentações:");
            e.printStackTrace();
        }

        return lista;
    }
}
