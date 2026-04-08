import java.time.LocalDateTime;

public abstract class P_LABTESTniRey {
    protected String TestName;
    protected double InputValue;
    protected String Unit;
    protected LocalDateTime TakenAt;
    protected LocalDateTime ResultAt;

    public P_LABTESTniRey(String testName, double inputValue, String unit) {
        this.TestName = testName;
        this.InputValue = inputValue;
        this.Unit = unit;

        this.TakenAt = LocalDateTime.now();
        this.ResultAt = this.TakenAt.plusSeconds(10);
    }

    public LocalDateTime getTakenAt() {
        return TakenAt;
    }

    public LocalDateTime getResultAt() {
        return ResultAt;
    }

    public abstract String EVALUATERESULT();
    public abstract String CONVERTTOSI();
}