import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Board extends JPanel {

    class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    goLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    goRight();
                    break;
                case KeyEvent.VK_UP:
                    rotateShape();
                    break;
                case KeyEvent.VK_DOWN:
                    goDown();
                    break;
                case KeyEvent.VK_P:
                    stopTime();
                    break;
                default:
            }
            repaint();//Repintar el board al cambiar de posicion de la pieza
        }
    }

    private final Color[] colors = {new Color(30,30,30),
            new Color(251, 233, 26),
            new Color(251, 159, 27), new Color(251, 27, 243),
            new Color(20, 139, 250), new Color(27, 243, 251),
            new Color(104, 251, 26), new Color(252, 21, 100)};


    public static final int NUM_ROWS = 22;
    public static final int NUM_COLS = 10;
    private Tetrominoes[][] matrixBoard;
    boolean notFreeSquare[][];
    private int currentRow;
    private int currentCol;
    private Boolean collision;
    public static int deltaTime = 500;
    private boolean gameOn = true;
    private Scoreboard scoreBoard;
    private Shape currentShape;
    private Timer timer;
    MyKeyAdapter keyAdepter;
    Music sound;
    Music sound2;
    Music sound3;


    public Board() { //Logica del juego
        super();
        matrixBoard = new Tetrominoes[NUM_ROWS][NUM_COLS];
        currentShape = new Shape();
        sound = new Music();
        sound2 = new Music();
        sound3 = new Music();
        notFreeSquare = new boolean[NUM_ROWS][NUM_COLS];


        System.out.println("Start Game");
        createCurrentShape();



        timer = new Timer(deltaTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameOver()) {

                    tick();
                } else {
                    finishGame();
                    try {
                        showHighScores();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    //sound.close();
                }
            }
        });
    }

    public void setScoreBoard(Scoreboard s) {
        scoreBoard = s;
    }


    //Every delta time operations
    public void tick() {

        if (canMove(currentShape, currentRow + 1, currentCol)) {
            printShapeBoard();
            checkLine();
            if (!gameOver()) {
                createCurrentShape();
            }

        } else {
            currentRow++;
        }
        repaint();
    }

    public void initGame() {
        currentRow = 0;
        currentCol = NUM_COLS / 2;
        playMusic();
        timer.start();
        keyAdepter = new MyKeyAdapter();
        addKeyListener(keyAdepter);
        setFocusable(true);//Para recibir eventos del teclado
        initMatrixBoard();

    }

    public void finishGame() {
        currentShape.setShape(Tetrominoes.NoShape);
        timer.stop();
        playFinishMusic();
        initMatrixBoard();

        colors[0] = new Color( 35, 54, 73
        );
        repaint();

        setFocusable(false);
    }

    public boolean gameOver() {
        boolean b = false;
        if (notFreeSquare[currentShape.maxY()][NUM_COLS / 2]) {
            b = true;
        }
        return b;
    }

    private void initMatrixBoard() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                matrixBoard[i][j] = Tetrominoes.NoShape;
            }
        }
    }

    private void createCurrentShape() {
        currentShape.setRandomShape();
        currentRow = 0;
        currentCol = NUM_COLS / 2;

        collision = false;
    }

    // Paint
    private void drawSquare(Graphics g, int row, int col, Tetrominoes shape) {
        int x = col * squareWidth();
        int y = row * squareHeight();
        Color color = colors[shape.ordinal()];
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2,
                squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }

    private void drawCurrentShape(Graphics g) {

        for (int i = 0; i < 4; i++) {
            drawSquare(g, currentRow + currentShape.getY(i), currentCol + currentShape.getX(i), currentShape.getShape());
        }
    }

    private void drawBoard(Graphics g) {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                drawSquare(g, i, j, matrixBoard[i][j]);
            }
        }


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawCurrentShape(g);
    }

    private void printShapeBoard() {
        //if (!gameOver()) {
        for (int i = 0; i < 4; i++) {
            int row = currentRow + currentShape.getY(i);
            int col = currentCol + currentShape.getX(i);
            if (row >= 0) {
                notFreeSquare[row][col] = true;
                matrixBoard[row][col] = currentShape.getShape();
            }
        }
        //}
    }

    //px to Square
    public int squareWidth() {

        return (int) (getSize().getWidth() / NUM_COLS);
    }

    public int squareHeight() {

        return (int) (getSize().getHeight() / NUM_ROWS);
    }

    //Collision
    private boolean canMove(Shape shape, int row, int col) {
        //Last Row
        if (row + 1 + shape.maxY() >= NUM_ROWS + 1) {
            this.collision = true;
        }

        //With other shapes
        for (int i = 0; i < 4; i++) {
            int y = row + shape.getY(i);
            int x = col + shape.getX(i);
            if (y >= 0 && y < NUM_ROWS && x >= 0 && x < NUM_COLS) {
                if (notFreeSquare[y][x]) {
                    this.collision = true;
                    break;
                }
            }
        }
        return collision;
    }

    //Check complete line
    private void checkLine() {
        int count;
        for (int i = 0; i < NUM_ROWS; i++) {
            count = 0;
            for (int j = 0; j < NUM_COLS; j++) {
                if (notFreeSquare[i][j]) {
                    count++;
                }
                if (count == NUM_COLS) {
                    completeRow(i);
                }
            }
        }

    }

    private void completeRow(int row) {
        scoreBoard.incrementScore(NUM_COLS);
        playSoundEffect();
        for (int i = row - 1; i > 0; i--) {
            for (int j = 0; j < NUM_COLS; j++) {
                matrixBoard[i + 1][j] = matrixBoard[i][j];
                notFreeSquare[i + 1][j] = notFreeSquare[i][j];
            }
        }
    }

    //Keys Actions
    public void stopTime() {
        if (gameOn) {
            timer.stop();
            gameOn = false;
        } else {
            timer.start();
            gameOn = true;
        }
    }

    private void rotateShape() {
        Shape s = currentShape;
        s = currentShape.rotateRight();

        if (currentCol + s.minX() < 0) {
            currentCol = Math.abs(s.minX());
        } else if (currentCol + s.maxX() > NUM_COLS - 1) {
            currentCol -= s.maxX();
        }

        if (!canMove(s, currentRow, currentCol)) {
            currentShape = s;
        } else {
            this.collision = false;
        }
    }

    private void goRight() {

        boolean b = canMove(currentShape, currentRow, currentCol + 1);
        int max = NUM_COLS - 1 - currentShape.maxX();
        ;
        if (!b && currentCol < max) {
            currentCol++;
        } else {
            this.collision = false;
        }
    }

    private void goLeft() {
        boolean b = canMove(currentShape, currentRow, currentCol - 1);
        int max = currentShape.minX();
        if (max == -1) {
            max = 1;
        } else if (max < -1) {
            max = 2;
        }

        if (!b && currentCol > max) {
            currentCol--;
        } else {

            this.collision = false;
        }
    }

    private void goDown() {
        boolean b = canMove(currentShape, currentRow + 1, currentCol);
        if (!b)
            currentRow++;
    }

    //Music
    public void playMusic(){
        sound.setFile(0);
        sound.play();
        sound.loop();
    }

    public void playSoundEffect(){
        sound3.setFile(2);
        sound3.play();
    }

    public void playFinishMusic(){
        sound.stop();
        sound2.setFile(1);
        sound2.play();
    }

    //High Scores
    public void showHighScores() throws IOException {
        HighScores dialog = new HighScores("High Score");
        dialog.checkNewRecord(scoreBoard.getScore());
        dialog.printScores();
        dialog.setVisible(true);
    }
}
