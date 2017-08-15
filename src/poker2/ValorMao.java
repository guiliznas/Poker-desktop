package poker2;

import java.util.ArrayList;

public class ValorMao implements Comparable<ValorMao> {

    int[] valorMao = new int[13];
    int[] naipeMao = new int[4];
    int[] valorMesa = new int[13];
    int[] naipeMesa = new int[4];
    int[] valorAll = new int[13];
    int[] naipeAll = new int[4];

    ArrayList<Carta> mao = new ArrayList<>();
    ArrayList<Carta> mesa = new ArrayList<>();
    ArrayList<Carta> all = new ArrayList<>();

    int valor;

    //Ultima carta da sequencia(0-12)
    int valorSequencia = -1;
    boolean sequenciaAsUm = false;

    int naipeFlush = -1;
    int cartaAltaFlush = -1;

    int quadraValor = -1;
    int trincaValor = -1;
    int[] pares = new int[2];
    int qtdPares = 0;

    int cartaAlta;

    Rank rank;

    public ValorMao(ArrayList<Carta> mao, ArrayList<Carta> mesa) {
        this.mao = mao;
        this.mesa = mesa;
        this.all.addAll(mao);
        this.all.addAll(mesa);
        this.pares[0] = -1;
        this.pares[1] = -1;

        calcQtd();
        acharSequencia();
        acharFlush();
        cartaAlta();
        acharRepetidos();

        ehUmPar();
        ehDoisPares();
        ehTrinca();
        ehSequencia();
        ehFlush();
        ehFullHouse();
        ehQuadra();
        ehStraightFlush();

        //Calcula valor
        if (rank.getValor() == 1) {
            valor = 13 - valorSequencia;
        } else if (rank.getValor() == 2) {
            valor = 13 - quadraValor;
        } else if (rank.getValor() == 3){
            valor = 13 - trincaValor;
            if (pares[0] > pares[1]) {
                valor += 13 - pares[0];
            } else {
                valor += 13 - pares[1];
            }
        } else if (rank.getValor() == 4) {
            valor = 13 - cartaAltaFlush;
        } else if (rank.getValor() == 5){
            valor = 13 - valorSequencia;
        } else if (rank.getValor() == 6){
            valor = 13 - trincaValor;
        } else if (rank.getValor() == 7){
            valor = (13 - pares[0]) + (13 - pares[1]);
        } else if (rank.getValor() == 8){
            valor = (13 - pares[0]);
        } else if (rank.getValor() == 9){
            valor = 13 - cartaAlta;
        }
        valor += (10 - rank.getValor()) * 100;
    }

    public void calcQtd() {
        for (Carta carta : mao) {
            valorMao[carta.getValor()]++;
            naipeMao[carta.getNaipe()]++;
            valorAll[carta.getValor()]++;
            naipeAll[carta.getNaipe()]++;
        }
        for (Carta carta : mesa) {
            valorMesa[carta.getValor()]++;
            naipeMesa[carta.getNaipe()]++;
            valorAll[carta.getValor()]++;
            naipeAll[carta.getNaipe()]++;
        }
    }

    public void acharSequencia() {
        boolean isSequence = false;
        int valor = -1;
        int aux = 0;
        for (int i = 0; i < 13; i++) {
            if (valorAll[i] == 00) {
                aux = 0;
                isSequence = false;
            } else {
                if (!isSequence) {
                    isSequence = true;
                    valor = i;
                }
                aux++;
                if (aux >= 5) {
                    valorSequencia = valor;
                    break;
                }
            }
        }
        if ((aux == 4) && (valor == 9) && valorAll[0] > 0) {
            sequenciaAsUm = true;
            valorSequencia = 0;
        } else if (valorSequencia != -1) {
            valorSequencia = valorSequencia + 4;
        }
    }

    public void acharFlush() {
        for (int i = 0; i < 4; i++) {
            if (naipeAll[i] >= 5) {
                naipeFlush = i;
                for (Carta carta : all) {
                    if (carta.getNaipe() == naipeFlush) {
                        if (!sequenciaAsUm || (carta.getValor() != 0)) {
                            cartaAltaFlush = carta.getValor();
                        }
                    }
                }
            }
        }
    }

