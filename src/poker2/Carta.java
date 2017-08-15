package poker2;
import javax.swing.JOptionPane;
public class Carta {
//Valor
    int AS = 0;
    int REI = 1;
    int DAMA = 2;
    int VALETE = 3;
    int DEZ = 4;
    int NOVE = 5;
    int OITO = 6;
    int SETE = 7;
    int SEIS = 8;
    int CINCO = 9;
    int QUATRO = 10;
    int TRES = 11;
    int DOIS = 12;
    String[] Valores = {"A", "K", "Q", "J", "D", "9", "8", "7", "6", "5", "4", "3", "2"};
//Naipe
    int COPAS = 0;
    int OURO = 1;
    int PAUS = 2;
    int ESPADAS = 3;
    String[] Naipes = {"C", "O", "P", "E"};

    int valor;
    int naipe;
    int id;

    public Carta(int valor, int naip) {
        if (valor < 0 || valor > 12) {
            JOptionPane.showMessageDialog(null, "Valor invalido");
        } else if (naip < 0 || naip > 3) {
            JOptionPane.showMessageDialog(null, "Naipe invalido");
        } else {
            this.valor = valor;
            this.naipe = naip;
        }
        id = (this.valor * 4) + this.naipe; 
    }
    public Carta(String card) {
        card = card.trim();
        if (card == null) {
            JOptionPane.showMessageDialog(null, "Carta invalida");
        } else if (card.length() != 2) {
            JOptionPane.showMessageDialog(null, "Carta invalida");
        } else {
            String val = card.substring(0,1);
            String nai = card.substring(1,2);
            for (int i = 0; i < 13; i++) {
                if (val.equalsIgnoreCase(Valores[i])) {
                    this.valor = i;
                }
            }
            for (int i = 0; i < 4; i++) {
                if (nai.equalsIgnoreCase(Naipes[i])) {
                    this.naipe = i;
                }
            }
        }
    } 
    public int getValor(){
        return valor;
    }
    public int getNaipe(){
        return naipe;
    }
    public int getId(){
        return id;
    }
    @Override
    public String toString(){
        return (Valores[valor] + Naipes[naipe]);
    }

    public String getUrl() {
        String aux = "null";
        switch (this.id) {
            case 0:
                aux = "/recursos/Hearts/A.png";
                break;
            case 1:
                aux = "/recursos/Diamonds/A.png";
                break;
            case 2:
                aux = "/recursos/Clubs/A.png";
                break;
            case 3:
                aux = "/recursos/Spades/A.png";
                break;
            case 4:
                aux = "/recursos/Hearts/K.png";
                break;
            case 5:
                aux = "/recursos/Diamonds/K.png";
                break;
            case 6:
                aux = "/recursos/Clubs/K.png";
                break;
            case 7:
                aux = "/recursos/Spades/K.png";
                break;
            case 8:
                aux = "/recursos/Hearts/Q.png";
                break;
            case 9:
                aux = "/recursos/Diamonds/Q.png";
                break;
            case 10:
                aux = "/recursos/Clubs/Q.png";
                break;
            case 11:
                aux = "/recursos/Spades/Q.png";
                break;
            case 12:
                aux = "/recursos/Hearts/J.png";
                break;
            case 13:
                aux = "/recursos/Diamonds/J.png";
                break;
            case 14:
                aux = "/recursos/Clubs/J.png";
                break;
            case 15:
                aux = "/recursos/Spades/J.png";
                break;
            case 16:
                aux = "/recursos/Hearts/10.png";
                break;
            case 17:
                aux = "/recursos/Diamonds/10.png";
                break;
            case 18:
                aux = "/recursos/Clubs/10.png";
                break;
            case 19:
                aux = "/recursos/Spades/10.png";
                break;
            case 20:
                aux = "/recursos/Hearts/9.png";
                break;
            case 21:
                aux = "/recursos/Diamonds/9.png";
                break;
            case 22:
                aux = "/recursos/Clubs/9.png";
                break;
            case 23:
                aux = "/recursos/Spades/9.png";
                break;
            case 24:
                aux = "/recursos/Hearts/8.png";
                break;
            case 25:
                aux = "/recursos/Diamonds/8.png";
                break;
            case 26:
                aux = "/recursos/Clubs/8.png";
                break;
            case 27:
                aux = "/recursos/Spades/8.png";
                break;
            case 28:
                aux = "/recursos/Hearts/7.png";
                break;
            case 29:
                aux = "/recursos/Diamonds/7.png";
                break;
            case 30:
                aux = "/recursos/Clubs/7.png";
                break;
            case 31:
                aux = "/recursos/Spades/7.png";
                break;
            case 32:
                aux = "/recursos/Hearts/6.png";
                break;
            case 33:
                aux = "/recursos/Diamonds/6.png";
                break;
            case 34:
                aux = "/recursos/Clubs/6.png";
                break;
            case 35:
                aux = "/recursos/Spades/6.png";
                break;
            case 36:
                aux = "/recursos/Hearts/5.png";
                break;
            case 37:
                aux = "/recursos/Diamonds/5.png";
                break;
            case 38:
                aux = "/recursos/Clubs/5.png";
                break;
            case 39:
                aux = "/recursos/Spades/5.png";
                break;
            case 40:
                aux = "/recursos/Hearts/4.png";
                break;
            case 41:
                aux = "/recursos/Diamonds/4.png";
                break;
            case 42:
                aux = "/recursos/Clubs/4.png";
                break;
            case 43:
                aux = "/recursos/Spades/4.png";
                break;
            case 44:
                aux = "/recursos/Hearts/3.png";
                break;
            case 45:
                aux = "/recursos/Diamonds/3.png";
                break;
            case 46:
                aux = "/recursos/Clubs/3.png";
                break;
            case 47:
                aux = "/recursos/Spades/3.png";
                break;
            case 48:
                aux = "/recursos/Hearts/2.png";
                break;
            case 49:
                aux = "/recursos/Diamonds/2.png";
                break;
            case 50:
                aux = "/recursos/Clubs/2.png";
                break;
            case 51:
                aux = "/recursos/Spades/2.png";
                break;
        }
        return aux;
    }
}
