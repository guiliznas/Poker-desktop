package poker2;

import java.util.ArrayList;
import java.util.List;
import poker2.acoes.Acao;
import poker2.telas.Player;

public class Jogador {

    String nome;
    Carta[] mao = new Carta[2];
    double fichas;
    double aposta;
    ValorMao valorMao;
    Acao acao;
    boolean temCarta;
    Player player;

    public Jogador(String nome, double fichas, Player player) {
        this.nome = nome;
        this.fichas = fichas;
        this.player = player;
    }

    public void ganhou(double pot){
        fichas += pot;
    }
    
    public Player getPlayer(){
        return player;
    }
    
    public Jogador publicClone(){
        Jogador clone = new Jogador(nome, fichas, null);
        clone.temCarta = temCarta;
        clone.aposta = aposta;
        clone.acao = acao;
        return clone;
    }
    
    public void resetar() {
        mao[0] = null;
        mao[1] = null;
        aposta = 0;
        temCarta = false;
    }

    public void resetaMao() {
        mao[0] = null;
        mao[1] = null;
        aposta = 0;
        temCarta = false;
    }

    public void smallBlind(double small) {
        acao = Acao.smallBlind;
        fichas -= small;
        aposta += small;
    }

    public void bigBlind(double big) {
        acao = Acao.bigBlind;
        fichas -= big;
        aposta += big;
    }
    
    public void setMao(List<Carta> cartas) {
        mao[0] = null;
        mao[1] = null;
        if (cartas != null) {
            if (cartas.size() == 2) {
                mao[0] = cartas.get(0);
                mao[1] = cartas.get(1);
                temCarta = true;
            } else {
                throw new IllegalArgumentException("Mta carta");
            }
        }
    }

    public String maoToString(){
        String aux = "";
        if (mao[0] != null) {
            aux += mao[0].getId();
        }
        aux += "|";
        if (mao[1] != null) {
            aux += mao[1].getId();
        }
        return aux;
    }
    
    public void apostar(double aposta){
        if (aposta > this.fichas) {
            throw new IllegalStateException("Nao tem tanta ficha");
        }
        fichas -= aposta;
    }
    
    public boolean allIn(){
        return temCarta && (fichas == 0);
    }
    
    public Carta[] getCartas() {
        return mao;
    }
    
    public ArrayList<Carta> getCartasList(){
        ArrayList<Carta> list = new ArrayList<>();
        list.add(mao[0]);
        list.add(mao[1]);
        return list;
    }

    @Override
    public String toString() {
        return nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getFichas() {
        return fichas;
    }

    public void setFichas(double fichas) {
        this.fichas = fichas;
    }

    public double getAposta() {
        return aposta;
    }

    public void setAposta(double aposta) {
        this.aposta = aposta;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public boolean temCarta() {
        return temCarta;
    }

    public void setTemCarta(boolean temCarta) {
        this.temCarta = temCarta;
    }

    public ValorMao getValorMao() {
        return valorMao;
    }

    public void setValorMao(ValorMao valorMao) {
        this.valorMao = valorMao;
    }

}
