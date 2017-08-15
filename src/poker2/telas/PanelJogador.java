package poker2.telas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import poker2.Carta;
import poker2.Jogador;
import poker2.acoes.Acao;

public class PanelJogador extends JPanel {
    private static Icon dealer = ResourceManager.getIcon("/recursos/dealer.png");
    private static Icon dealerVazio = ResourceManager.getIcon("/recursos/vazio.png");
    private static Icon backCartaVazio = ResourceManager.getIcon("/recursos/Back_Covers/vazio.png");
    private static Icon backCarta = ResourceManager.getIcon("/recursos/Back_Covers/Pomegranate.png");
    private static Border borda = new EmptyBorder(10, 10, 10, 10);
    private JLabel labelNome;
    private JLabel labelFichas;
    private JLabel labelAcao;
    private JLabel labelAposta;
    private JLabel labelCarta1;
    private JLabel labelCarta2;
    private JLabel labelDealer;

    public PanelJogador() {
        setBorder(borda);
        setBackground(Padrao.corMesa);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        labelNome = new LabelPadrao();
        labelFichas = new LabelPadrao();
        labelAcao = new LabelPadrao();
        labelAposta = new LabelPadrao();
        labelCarta1 = new JLabel(backCartaVazio);
        labelCarta2 = new JLabel(backCartaVazio);
        labelDealer = new JLabel(dealerVazio);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(labelDealer, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(1, 1, 1, 1);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(labelNome, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(labelFichas, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(labelAcao, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(labelAposta, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(labelCarta1, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(labelCarta2, gbc);
        
        setEmTurno(false);
        setDealer(false);
    }
    
    public void update(Jogador jogador){
        labelNome.setText(jogador.getNome());
        labelFichas.setText("$ " + jogador.getFichas());
        double aposta = jogador.getAposta();
        if (aposta == 0) {
            labelAposta.setText(" ");
        } else {
            labelAposta.setText("$ " + aposta);
        }
        Acao acao = jogador.getAcao();
        if (acao != null) {
            labelAcao.setText(acao.getNome());
        } else {
            labelAcao.setText(" ");
        }
        if (jogador.temCarta()) {
            Carta[] cartas = jogador.getCartas();
            if (cartas[0] != null) {
                labelCarta1.setIcon(ResourceManager.getCartaImagem(cartas[0]));
            } else {
                labelCarta1.setIcon(backCarta);
            }
            if (cartas[1] != null) {
                labelCarta2.setIcon(ResourceManager.getCartaImagem(cartas[1]));
            } else {
                labelCarta2.setIcon(backCarta);
            }
        } else {
            labelCarta1.setIcon(backCartaVazio);
            labelCarta2.setIcon(backCartaVazio);
        }
    }
    
    public void setEmTurno(boolean emTurno){
        if (emTurno) {
            labelNome.setForeground(Color.red);
        } else {
            labelNome.setForeground(Color.black);
        }
    }
    
    public void setDealer(boolean dealer){
        if (dealer) {
            labelDealer.setIcon(this.dealer);
        } else {
            labelDealer.setIcon(dealerVazio);
        }
    }
    
    private static class LabelPadrao extends JLabel {

        public LabelPadrao() {
            setBorder(Padrao.bordaLabel);
            setForeground(Padrao.corText);
            setHorizontalAlignment(SwingConstants.CENTER);
            setText(" ");
        }
        
    }
    
}
