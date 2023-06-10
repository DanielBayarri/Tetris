import javax.swing.*;
import java.awt.*;

public class Scoreboard extends JPanel {

    //JFRAME - La ventana.
    //JPanel -> Un contenedor.

    private JLabel label;
    private int score;
    private Board board;

    public Scoreboard() {
        super();//LLame al contructor de la superclase
        setLayout(new FlowLayout()); //un tipo de layout.
        label = new JLabel();
        add(label); //Añade el label en el centro.

        label.setText("0");
        score = 0;
    }

    public int getScore(){
        return score;
    }

    public void incrementScore(int increment){
        score += increment;
        label.setText("" + score);
    }
}
