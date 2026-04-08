public class S_CLINICALMICROSCOPYniRey extends P_LABTESTniRey{
    public S_CLINICALMICROSCOPYniRey(String TestName, double InputValue, String Unit){
        super(TestName, InputValue, Unit);
    }
    @Override
    public String EVALUATERESULT(){
        if(TestName.equalsIgnoreCase("Urinalysis - Protein"))
            return(InputValue <= 10) ? "NEGATIVE" : "POSITIVE";
        if(TestName.equalsIgnoreCase("Pregnancy Test"))
            return(InputValue < 25) ? "NEGATIVE" : "POSITIVE";
        if(TestName.equalsIgnoreCase("Urinalysis - Blood"))
            return(InputValue <= 2) ? "NEGATIVE" : "POSITIVE";
        if(TestName.equalsIgnoreCase("Fat Test"))
            return(InputValue <= 7) ? "NEGATIVE" : "POSITIVE";
        return "RESULT NOT DEFINED";
    }
    @Override
    public String CONVERTTOSI(){
        return "";
    }

    @Override
    public double GETCOST(){
        if(TestName.equalsIgnoreCase("Urinalysis - Protein")) 
            return 200;
        else if(TestName.equalsIgnoreCase("Fat Test")) 
            return 600;
        else if(TestName.equalsIgnoreCase("Pregnancy Test")) 
            return 250;
        else if(TestName.equalsIgnoreCase("Urinalysis - Blood")) 
            return 200;
        else 
            return 0.0;
    }

}
