package poker2;

import java.util.HashSet;
import java.util.Set;

public class Ficha {
    private double aposta;
    public Set<Jogador> apostadores;
    
    public Ficha(double aposta){
        this.aposta = aposta;
        this.apostadores = new HashSet<>();
    }
    
    public double getAposta(){
        return this.aposta;
    }
    
    public Set<Jogador> getApotadores(){
        return apostadores;
    }
    
    public void addApostador(Jogador j){
        apostadores.add(j);
    }
    
    public boolean ehApostador(Jogador j ){
        return apostadores.contains(j);
    }
    
    public double getTotal(){
        return aposta * apostadores.size();
    }
    
    public Ficha dividir(Jogador j, double aposta){
        Ficha ficha = new Ficha(this.aposta - aposta);
        for(Jogador jog : apostadores){
            ficha.addApostador(j);
        }
        this.aposta = aposta;
        apostadores.add(j);
        return ficha;
    }
    
    public void reset(){
        aposta = 0;
        apostadores.clear();
    }
    
    
}