    public void acharRepetidos() {
        for (int i = 0; i < 13; i++) {
            if (valorAll[i] == 4) {
                quadraValor = i;
            } else if (valorAll[i] == 3) {
                trincaValor = i;
            } else if (valorAll[i] == 2) {
                if (qtdPares < 2) {
                    pares[qtdPares++] = i;
                }
            }
        }
    }

    //Ultima carta igual, vai conferindo de tras para a frente. 
    //Considerar As como carta alta
    public boolean ehStraightFlush() {
        if (valorSequencia != -1 && naipeFlush != -1 && valorSequencia == cartaAltaFlush) {
            int valorSequenciaAux = -1;
            int ultimoNaipe = -1;
            int ultimoValor = -1;
            int emSequencia = 1;
            int emFlush = 1;
            for (Carta carta : all) {
                int valor = carta.getValor();
                int naipe = carta.getNaipe();
                if (ultimoValor != -1) {
                    int valorDiferenca = valor - ultimoValor;
                    if (valorDiferenca == 1) {
                        //Cartas consecutivas, possivel sequencia
                        emSequencia++;
                        if (valorSequenciaAux == -1) {
                            valorSequenciaAux = ultimoValor;
                        }
                        if (naipe == ultimoNaipe) {
                            emFlush++;
                        } else {
                            emFlush = 1;
                        }
                        if (emSequencia >= 5 && emFlush >= 5) {
                            //Achou!
                            break;
                        }
                    } else if (valorDiferenca == 0) {
                        //Carta repetida, pula
                    } else {
                        //Carca nao consecutiva, restart
                        valorSequenciaAux = -1;
                        emFlush = 1;
                        emSequencia = 1;
                    }
                }
                ultimoValor = valor;
                ultimoNaipe = naipe;
            }
            //colocar em cima
            if (emSequencia >= 5 & emFlush >= 5) {
                if (valorSequencia == 0) {
                    //Sequencia Real
                    rank = Rank.ROYALFLUSH;
                    return true;
                } else {
                    //Straight Flush normal
                    rank = Rank.STRAIGHTFLUSH;
                    return true;
                }
            } else if (sequenciaAsUm & emSequencia >= 4 & emFlush >= 4) {
                //Sequencia com As == Um
                rank = Rank.STRAIGHTFLUSH;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean ehQuadra() {
        if (quadraValor != -1) {
            rank = Rank.QUADRA;
            return true;
        } else {
            return false;
        }
    }

    public boolean ehFullHouse() {
        if (trincaValor != -1 && qtdPares > 0) {
            rank = Rank.FULLHOUSE;
            return true;
        } else {
            return false;
        }
    }

    public boolean ehSequencia() {
        if (valorSequencia != -1) {
            rank = Rank.STRAIGHT;
            return true;
        } else {
            return false;
        }
    }

    public boolean ehTrinca() {
        if (trincaValor != -1) {
            rank = Rank.TRINCA;
            return true;
        } else {
            return false;
        }
    }

    public boolean ehDoisPares() {
        if (qtdPares == 2) {
            rank = Rank.TWOPAIR;
            return true;
        } else {
            return false;
        }
    }

    public boolean ehUmPar() {
        if (qtdPares == 1) {
            rank = Rank.ONEPAIR;
            return true;
        } else {
            return false;
        }
    }

    public void cartaAlta() {
        for (int i = 0; i < 13; i++) {
            if (valorMao[i] != 0) {
                cartaAlta = i;
                rank = Rank.HIGHCARD;
                break;
            }
        }
    }

    public boolean ehFlush() {
        if (naipeFlush == -1) {
            return false;
        } else {
            rank = Rank.FLUSH;
            return true;
        }
    }

    public int[] getPares() {
        return pares;
    }

    public void setPares(int[] pares) {
        this.pares = pares;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public int getValor() {
        return this.valor;
    }

    @Override
    public int compareTo(ValorMao o) {
        if (valor > o.getValor()) {
            return -1;
        } else if (valor < o.getValor()) {
            return 1;
        } else {
            return 0;
        }
    }

}
