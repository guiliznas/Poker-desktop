package poker2.telas;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelHelp extends JPanel implements ActionListener {

    private JButton botao = new JButton();
    private Help h;

    public PanelHelp() {
        setBackground(Padrao.corMesa);
        setLayout(new GridBagLayout());
        botao.setSize(100, 30);
        botao.setMnemonic('H');
        botao.addActionListener(this);
        botao.setText("HELP");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        add(botao, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.h == null) {
            this.h = new Help();
        }
        h.setVisible(true);
    }

}
