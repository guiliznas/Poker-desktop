package poker2.telas;

import java.net.URL;
import javax.swing.ImageIcon;
import poker2.Carta;

public abstract class ResourceManager {

    public static ImageIcon getCartaImagem(Carta carta) {
        return getIcon(carta.getUrl());
    }

    public static ImageIcon getIcon(String caminho) {
        URL url = ResourceManager.class.getResource(caminho);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            throw new RuntimeException("Arquivo nao encontrado: " + caminho);
        }
    }
}
