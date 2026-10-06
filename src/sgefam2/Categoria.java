package sgefam2;

public class Categoria {

    private int id;
    private String nome;

    // Construtor para categorias que já possuem ID
    public Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Construtor para cadastrar uma nova categoria
    public Categoria(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

@Override
public String toString() {
    return nome;
}

}

