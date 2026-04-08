public abstract class P_LABTESTniRey {
    protected String TestName;
    protected double InputValue;    
    protected String Unit;

    public P_LABTESTniRey(String TestName, double InputValue, String Unit){
        this.TestName = TestName;
        this.InputValue = InputValue;
        this.Unit = Unit;
    }

    public abstract String EVALUATERESULT();
    public abstract String CONVERTTOSI();

    public void DISPLAYRESULT(){
        String Result = EVALUATERESULT();
        String SIValue = CONVERTTOSI();
        System.out.println(TestName + "= " + Result + " | " + InputValue + " " + Unit +
                            (SIValue.isEmpty() ? "" : " | " + SIValue));
    }
}
