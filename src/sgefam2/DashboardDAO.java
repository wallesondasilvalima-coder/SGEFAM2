
package sgefam2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DashboardDAO {

    public double totalReceitas() {
        String sql = "SELECT COALESCE(SUM(valor), 0) AS total FROM receita";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar total de receitas:");
            e.printStackTrace();
        }

        return 0;
    }

    public double totalDespesas() {
        String sql = "SELECT COALESCE(SUM(valor), 0) AS total FROM despesa";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            System.out.println("Erro ao consultar total de despesas:");
            e.printStackTrace();
        }

        return 0;
    }

    public List<Object[]> listarUltimasMovimentacoes() {

        List<Object[]> lista = new ArrayList<>();

        String sql =
                "SELECT data_mov, tipo, descricao, valor, usuario " +
                "FROM ( " +

                "SELECT r.data_receita AS data_mov, " +
                "'Receita' AS tipo, " +
                "r.descricao AS descricao, " +
                "r.valor AS valor, " +
                "u.nome AS usuario " +
                "FROM receita r " +
                "INNER JOIN usuario u " +
                "ON r.id_usuario = u.id_usuario " +

                "UNION ALL " +

                "SELECT d.data_despesa AS data_mov, " +
                "'Despesa' AS tipo, " +
                "d.descricao AS descricao, " +
                "d.valor AS valor, " +
                "u.nome AS usuario " +
                "FROM despesa d " +
                "INNER JOIN usuario u " +
                "ON d.id_usuario = u.id_usuario " +

                ") AS movimentacoes " +
                "ORDER BY data_mov DESC " +
                "LIMIT 5";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String data = rs.getString("data_mov");
                String tipo = rs.getString("tipo");
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                String usuario = rs.getString("usuario");

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
                    usuario
                });
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar últimas movimentações:");
            e.printStackTrace();
        }

        return lista;
    }
}