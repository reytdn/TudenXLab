public class S_MOLECULARPCRniRey extends P_LABTESTniRey{
    public S_MOLECULARPCRniRey(String TestName, double InputValue, String Unit){
        super(TestName, InputValue, Unit);
    }
    @Override
    public String EVALUATERESULT(){
        if(TestName.equalsIgnoreCase("COVID-19 RT-PCR"))
            return(InputValue <= 35) ? "POSITIVE" : "NEGATIVE";
        if(TestName.equalsIgnoreCase("HIV Viral Load"))
            return(InputValue < 50) ? "NEGATIVE" : "POSITIVE";
        if(TestName.equalsIgnoreCase("Hepatitis B DNA"))
            return(InputValue < 20) ? "NEGATIVE" : "POSITIVE";
        if(TestName.equalsIgnoreCase("HPV DNA"))
            return(InputValue < 20) ? "NEGATIVE" : "POSITIVE";
        return "RESULT NOT DEFINED";
    }

    @Override
    public String CONVERTTOSI(){
        return "";
    }
    
    @Override
    public double GETCOST(){
        if(TestName.equalsIgnoreCase("Urine Culture")) 
            return 900;
        else if(TestName.equalsIgnoreCase("Sputum Culture")) 
            return 1000;
        else if(TestName.equalsIgnoreCase("Throat Swab Culture")) 
            return 950;
        else if(TestName.equalsIgnoreCase("Wound Culture")) 
            return 1150;
        else 
            return 0.0;
    }

}
