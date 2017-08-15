package poker2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import poker2.acoes.Acao;
import poker2.acoes.Apostar;
import poker2.acoes.Aumentar;

public class Poker {

    private double bigBlind;
    private List<Jogador> jogadores;
    private List<Jogador> jogadoresInGame;
    private Baralho baralho;
    private List<Carta> baralhoAux;
    private int dealerPos;
    private Jogador dealer;
    private int vez;
    private Jogador jogador;
    private double apostaMin;
    private double aposta;
    private List<Ficha> fichas;
    private Jogador aumentou;
    private int aumentadas;
    private final int MAX_AUMENTADAS = 3;

    private ArrayList<Carta> mesa;

    public Poker(double bigBlind) {
        this.bigBlind = bigBlind;
        this.jogadores = new ArrayList<>();
        this.jogadoresInGame = new ArrayList<>();
        this.baralho = new Baralho();
        this.baralhoAux = new ArrayList<>();
        this.fichas = new ArrayList<>();
        this.mesa = new ArrayList<>();
    }

    public void addJogador(Jogador j) {
        jogadores.add(j);
    }

    public void start() {
        for (Jogador j : jogadores) {
            j.getPlayer().entrandoMesa(bigBlind, jogadores);
        }

        dealerPos = -1;
        vez = -1;
        while (true) {
            int nJogInGame = 0;
            for (Jogador j : jogadores) {
                if (j.getFichas() >= bigBlind) {
                    nJogInGame++;
                }
            }
            if (nJogInGame > 1) {
                startRodada();
            } else {
                break;
            }
        }

        baralhoAux.clear();
        fichas.clear();
        aposta = 0;
        notificaMesaUpdate();
        for (Jogador j : jogadores) {
            j.resetaMao();
        }
        notificaJogadorUpdate(false);
        notificaMensagem("Game over.");
        JOptionPane.showMessageDialog(null, "Acabou o Jogo!");
    }

    private void startRodada() {
        resetarMaos();
        baralho.reset();
        if (jogadoresInGame.size() > 2) {
            proximo();
        }
        smallBlind();
        proximo();
        bigBlind();
        distribuirCartas();
        rodada();
        //Flop
        if (jogadoresInGame.size() > 1) {
            aposta = 0;
            distribuirMesa("Flop", 3);
            rodada();
            //Turn
            if (jogadoresInGame.size() > 1) {
                aposta = 0;
                distribuirMesa("Turn", 1);
                apostaMin = bigBlind * 2;
                rodada();
                //River
                if (jogadoresInGame.size() > 1) {
                    aposta = 0;
                    distribuirMesa("River", 1);
                    rodada();
                    //Acabou
                    if (jogadoresInGame.size() > 1) {
                        aposta = 0;
                        encerrar();
                    }
                }
            }
        }
    }

