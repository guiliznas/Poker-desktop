package poker2;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import poker2.acoes.Acao;
import poker2.bots.Bot;
import poker2.telas.*;

public class Game extends JFrame implements Player {

    private double bigBlind = 10;
    private double startCash = 500;
    private GridBagConstraints gbc;
    private Mesa mesa;
    private Controle painelControle;
    private Map<String, PanelJogador> painelJogadores;
    private Jogador jogador;
    private String dealerNome;
    private String vezNome;

    public Game() {
        super("Poker Simulator ©2016");

        try {
            setIconImage(ImageIO.read(new File("src/recursos/Token.png")));
        } catch (IOException ex) {
            throw new IllegalStateException("Nao carregou imagem");
        }
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(Padrao.corMesa);
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        painelControle = new Controle();

        mesa = new Mesa(painelControle);
        addComponent(mesa, 1, 1, 1, 1);

        Map<String, Jogador> jogadores = new LinkedHashMap<>();
        jogador = new Jogador("Voce", 500, this);
        jogadores.put("Voce", jogador);
        jogadores.put("Bot1", new Jogador("Bot1", 500, new Bot()));
        jogadores.put("Bot2", new Jogador("Bot2", 500, new Bot()));
        jogadores.put("Bot3", new Jogador("Bot3", 500, new Bot()));

        Poker p = new Poker(bigBlind);
        for(Jogador j : jogadores.values()){
            p.addJogador(j);
        }
        
        painelJogadores = new HashMap<>();
        int i = 0;
        for (Jogador j : jogadores.values()) {
            PanelJogador panel = new PanelJogador();
            painelJogadores.put(j.getNome(), panel);
            switch (i++) {
                case 0://Cima
                    addComponent(panel, 1, 0, 1, 1);
                    break;
                case 1://Direita
                    addComponent(panel, 2, 1, 1, 1);
                    break;
                case 2://Baixo
                    addComponent(panel, 1, 2, 1, 1);
                    break;
                case 3://Esquerda
                    addComponent(panel, 0, 1, 1, 1);
                    break;
                default:

            }
        }

        addComponent(new PanelHelp(), 0, 0, 1, 1);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        p.start();
    }

    private void addComponent(Component component, int x, int y, int width, int height) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        getContentPane().add(component, gbc);
    }
    
    private void setDealer(boolean aux) {
        if (dealerNome != null) {
            PanelJogador panelJogador = painelJogadores.get(dealerNome);
            if (panelJogador != null) {
                panelJogador.setDealer(aux);
            }
        }
    }

    private void setVez(boolean aux) {
        if (vezNome != null) {
            PanelJogador panelJogador = painelJogadores.get(vezNome);
            if (panelJogador != null) {
                panelJogador.setEmTurno(aux);
            }
        }
    }

    @Override
    public void jogadorVez(Jogador j) {
        String nome = j.getNome();
        PanelJogador panelJogador = painelJogadores.get(nome);
        if (panelJogador != null) {
            panelJogador.update(j);
            Acao acao = j.getAcao();
            if (acao != null) {
                mesa.setMensagem(nome + " " + acao.getAcao());
                if (j.getPlayer() != this) {
                    mesa.esperaUsuarioEntrada();
                }
            }
        } else {
            throw new IllegalStateException("Nenhum jogador [" + nome + "] encontrado");
        }
    }

    @Override
    public Acao acao(double apostaMin, double apostaAtual, Set<Acao> acaoPermitidas) {
        mesa.setMensagem("Escolha uma acao:");
        return painelControle.getUsuarioEntrada(apostaMin, jogador.getFichas(), acaoPermitidas);
    }

    @Override
    public void mensagemRecebida(String txt) {
        mesa.setMensagem(txt);
        mesa.esperaUsuarioEntrada();
    }

    @Override
    public void entrandoMesa(double bigBlind, List<Jogador> jogadores) {
        for (Jogador j : jogadores) {
            PanelJogador pj = painelJogadores.get(j.getNome());
            if (pj != null) {
                pj.update(j);
            }
        }
    }

    @Override
    public void novaMao(Jogador dealer) {
        setDealer(false);
        dealerNome = dealer.getNome();
        setDealer(true);
    }

    @Override
    public void proximo(Jogador j) {
        setVez(false);
        vezNome = j.getNome();
        setVez(true);
    }

    @Override
    public void updateJogador(Jogador j) {
        PanelJogador pj = painelJogadores.get(j.getNome());
        if (pj != null) {
            pj.update(j);
        }
    }

    @Override
    public void updateMesa(List<Carta> cartas, double aposta, double fichas) {
        mesa.update(cartas, aposta, fichas);
    }
}
