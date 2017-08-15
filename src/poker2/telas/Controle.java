package poker2.telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import poker2.acoes.Acao;
import poker2.acoes.Apostar;
import poker2.acoes.Aumentar;

public class Controle extends JPanel implements ActionListener {

    private JButton botaoCheck;
    private JButton botaoCobrir;
    private JButton botaoApostar;
    private JButton botaoAumentar;
    private JButton botaoDesistir;
    private JButton botaoContinuar;
    private Aposta painelAposta;
    private final Object monitor = new Object();
    private Acao acaoEscolhida;

    public Controle() {
        setBackground(Padrao.corMesa);
        botaoContinuar = createActionButton(Acao.Continue);
        botaoCheck = createActionButton(Acao.check);
        botaoCobrir = createActionButton(Acao.cobrir);
        botaoApostar = createActionButton(Acao.apostar);
        botaoAumentar = createActionButton(Acao.aumentar);
        botaoDesistir = createActionButton(Acao.desistir);
        painelAposta = new Aposta();
    }

    public void esperarUsuario(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                removeAll();
                add(botaoContinuar);
                repaint();
            }
        });
        Set<Acao> acaoPermitidas = new HashSet<>();
        acaoPermitidas.add(Acao.Continue);
        getUsuarioEntrada(0, 0, acaoPermitidas);
    }
    
    public Acao getUsuarioEntrada(double apostaMin, double apostaMax, final/*Nao precisa disso*/ Set<Acao> acaoPermitidas){
        acaoEscolhida = null;
        while (acaoEscolhida == null){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    removeAll();
                    if (acaoPermitidas.contains(Acao.Continue)) {
                        add(botaoContinuar);
                    } else {
                        if (acaoPermitidas.contains(Acao.check)) {
                            add(botaoCheck);
                        }
                        if (acaoPermitidas.contains(Acao.cobrir)) {
                            add(botaoCobrir);
                        }
                        if (acaoPermitidas.contains(Acao.apostar)) {
                            add(botaoApostar);
                        }
                        if (acaoPermitidas.contains(Acao.aumentar)) {
                            add(botaoAumentar);
                        }
                        if (acaoPermitidas.contains(Acao.desistir)) {
                            add(botaoDesistir);
                        }
                    }
                    repaint();
                }
            });
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                }
            }
            if (acaoEscolhida == Acao.apostar || acaoEscolhida == Acao.aumentar) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        removeAll();
                        add(painelAposta);
                        repaint();
                    }
                });
                acaoEscolhida = painelAposta.mostra(acaoEscolhida, apostaMin, apostaMax);
                if (acaoEscolhida == Acao.apostar) {
                    acaoEscolhida = new Apostar(painelAposta.getValor());
                } else if (acaoEscolhida == Acao.aumentar) {
                    acaoEscolhida = new Aumentar(painelAposta.getValor());
                } else {
                    acaoEscolhida = null;
                }
            }
        }
        return acaoEscolhida;
    }
    
    public JButton createActionButton(Acao acao) {
        String label = acao.getNome();
        JButton button = new JButton(label);
        button.setMnemonic(label.charAt(0));
        button.setSize(100, 30);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object origem = e.getSource();
        if (origem == botaoContinuar) {
            acaoEscolhida = Acao.Continue;
        } else if (origem == botaoCheck) {
            acaoEscolhida = Acao.check;
        } else if (origem == botaoCobrir) {
            acaoEscolhida = Acao.cobrir;
        } else if (origem == botaoApostar){
            acaoEscolhida = Acao.apostar;
        } else if (origem == botaoAumentar) {
            acaoEscolhida = Acao.aumentar;
        } else {
            acaoEscolhida = Acao.desistir;
        }
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }
}
