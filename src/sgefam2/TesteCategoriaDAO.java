
package sgefam2;

public class TesteCategoriaDAO {

    public static void main(String[] args) {

        CategoriaDAO dao = new CategoriaDAO();

        // Cadastrar uma categoria de teste
        Categoria categoria = new Categoria("Teste");

        dao.inserir(categoria);

        // Listar categorias
        System.out.println("\nCategorias cadastradas:");

        for (Categoria c : dao.listar()) {
            System.out.println(
                c.getId() + " - " + c.getNome()
            );
        }
    }
}
