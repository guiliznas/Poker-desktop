package poker2.telas;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JFrame implements KeyListener {

    JPanel painel = new JPanel();

    public Menu() {
        setSize(800, 600);
        setTitle("Poker Simulator ©2016");
        try {
            setIconImage(ImageIO.read(new File("recursos/Token.png")));
        } catch (IOException ex) {
            throw new IllegalStateException("Nao carregou imagem");
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        addKeyListener(this);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        painel.setLayout(null);
        painel.setBackground(Color.BLACK);
        painel.setSize(1000, 800);

    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

}
