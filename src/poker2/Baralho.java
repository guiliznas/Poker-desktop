package poker2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
    
    private Carta[] cartas = new Carta[52];
    private Carta[] cartasAUX = new Carta[52];
    private ArrayList<Carta> lista = new ArrayList<>();
    private int num;
    
    Baralho(){
        int aux = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                cartas[aux++] = new Carta(j, i);
            }
        }
        cartasAUX = cartas;
    }
    
    public void Embaralhar(){
        for (int i = 0; i < 52; i++) {
            lista.add(cartas[i]);
        }
        Collections.shuffle(lista);
        for (int i = 0; i < 52; i++) {
            cartas[i] = lista.get(i);
        }
    }
    
    public void reset(){
        this.Embaralhar();
        this.num = 0;
    }
    
    public List<Carta> pegar(int num){
        if (num + this.num >= 52) {
            throw new IllegalStateException("Acabou as cartas");
        }
        List<Carta> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(cartas[this.num++]);
        }
        return list;
    }
    
    public Carta pegar(){
        if (this.num + 1 >= 52) {
            throw new IllegalStateException("Acabou as cartas");
        }
        return cartas[this.num++];
    }
    public Carta[] getCarta(){
        return this.cartas;
    }
    public Carta[] getCartaAux(){
        return this.cartasAUX;
    }
    @Override
    public String toString(){
       String aux = "";
        for (int i = 0; i < 52; i++) {
            if (i != 51) {
                aux = aux + cartas[i].toString() + ", ";
            } else {
                aux = aux + cartas[i].toString();
            }
        }
        return aux;
    }
}
