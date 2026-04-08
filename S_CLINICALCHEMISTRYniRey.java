import javax.print.DocFlavor.READER;

public class S_CLINICALCHEMISTRYniRey extends P_LABTESTniRey{
    private String Gender;

    public S_CLINICALCHEMISTRYniRey(String TestName, double InputValue, String Unit){
        super(TestName, InputValue, Unit);
    }

    @Override
    public String EVALUATERESULT(){
        if(TestName.equalsIgnoreCase("FBS Test")){
            if(InputValue < 70) 
                return "Low";
            else if (InputValue <= 99) 
                return "Normal";
            else 
                return "High";
        }else if(TestName.equalsIgnoreCase("RBS Test")){
            if(InputValue < 70)
                return "Low";
            else if (InputValue <= 139)
                return "Normal";
            else
                return "High";

        }else if(TestName.equalsIgnoreCase("Total Cholesterol")){
            if(InputValue < 150)
                return "Low";
            else if(InputValue <= 200)
                return "Normal";
            else
               return "High";

        }else if(TestName.equalsIgnoreCase("HDL Test")){
            if(Gender.equalsIgnoreCase("M")){
                if(InputValue < 40)
                    return "Low";
                else if(InputValue <= 80)
                    return "Normal";
                else
                    return "High";
            }else
                if(InputValue < 42)
                    return "Low";
                else if(InputValue <= 88)
                    return "Normal";
                else
                    return "High";
        }else if(TestName.equalsIgnoreCase("LDL Test")){
            if(InputValue < 50)
                return "Low";
            else if(InputValue <= 130)
                return "Normal";
            else
                return "High";

        }else if(TestName.equalsIgnoreCase("Triglycerides Test")){
            if(Gender.equalsIgnoreCase("M")){
                if(InputValue < 60)
                    return "Low";
                else if(InputValue <= 165)
                    return "Normal";
                else
                    return "High";
            }else
                if(InputValue < 40)
                    return "Low";
                else if(InputValue <= 140)
                    return "Normal";
                else
                    return "High";

        }else if(TestName.equalsIgnoreCase("Creatinine Test")){
            if(Gender.equalsIgnoreCase("M")){
                if(InputValue < 0.9)
                    return "Low";
                else if(InputValue <= 1.3)
                    return "Normal";
                else
                    return "High";

            }else
                if(InputValue < 0.6)
                    return "Low";
                else if(InputValue <= 1.2)
                    return "Normal";
                else 
                    return "High";

        }else if(TestName.equalsIgnoreCase("Uric Acid Test")){
            if(Gender.equalsIgnoreCase("M")){
                if(InputValue < 3.5)
                    return "Low";
                else if(InputValue <= 7.2)
                    return "Normal";
                else 
                    return "High";
            }else
                if(InputValue < 2.6)
                    return "Low";
                else if(InputValue <= 6)
                    return "Normal";
                else 
                    return "High";

        }else if(TestName.equalsIgnoreCase("BUN Test")){
            if(InputValue < 6)
                return "Low";
            else if(InputValue <= 20)
                return "Normal";
            else 
                return "High";

        }else if(TestName.equalsIgnoreCase("AST / SGOT Test")){
            if(InputValue < 0)
                return "Low";
            else if(InputValue <= 45)
                return "Normal";
            else
                return "High";

        }else if(TestName.equalsIgnoreCase("ALT / SGPT Test")){
            if(InputValue < 0)
                return "Low";
            else if(InputValue <= 48)
                return "Normal";
            else 
                return "High";

        }else if(TestName.equalsIgnoreCase("Sodium Test")){
            if(InputValue < 135)
                return "Low";
            else if(InputValue <= 145)
                return "Normal";
            else 
                return "High";

        }else if(TestName.equalsIgnoreCase("Potassium Test")){
            if(InputValue < 3.5)
                return "Low";
            else if(InputValue <= 5)
                return "Normal";
            else 
                return "High";

        }else if(TestName.equalsIgnoreCase("Chloride Test")){
            if(InputValue < 96)
                return "Low";
            else if(InputValue <= 110)
                return "Normal";
            else
                return "High";

        }else if(TestName.equalsIgnoreCase("Total Calcium Test")){
            if(InputValue < 8.6)
                return "Low";
            else if(InputValue <= 10.28)
                return "Normal";
            else 
                return "High";

        }else if(TestName.equalsIgnoreCase("Ionized Calcium Test")){
            if(InputValue < 4.4)
                return "Low";
            else if(InputValue <= 5.2)
                return "Normal";
            else
                return "High";
        }
    return "RESULT NOT DEFINED";
    }

    @Override
    public String CONVERTTOSI(){
        if(TestName.equalsIgnoreCase("FBS Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.055);

        }else if(TestName.equalsIgnoreCase("RBS Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.055);

        }else if(TestName.equalsIgnoreCase("Total Cholesterol Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.026);

        }else if(TestName.equalsIgnoreCase("HDL Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.026);

        }else if(TestName.equalsIgnoreCase("LDL Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.026);

        }else if(TestName.equalsIgnoreCase("Triglycerides Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.0114);

        }else if(TestName.equalsIgnoreCase("Creatinine Test")){
            return String.format("%.2f umol/L ", InputValue * 88.4);

        }else if(TestName.equalsIgnoreCase("Uric Acid Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.06);

        }else if(TestName.equalsIgnoreCase("BUN Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.357);

        }else if(TestName.equalsIgnoreCase("AST / SGOT Test")){
            return String.format("%.2f uKat/L ", InputValue * 0.017);

        }else if(TestName.equalsIgnoreCase("ALT / SGPT Test")){
            return String.format("%.2f uKat/L ", InputValue * 0.017);

        }else if(TestName.equalsIgnoreCase("Sodium Test")){
            return String.format("%.2f mmol/L ", InputValue * 1);

        }else if(TestName.equalsIgnoreCase("Potassium Test")){
            return String.format("%.2f mmol/L ", InputValue * 1);

        }else if(TestName.equalsIgnoreCase("Chloride Test")){
            return String.format("%.2f mmol/L ", InputValue * 1);

        }else if(TestName.equalsIgnoreCase("Total Calcium Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.2495);

        }else if(TestName.equalsIgnoreCase("Ionized Calcium Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.2495);
        }
    return "";
    }
    
}
