
public class ScoreData implements Comparable<ScoreData> {
    public int score;
    public String date;

    public ScoreData(int score, String date) {
        this.score = score;
        this.date = date;
    }

    @Override
    public String toString() {
        return "" + score + ": " + date;
    }


    @Override
    public int compareTo(ScoreData o) {
        return Integer.compare(score, o.score);
    }
}
