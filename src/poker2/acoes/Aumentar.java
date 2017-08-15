package poker2.acoes;

public class Aumentar extends Acao {

    public Aumentar(double valor) {
        super("Aumentar", "aumenta ", valor);
    }
    
    @Override
    public String toString(){
        return "Aumentar " + this.getValor();
    }
}