    private void encerrar() {
        //Ordem pra ver quem ganha
        List<Jogador> jogsAux = new ArrayList<>();
        //allIn
        for (Ficha f : fichas) {
            for (Jogador j : f.getApotadores()) {
                if (!jogsAux.contains(j) && j.allIn()) {
                    jogsAux.add(j);
                }
            }
        }
        //quem apostou/aumentou por ultimo
        if (aumentou != null) {
            if (!jogsAux.contains(aumentou)) {
                jogsAux.add(aumentou);
            }
        }
        //resto dos jogadores
        int pos = (dealerPos + 1) % jogadoresInGame.size();
        while (jogsAux.size() < jogadoresInGame.size()) {
            Jogador j = jogadoresInGame.get(pos);
            if (!jogsAux.contains(j)) {
                jogsAux.add(j);
            }
            pos = (pos + 1) % jogadoresInGame.size();
        }
        //Jogadores q mostram/saem pela ordem
        boolean aux = true;
        int melhorValorMao = -1;
        for (Jogador j : jogsAux) {
            ValorMao vm = new ValorMao(j.getCartasList(), this.mesa);
            boolean doShow = false;
            if (!doShow) {
                if (j.allIn()) {
                    doShow = true;
                    aux = false;
                } else if (aux) {
                    doShow = true;
                    melhorValorMao = vm.getValor();
                    aux = false;
                } else if (vm.getValor() >= melhorValorMao) {
                    doShow = true;
                    melhorValorMao = vm.getValor();
                }
            }
            if (doShow) {
                //Amostra mao
                for (Jogador jog : jogadores) {
                    jog.getPlayer().updateJogador(j);
                }
                notificaMensagem(j.getNome() + " tem " + vm.rank.getNome());
            } else {
                //Fold
                j.setMao(null);
                jogadoresInGame.remove(j);
                for (Jogador jog : jogadores) {
                    if (jog.equals(j)) {
                        jog.getPlayer().updateJogador(j);
                    } else {
                        //esconder dos outros
                        jog.getPlayer().updateJogador(j.publicClone());
                    }
                }
                notificaMensagem(j.getNome() + " desistiu");
            }
        }

        //Organizar jogadores pelo valor da mao, decrescente
        Map<ValorMao, List<Jogador>> rank = new TreeMap<>();
        for (Jogador j : jogadoresInGame) {
            ValorMao vm = new ValorMao(j.getCartasList(), this.mesa);
            List<Jogador> list = rank.get(vm);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(j);
            rank.put(vm, list);
        }

        //distribuicao do pot
        double totalFichas = getTotalFichas();
        Map<Jogador, Double> divisaoFicha = new HashMap<>();
        for (ValorMao vm : rank.keySet()) {
            List<Jogador> wins = rank.get(vm);
            for (Ficha f : fichas) {
                //qnts ganharam
                int numWins = 0;
                for (Jogador w : wins) {
                    if (f.ehApostador(w)) {
                        numWins++;
                    }
                }
                if (numWins > 0) {
                    //Dividir ficha entre ganhadores
                    double fichaDivi = f.getTotal() / numWins;
                    for (Jogador w : wins) {
                        if (f.ehApostador(w)) {
                            Double antes = divisaoFicha.get(w);
                            if (antes != null) {
                                divisaoFicha.put(w, (antes + fichaDivi));
                            } else {
                                divisaoFicha.put(w, fichaDivi);
                            }
                        }
                    }
                    //Verifica se tem fichas impares
                    double imparFicha = f.getTotal() % numWins;
                    if (imparFicha > 0) {
                        pos = dealerPos;
                        while (imparFicha > 0) {
                            pos = (pos + 1) % jogadoresInGame.size();
                            Jogador win = jogadoresInGame.get(pos);
                            Double antes = divisaoFicha.get(win);
                            if (antes != null) {
                                divisaoFicha.put(win, antes + 1);
                                imparFicha -= 1;
                            }
                        }
                    }
                    f.reset();
                }
            }
        }
        //Divide entre os ganhadores
        String textWins = "";
        double totalWin = 0;
        for (Jogador w : divisaoFicha.keySet()) {
            double fichaParte = divisaoFicha.get(w);
            BigDecimal a = new BigDecimal("" + fichaParte);
            w.ganhou(a.intValue());
            totalWin += a.intValue();
            if (textWins.length() > 0) {
                textWins += ", ";
            }
            textWins += w.getNome() + " ganhou " + a.intValue();
            notificaJogadorUpdate(true);
        }
        textWins += ".";
        notificaMensagem(textWins);

        //Precaucao
        if (totalWin != totalFichas) {
            throw new IllegalStateException("Divisao errada!");
        }
    }

    private void distribuirCartas() {
        int cont = 0;
        for (Jogador j : jogadoresInGame) {
            j.setMao(baralho.pegar(2));
            System.out.println(j.maoToString());
        }
        notificaJogadorUpdate(false);
        notificaMensagem(dealer.getNome() + " distribuiu as cartas");
    }

    private void distribuirMesa(String nome, int num) {
        for (int i = 0; i < num; i++) {
            Carta c = baralho.pegar();
            baralhoAux.add(c);
            mesa.add(c);
        }
        notificaJogadorUpdate(false);
        notificaMensagem(dealer.getNome() + " deu o " + nome);
    }

