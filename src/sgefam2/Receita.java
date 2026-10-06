package sgefam2;


public class Receita {
    private int id;
    private double valor;
    private String data;
    private String descricao;
    private Usuario usuario;

    public Receita(int id, double valor, String data, String descricao, Usuario usuario) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    public int getId() { return id; }
    public double getValor() { return valor; }
    public String getData() { return data; }
    public String getDescricao() { return descricao; }
    public Usuario getUsuario() { return usuario; }
}
