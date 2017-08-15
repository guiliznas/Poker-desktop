package poker2.bots;

import java.util.List;
import java.util.Set;
import poker2.Carta;
import poker2.Jogador;
import poker2.acoes.Acao;
import poker2.acoes.Apostar;
import poker2.acoes.Aumentar;
import poker2.telas.Player;

public class Bot implements Player {

    private Carta[] mao = new Carta[2];

    @Override
    public void mensagemRecebida(String txt) {
    }

    @Override
    public void entrandoMesa(double bigBlind, List<Jogador> jogadores) {
    }

    @Override
    public void novaMao(Jogador dealer) {
        mao[0] = null;
        mao[1] = null;
    }

    @Override
    public void proximo(Jogador j) {
    }

    @Override
    public void updateJogador(Jogador j) {
        if (j.getCartas().length == 2) {
            this.mao = j.getCartas();
        }
    }

    @Override
    public void updateMesa(List<Carta> cartas, double aposta, double fichas) {
    }

    @Override
    public void jogadorVez(Jogador j) {
    }
    
    public Acao acao(double apostaMin, double apostaAtual, Set<Acao> acoesPermitidas, int aumentadas){
        Acao acao = null;
        if (mao[0] != null && mao[1] != null) {
            if (acoesPermitidas.size() == 1) {
                acao = Acao.check;
            } else {
                int valor = mao[0].getValor() + mao[1].getValor();
                if (valor > 13) {
                    if (acoesPermitidas.contains(Acao.check)) {
                        acao = Acao.check;
                    } else {
                        acao = Acao.desistir;
                    }
                } else if (valor > 8) {
                    if (acoesPermitidas.contains(Acao.cobrir)) {
                        acao = Acao.cobrir;
                    } else {
                        acao = Acao.check;
                    }
                } else {
                    double apos = apostaMin;
                    if (acoesPermitidas.contains(Acao.apostar)) {
                        acao = new Apostar(apos);
                    } else if (acoesPermitidas.contains(Acao.aumentar)) {
                        acao = new Aumentar(apos);
                    } else if (acoesPermitidas.contains(Acao.cobrir)) {
                        acao = Acao.cobrir;
                    } else {
                        acao = Acao.check;
                    }
                }
            }
        }
        if (Math.floor((Math.random() * 100) + 1) % 5 == 0) {
            double apos = apostaMin * 2;
            if (acoesPermitidas.contains(Acao.apostar)) {
                acao = new Apostar(apos);
            } else if (acoesPermitidas.contains(Acao.aumentar)) {
                acao = new Aumentar(apos);
            } else if (acoesPermitidas.contains(Acao.cobrir)) {
                acao = Acao.cobrir;
            } else {
                acao = Acao.check;
            }
        }
        if (acao == null) {
            acao = Acao.desistir;////aq
        } else {
            if (aumentadas == 1 && acao == Acao.aumentar) {
                if (acoesPermitidas.contains(Acao.cobrir)) {
                    acao = Acao.cobrir;
                } else if (acoesPermitidas.contains(Acao.check)){
                    acao = Acao.check;
                }
            }
        }
        return acao;
    }

    @Override
    public Acao acao(double apostaMin, double apostaAtual, Set<Acao> acoesPermitidas) {
        Acao acao = null;
        if (mao[0] != null && mao[1] != null) {
            if (acoesPermitidas.size() == 1) {
                acao = Acao.check;
            } else {
                int valor = mao[0].getValor() + mao[1].getValor();
                if (valor > 13) {
                    if (acoesPermitidas.contains(Acao.check)) {
                        acao = Acao.check;
                    } else {
                        acao = Acao.desistir;
                    }
                } else if (valor > 8) {
                    if (acoesPermitidas.contains(Acao.cobrir)) {
                        acao = Acao.cobrir;
                    } else {
                        acao = Acao.check;
                    }
                } else {
                    double apos = apostaMin;
                    if (acoesPermitidas.contains(Acao.apostar)) {
                        acao = new Apostar(apos);
                    } else if (acoesPermitidas.contains(Acao.aumentar)) {
                        acao = new Aumentar(apos);
                    } else if (acoesPermitidas.contains(Acao.cobrir)) {
                        acao = Acao.cobrir;
                    } else {
                        acao = Acao.check;
                    }
                }
            }
        }
        if (Math.floor((Math.random() * 100) + 1) % 5 == 0) {
            double apos = apostaMin * 2;
            if (acoesPermitidas.contains(Acao.apostar)) {
                acao = new Apostar(apos);
            } else if (acoesPermitidas.contains(Acao.aumentar)) {
                acao = new Aumentar(apos);
            } else if (acoesPermitidas.contains(Acao.cobrir)) {
                acao = Acao.cobrir;
            } else {
                acao = Acao.check;
            }
        }
        if (acao == null) {
            acao = Acao.desistir;
        }
        return acao;
    }

}
