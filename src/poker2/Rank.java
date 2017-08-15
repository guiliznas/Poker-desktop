
package poker2;

public enum Rank {
    ROYALFLUSH("Royal Flush", 0), STRAIGHTFLUSH("Straight Flush", 1),
    QUADRA("4 of a Kind", 2), FULLHOUSE("Full House", 3),
    FLUSH("Flush", 4), STRAIGHT("Straight", 5),
    TRINCA("3 of a Kind", 6), TWOPAIR("2 Pair", 7),
    ONEPAIR("1 Pair", 8), HIGHCARD("High Card", 9);
    
    private String nome;
    private int valor;
    
    Rank(String nome, int valor){
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }
    
    
}
