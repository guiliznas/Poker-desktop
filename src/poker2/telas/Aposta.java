package poker2.telas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import poker2.acoes.Acao;

public class Aposta extends JPanel implements ChangeListener, ActionListener {

    private final JSlider sliderQtd;
    private final JLabel labelQtd;
    private final JButton botaoConfirma;
    private final JButton botaoCancela;
    private final HashMap<Integer, Double> valores;
    private final Object monitor = new Object();
    private Acao acaoPadrao;
    private Acao acaoEscolhida;

    public Aposta() {
        setBackground(Padrao.corMesa);

        valores = new HashMap<>();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        sliderQtd = new JSlider();
        sliderQtd.setBackground(Padrao.corMesa);
        sliderQtd.setMajorTickSpacing(1);
        sliderQtd.setMinorTickSpacing(1);
        sliderQtd.setPaintTicks(true);
        sliderQtd.setSnapToTicks(true);
        sliderQtd.addChangeListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(sliderQtd, gbc);

        labelQtd = new JLabel(" ");
        labelQtd.setForeground(Padrao.corText);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 5, 0);
        add(labelQtd, gbc);

        botaoConfirma = new JButton("Confirma");
        botaoConfirma.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(botaoConfirma, gbc);

        botaoCancela = new JButton("Cancela");
        botaoCancela.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(botaoCancela, gbc);
    }

    public Acao mostra(Acao acaoPadrao, double apostaMin, double apostaMax) {
        this.acaoPadrao = acaoPadrao;
        botaoConfirma.setText(acaoPadrao.getNome());
        acaoEscolhida = null;

        valores.clear();
        int nValores = 0;
        double valor = apostaMin;
        while ((valor < apostaMax) && (nValores < 9)) {
            valores.put(nValores, valor);
            nValores++;
            valor += valor;
        }
        valores.put(nValores, apostaMax);
        sliderQtd.setMinimum(0);
        sliderQtd.setMaximum(nValores);
        sliderQtd.setValue(0);

        synchronized (monitor) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
            }
        }

        return acaoEscolhida;
    }

    public double getValor() {
        int aux = sliderQtd.getValue();
        return valores.get(aux);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int aux = sliderQtd.getValue();
        double valor = valores.get(aux);
        labelQtd.setText("$ " + valor);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoConfirma) {
            acaoEscolhida = acaoPadrao;
        } else if (e.getSource() == botaoCancela) {
            acaoEscolhida = null;
        }
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }

}
