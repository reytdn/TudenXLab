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
        if(TestName.equalsIgnoreCase("COVID-19 RT-PCR")) 
            return 2500;
        else if(TestName.equalsIgnoreCase("HIV Viral Load")) 
            return 6000;
        else if(TestName.equalsIgnoreCase("Hepatitis B DNA")) 
            return 5000;
        else if(TestName.equalsIgnoreCase("HPV DNA")) 
            return 4500;
        else 
            return 0.0;
    }

}
