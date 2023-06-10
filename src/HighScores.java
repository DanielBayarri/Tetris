import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HighScores extends JDialog {
    private JPanel mainPanel;
    private JList<String> JList;
    private JLabel Tittle;
    private List<ScoreData> scores;
    BufferedReader in = null;
    PrintWriter out = null;


    public HighScores(String title) throws IOException {
        super();
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setPreferredSize(new Dimension((220), (200)));
        setContentPane(mainPanel);
        pack(); // Adjusts the Frame size to the main panel content
        setLocationRelativeTo(null);//Center Window
        initListScores();
    }


    public void initListScores() throws IOException {
        scores = new ArrayList<ScoreData>();
        String line;
        String[] parts;
        int score;
        String date;
        ScoreData data;
        try {
            in = new BufferedReader(new FileReader("HighScores.txt"));
            while ((line = in.readLine()) != null) {
                parts = line.split(",");
                score = Integer.parseInt(parts[0]);
                date = parts[1];
                data = new ScoreData(score, date);
                scores.add(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void checkNewRecord(int score){
        int lastRecord = scores.get(4).score;
        if (lastRecord <= score) {
            newHighScore(score);
        }
    }

    private void newHighScore(int score) {
            scores.get(4).score = score;
            scores.get(4).date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            scores.sort(Collections.reverseOrder());
        try {
            out = new PrintWriter(new FileWriter("HighScores.txt"));
            for (ScoreData data : scores) {
                String line = "";
                line += data.score;
                line += "," + data.date;
                out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public void printScores() {
        String[] l = new String[5];
        for (int i = 0; i < 5; i++) {
            l[i] = scores.get(i).toString();
        }
        JList.setListData(l);

    }

}

