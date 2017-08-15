package poker2.acoes;

public abstract class Acao {

    private String nome;
    private String acao;
    private double valor;
    
    public static Acao allIn = new AllIn();
    public static Acao apostar = new Apostar(0);
    public static Acao bigBlind = new BigBlind();
    public static Acao cobrir = new Cobrir();
    public static Acao check = new Check();
    public static Acao Continue = new Continue();
    public static Acao desistir = new Desistir();
    public static Acao aumentar = new Aumentar(0);
    public static Acao smallBlind = new SmallBlind();
    
    
    public Acao(String nome, String acao) {
        this.nome = nome;
        this.acao = acao;
        this.valor = 0;
    }
    
    public Acao(String nome, String acao, double valor) {
        this.nome = nome;
        this.acao = acao;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getAcao() {
        return acao;
    }

    public double getValor() {
        return valor;
    }
    
}
