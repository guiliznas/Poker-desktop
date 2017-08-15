
package poker2.acoes;

public class Apostar extends Acao {
    
    public Apostar(double valor) {
        super("Apostar", "aposta ", valor);
    }
    
    @Override
    public String toString(){
        return "Apostar " + this.getValor();
    }
    
}
