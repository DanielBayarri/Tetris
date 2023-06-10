import javax.swing.*;
import java.awt.*;

public class Deltatime extends JPanel {

    //JFRAME - La ventana.
    //JPanel -> Un contenedor.

    private JLabel label;
    private int deltaTime;
    private Board board;

    public Deltatime() {
        super();//LLame al contructor de la superclase
        setLayout(new FlowLayout()); //un tipo de layout.
        label = new JLabel();
        add(label); //Añade el label en el centro.

        deltaTime = 500;
        label.setText("Delta Time " + Integer.toString(deltaTime));
    }

    public int getDeltaTime(){
        return deltaTime;
    }

}
