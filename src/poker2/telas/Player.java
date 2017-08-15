package poker2.telas;

import java.util.List;
import java.util.Set;
import poker2.Carta;
import poker2.Jogador;
import poker2.acoes.Acao;

public interface Player {
    
    void mensagemRecebida(String txt);
    
    void entrandoMesa(double bigBlind, List<Jogador> jogadores);
    
    void novaMao(Jogador dealer);
    
    void proximo(Jogador j);
    
    void updateJogador(Jogador j);
    
    void updateMesa(List<Carta> cartas, double aposta, double fichas);
    
    void jogadorVez(Jogador j);
    
    Acao acao(double apostaMin, double apostaAtual, Set<Acao> acoesPermitidas);
}
