package masli.prof.cubinghelper;

public class Result {
    private double time;
    private String scramble;

    public Result(double time, String scramble) {
        this.time = time;
        this.scramble = scramble;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
    }
}
