import java.time.LocalDateTime;

public abstract class P_LABTESTniRey {
    protected String TestName;
    protected double InputValue;
    protected String Unit;
    protected LocalDateTime TakenAt;

    public P_LABTESTniRey(String testName, double inputValue, String unit) {
        this.TestName = testName;
        this.InputValue = inputValue;
        this.Unit = unit;

        this.TakenAt = LocalDateTime.now();
    }

    public LocalDateTime GETTAKENAT() {
        return TakenAt;
    }

    public abstract String EVALUATERESULT();
    public abstract String CONVERTTOSI();

}