    private void rodada() {
        int jogs = jogadoresInGame.size();
        if (baralhoAux.size() == 0) {
            aposta = bigBlind;
        } else {
            vez = dealerPos;
            aposta = 0;
        }
        if (jogs == 2) {
            vez = dealerPos;
        }

        aumentou = null;
        aumentadas = 0;
        notificaMesaUpdate();

        while (jogs > 0) {
            proximo();
            Acao acao;
            if (jogador.allIn()) {
                acao = Acao.check;
                jogs--;
            } else {
                Set<Acao> acoes = getAcoesPermitidas(jogador);
                acao = jogador.getPlayer().acao(apostaMin, aposta, acoes);
                if (!acoes.contains(acao)) {
                    if (acao instanceof Apostar && !acoes.contains(Acao.apostar)) {
                        throw new IllegalStateException(String.format("Jogador nao pode apostar", jogador));
                    } else if (acao instanceof Aumentar && !acoes.contains(Acao.aumentar)) {
                        throw new IllegalStateException(String.format("Jogador nao pode aumentar", jogador));
                    }
                }
                jogs--;
                if (acao == Acao.check) {

                } else if (acao == Acao.cobrir) {
                    double apost = aposta - jogador.getAposta();
                    if (apost > jogador.getFichas()) {
                        apost = jogador.getFichas();
                    }
                    jogador.apostar(apost);
                    jogador.setAposta(jogador.getAposta() + apost);
                    apostar(apost);
                } else if (acao instanceof Apostar) {
                    double apost = acao.getValor();
                    if (apost < apostaMin && apost < jogador.getAposta()) {
                        throw new IllegalStateException("Nao pode apostar isso / minimo");
                    }
                    jogador.setAposta(apost);
                    jogador.apostar(apost);
                    apostar(apost);
                    aposta = apost;
                    apostaMin = apost;
                    aumentou = jogador;
                    jogs = jogadoresInGame.size();
                } else if (acao instanceof Aumentar) {
                    double apost = acao.getValor();
                    if (apost < apostaMin && apost < jogador.getFichas()) {
                        throw new IllegalStateException("Nao pode aumetar isso / minimo");
                    }
                    aposta += apost;
                    apostaMin = apost;
                    double apostando = aposta - jogador.getAposta();
                    if (apostando > jogador.getFichas()) {
                        apostando = jogador.getFichas();
                    }
                    jogador.setAposta(aposta);
                    jogador.apostar(apostando);
                    apostar(apostando);
                    aumentou = jogador;
                    aumentadas++;
                    if (aumentadas < MAX_AUMENTADAS || jogadoresInGame.size() == 2) {
                        jogs = jogadoresInGame.size();
                    } else {
                        jogs = jogadoresInGame.size() - 1;
                    }
                } else if (acao == Acao.desistir) {
                    jogador.setMao(null);
                    jogadoresInGame.remove(jogador);
                    vez--;
                    if (jogadoresInGame.size() == 1) {
                        notificaMesaUpdate();
                        notificaQuemJogou();
                        Jogador win = jogadoresInGame.get(0);
                        double winFicha = getTotalFichas();
                        win.ganhou(winFicha);
                        notificaMesaUpdate();
                        notificaMensagem(win.getNome() + " ganhou $ " + winFicha);
                        jogs = 0;
                    }
                } else {
                    throw new IllegalStateException("Acao invalida (" + acao + ")");
                }
            }
            jogador.setAcao(acao);
            if (jogs > 0) {
                notificaMesaUpdate();
                notificaQuemJogou();
            }
        }
        for (Jogador j : jogadoresInGame) {
            j.aposta = 0;
            if (j.temCarta() && j.fichas == 0) {
                j.setAcao(Acao.allIn);
            } else {
                j.setAcao(null);
            }
        }
        notificaMesaUpdate();
        notificaJogadorUpdate(false);
    }

