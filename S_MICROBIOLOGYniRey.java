public class S_MICROBIOLOGYniRey extends P_LABTESTniRey{
    public S_MICROBIOLOGYniRey(String TestName, double InputValue, String Unit){
        super(TestName, InputValue, Unit);
    }

    @Override
    public String EVALUATERESULT(){
        return(InputValue < 100000) ? "NEGATIVE" : "POSITIVE";
    }
    
    @Override
    public String CONVERTTOSI(){
        return "";
    }
}
