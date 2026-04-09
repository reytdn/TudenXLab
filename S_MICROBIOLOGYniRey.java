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

    @Override
    public double GETCOST(){
        if(TestName.equalsIgnoreCase("Urine Culture")) 
            return 900.0;
        else if(TestName.equalsIgnoreCase("Sputum Culture")) 
            return 1000.0;
        else if(TestName.equalsIgnoreCase("Throat Swab Culture")) 
            return 950.0;
        else if(TestName.equalsIgnoreCase("Wound Culture")) 
            return 1150.0;
        else 
            return 0.0;
    }

}