    private void smallBlind() {
        double small = bigBlind / 2;
        jogador.smallBlind(small);
        apostar(small);
        notificaMesaUpdate();
        notificaQuemJogou();
    }

    private void bigBlind() {
        jogador.bigBlind(bigBlind);
        apostar(bigBlind);
        notificaMesaUpdate();
        notificaQuemJogou();
    }

    private void apostar(double qtd) {
        for (Ficha f : fichas) {
            if (!f.ehApostador(jogador)) {
                double aposta = f.getAposta();
                if (qtd >= 0) {
                    f.addApostador(jogador);
                    qtd -= f.getAposta();
                } else {
                    fichas.add(f.dividir(jogador, qtd));
                    qtd = 0;
                }
            }
            if (qtd <= 0) {
                break;
            }
        }
        if (qtd > 0) {
            Ficha f = new Ficha(qtd);
            f.addApostador(jogador);
            fichas.add(f);
        }
    }

    private void proximo() {
        vez = (vez + 1) % jogadoresInGame.size();
        jogador = jogadoresInGame.get(vez);
        for (Jogador j : jogadores) {
            j.getPlayer().proximo(j);
        }
    }

    private double getTotalFichas() {
        double total = 0;
        for (Ficha f : fichas) {
            total = total + f.getTotal();
        }
        return total;
    }

    private void resetarMaos() {
        baralhoAux.clear();
        fichas.clear();
        notificaMesaUpdate();

        jogadoresInGame.clear();
        for (Jogador j : jogadores) {
            j.resetaMao();
            if (j.getFichas() >= bigBlind) {
                jogadoresInGame.add(j);
            }
        }

        dealerPos = (dealerPos + 1) % jogadoresInGame.size();
        dealer = jogadoresInGame.get(dealerPos);

        baralho.Embaralhar();

        vez = dealerPos;
        jogador = jogadoresInGame.get(vez);

        apostaMin = bigBlind;
        aposta = apostaMin;

        for (Jogador j : jogadores) {
            j.getPlayer().novaMao(dealer);
        }
        notificaJogadorUpdate(false);
        notificaMensagem("Nova mao, " + dealer.getNome() + " eh o dealer");
    }

    private Set<Acao> getAcoesPermitidas(Jogador j) {
        Set<Acao> acoes = new HashSet<>();
        if (jogador.allIn()) {
            acoes.add(Acao.check);
        } else {
            double apos = jogador.getAposta();
            if (apos == aposta) {//
                acoes.add(Acao.check);
            }
            if (aposta == 0) {
                acoes.add(Acao.check);
                if (aumentadas < MAX_AUMENTADAS) {
                    acoes.add(Acao.apostar);
                }
            } else {
                if (apos < aposta) {
                    acoes.add(Acao.cobrir);
                    if (aumentadas < MAX_AUMENTADAS) {
                        acoes.add(Acao.aumentar);
                    }
                } else {
                    acoes.add(Acao.check);
                    if (aumentadas < MAX_AUMENTADAS) {
                        acoes.add(Acao.aumentar);
                    }
                }
            }
            acoes.add(Acao.desistir);
        }
        return acoes;
    }

    private void notificaMesaUpdate() {
        double ficha = getTotalFichas();
        for (Jogador j : jogadores) {
            j.getPlayer().updateMesa(baralhoAux, aposta, ficha);
        }
    }

    private void notificaJogadorUpdate(boolean encerrar) {
        for (Jogador j : jogadores) {
            for (Jogador jog : jogadores) {
                if (!encerrar && !jog.equals(j)) {
                    jog = jog.publicClone();
                }
                j.getPlayer().updateJogador(jog);
            }
        }
    }

    private void notificaMensagem(String msg) {
        for (Jogador j : jogadores) {
            j.getPlayer().mensagemRecebida(msg);
        }
    }

    private void notificaQuemJogou() {
        for (Jogador j : jogadores) {
            Jogador jog;
            if (j.equals(jogador)) {
                jog = jogador;
            } else {
                jog = jogador.publicClone();
            }
            j.getPlayer().jogadorVez(jog);
        }
    }
}
