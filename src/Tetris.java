import javax.swing.*;

public class Tetris extends JFrame {

    private JPanel tetrisPanel;
    private Scoreboard scoreBoard;
    private Board board;
    private Deltatime deltaTime;

    public Tetris(String title) {
        super(title);
        setContentPane(tetrisPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Cerrar y que no aparezca nada en la consola.
        pack(); //Adjust the Frame size to the main panel content
        setLocationRelativeTo(null);//Center Window

        board.initGame();//times.start();
        board.setScoreBoard(scoreBoard);

    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new Tetris("Tetris");
                frame.setVisible(true);
            }
        });
    }

}

