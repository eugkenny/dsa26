public class Stopwatch {

    private long startTime;

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - startTime;
    }
}
