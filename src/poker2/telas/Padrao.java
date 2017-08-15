
package poker2.telas;

import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public interface Padrao {
    Color corMesa = new Color(60, 220, 90);
    Color corText = new Color(0, 0, 0);
    Border bordaLabel = new LineBorder(Color.black, 1);
    Border bordaPanel = new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10));
    Color corText1 = new Color(10, 136, 200);
}
