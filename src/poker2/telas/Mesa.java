package poker2.telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import poker2.Carta;

public class Mesa extends JPanel {
    private Controle painelControle;
    private JLabel labelAposta;
    private JLabel labelFichas;
    private JLabel[] labelCartas;
    private JLabel labelMensagem;

    public Mesa(Controle painelControle) {
        this.painelControle = painelControle;
        
        setBorder(Padrao.bordaPanel);
        setBackground(Padrao.corMesa);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        JLabel label = new JLabel("Aposta");
        label.setForeground(Padrao.corText1);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 5, 0, 5);
        add(label, gbc);
        
        label = new JLabel("Fichas");
        label.setForeground(Padrao.corText1);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(0, 5, 0, 5);
        add(label, gbc);
        
        labelAposta = new JLabel(" ");
        labelAposta.setBorder(Padrao.bordaLabel);
        labelAposta.setForeground(Padrao.corText1);
        labelAposta.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(labelAposta, gbc);
        
        labelFichas = new JLabel(" ");
        labelFichas.setBorder(Padrao.bordaLabel);
        labelFichas.setForeground(Padrao.corText1);
        labelFichas.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(labelFichas, gbc);
        
        labelCartas = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            labelCartas[i] = new JLabel(ResourceManager.getIcon("/recursos/Back_Covers/Pomegranate.png"));
            gbc.gridx = i;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0.0;
            gbc.weighty = 0.0;
            gbc.insets = new Insets(5, 1, 5, 1);
            add(labelCartas[i], gbc);
        }
        
        labelMensagem = new JLabel();
        labelMensagem.setForeground(Color.red);
        labelMensagem.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(labelMensagem, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(this.painelControle, gbc);
        
        setPreferredSize(new Dimension(400, 270));
        
        update(null, 0, 0);
    }
    
    public void update(List<Carta> cartas, double aposta, double fichas){
        if (aposta == 0) {
            labelAposta.setText(" ");
        } else {
            labelAposta.setText("$ " + aposta);
        }
        if (fichas == 0) {
            labelFichas.setText(" ");
        } else {
            labelFichas.setText("$ " + fichas);
        }
        int nCartas;
        if (cartas == null) {
            nCartas = 0;
        } else {
            nCartas = cartas.size();
        }
        for (int i = 0; i < 5; i++) {
            if (i < nCartas) {
                labelCartas[i].setIcon(ResourceManager.getCartaImagem(cartas.get(i)));
            } else {
                labelCartas[i].setIcon(ResourceManager.getIcon("/recursos/Back_Covers/Pomegranate.png")); 
            }
        }
    }
    public void setMensagem(String txt){
        if (txt.length() == 0) {
            labelMensagem.setText(" ");
        } else {
            labelMensagem.setText(txt);
        }
    }
    public void esperaUsuarioEntrada(){
        painelControle.esperarUsuario();
    }
}
