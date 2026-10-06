package sgefam2;


public class Despesa {
    private int id;
    private double valor;
    private String data;
    private String descricao;
    private Usuario usuario;
    private Categoria categoria;

    public Despesa(int id, double valor, String data, String descricao, Usuario usuario, Categoria categoria) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.usuario = usuario;
        this.categoria = categoria;
    }

    public int getId() { return id; }
    public double getValor() { return valor; }
    public String getData() { return data; }
    public String getDescricao() { return descricao; }
    public Usuario getUsuario() { return usuario; }
    public Categoria getCategoria() { return categoria; }
}
