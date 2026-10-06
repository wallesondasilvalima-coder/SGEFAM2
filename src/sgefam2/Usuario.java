package sgefam2;


public class Usuario {
  private int id;
    private String nome;
    private String login;
    private String senha;
    private String perfil;

    public Usuario(int id, String nome, String login, String senha, String perfil) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getPerfil() { return perfil; }

    @Override
    public String toString() { return nome; }  
}
