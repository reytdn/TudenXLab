public class S_CLINICALCHEMISTRYniRey extends P_LABTESTniRey {
    private String Gender;

    public S_CLINICALCHEMISTRYniRey(String TestName, double InputValue, String Unit, String Gender){
        super(TestName, InputValue, Unit);
        this.Gender = Gender; // must be "M" or "F"
    }

    @Override
    public String EVALUATERESULT(){
        if(TestName.equalsIgnoreCase("FBS Test")){
            if(InputValue < 70) return "Low";
            else if (InputValue <= 99) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("RBS Test")){
            if(InputValue < 70) return "Low";
            else if (InputValue <= 139) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("Total Cholesterol")){
            if(InputValue < 150) return "Low";
            else if(InputValue <= 200) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("HDL Test")){
            if ("M".equalsIgnoreCase(Gender)) {
                if(InputValue < 40) return "Low";
                else if(InputValue <= 80) return "Normal";
                else return "High";
            } else if ("F".equalsIgnoreCase(Gender)) {
                if(InputValue < 42) return "Low";
                else if(InputValue <= 88) return "Normal";
                else return "High";
            } else {
                return "UNKNOWN GENDER";
            }

        } else if(TestName.equalsIgnoreCase("LDL Test")){
            if(InputValue < 50) return "Low";
            else if(InputValue <= 130) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("Triglycerides Test")){
            if ("M".equalsIgnoreCase(Gender)) {
                if(InputValue < 60) return "Low";
                else if(InputValue <= 165) return "Normal";
                else return "High";
            } else if ("F".equalsIgnoreCase(Gender)) {
                if(InputValue < 40) return "Low";
                else if(InputValue <= 140) return "Normal";
                else return "High";
            } else {
                return "UNKNOWN GENDER";
            }

        } else if(TestName.equalsIgnoreCase("Creatinine Test")){
            if ("M".equalsIgnoreCase(Gender)) {
                if(InputValue < 0.9) return "Low";
                else if(InputValue <= 1.3) return "Normal";
                else return "High";
            } else if ("F".equalsIgnoreCase(Gender)) {
                if(InputValue < 0.6) return "Low";
                else if(InputValue <= 1.2) return "Normal";
                else return "High";
            } else {
                return "UNKNOWN GENDER";
            }

        } else if(TestName.equalsIgnoreCase("Uric Acid Test")){
            if ("M".equalsIgnoreCase(Gender)) {
                if(InputValue < 3.5) return "Low";
                else if(InputValue <= 7.2) return "Normal";
                else return "High";
            } else if ("F".equalsIgnoreCase(Gender)) {
                if(InputValue < 2.6) return "Low";
                else if(InputValue <= 6) return "Normal";
                else return "High";
            } else {
                return "UNKNOWN GENDER";
            }

        } else if(TestName.equalsIgnoreCase("BUN Test")){
            if(InputValue < 6) return "Low";
            else if(InputValue <= 20) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("AST / SGOT Test")){
            if(InputValue < 0) return "Low";
            else if(InputValue <= 45) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("ALT / SGPT Test")){
            if(InputValue < 0) return "Low";
            else if(InputValue <= 48) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("Sodium Test")){
            if(InputValue < 135) return "Low";
            else if(InputValue <= 145) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("Potassium Test")){
            if(InputValue < 3.5) return "Low";
            else if(InputValue <= 5) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("Chloride Test")){
            if(InputValue < 96) return "Low";
            else if(InputValue <= 110) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("Total Calcium Test")){
            if(InputValue < 8.6) return "Low";
            else if(InputValue <= 10.28) return "Normal";
            else return "High";

        } else if(TestName.equalsIgnoreCase("Ionized Calcium Test")){
            if(InputValue < 4.4) return "Low";
            else if(InputValue <= 5.2) return "Normal";
            else return "High";
        }

        return "RESULT NOT DEFINED";
    }

    @Override
    public String CONVERTTOSI(){
        if(TestName.equalsIgnoreCase("FBS Test") || TestName.equalsIgnoreCase("RBS Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.055);
        } else if(TestName.equalsIgnoreCase("Total Cholesterol") || TestName.equalsIgnoreCase("HDL Test") || TestName.equalsIgnoreCase("LDL Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.026);
        } else if(TestName.equalsIgnoreCase("Triglycerides Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.0114);
        } else if(TestName.equalsIgnoreCase("Creatinine Test")){
            return String.format("%.2f umol/L ", InputValue * 88.4);
        } else if(TestName.equalsIgnoreCase("Uric Acid Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.06);
        } else if(TestName.equalsIgnoreCase("BUN Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.357);
        } else if(TestName.equalsIgnoreCase("AST / SGOT Test") || TestName.equalsIgnoreCase("ALT / SGPT Test")){
            return String.format("%.2f uKat/L ", InputValue * 0.017);
        } else if(TestName.equalsIgnoreCase("Sodium Test") || TestName.equalsIgnoreCase("Potassium Test") || TestName.equalsIgnoreCase("Chloride Test")){
            return String.format("%.2f mmol/L ", InputValue);
        } else if(TestName.equalsIgnoreCase("Total Calcium Test") || TestName.equalsIgnoreCase("Ionized Calcium Test")){
            return String.format("%.2f mmol/L ", InputValue * 0.2495);
        }
        return "";
    }

    @Override
    public double GETCOST(){
        if(TestName.equalsIgnoreCase("FBS Test")) 
            return 180.0;
        else if(TestName.equalsIgnoreCase("RBS Test")) 
            return 180.0;
        else if(TestName.equalsIgnoreCase("Total Cholesterol")) 
            return 300.0;
        else if(TestName.equalsIgnoreCase("HDL Test")) 
            return 350.0;
        else if(TestName.equalsIgnoreCase("LDL Test")) 
            return 350.0;
        else if(TestName.equalsIgnoreCase("Triglycerides Test")) 
            return 300.0;
        else if(TestName.equalsIgnoreCase("Creatinine Test")) 
            return 200.0;
        else if(TestName.equalsIgnoreCase("Uric Acid Test")) 
            return 200.0;
        else if(TestName.equalsIgnoreCase("BUN Test")) 
            return 200.0;
        else if(TestName.equalsIgnoreCase("AST / SGOT Test")) 
            return 300.0;
        else if(TestName.equalsIgnoreCase("ALT / SGPT Test")) 
            return 300.0;
        else if(TestName.equalsIgnoreCase("Sodium Test")) 
            return 325.0;
        else if(TestName.equalsIgnoreCase("Potassium Test")) 
            return 325.0;
        else if(TestName.equalsIgnoreCase("Chloride Test")) 
            return 325.0;
        else if(TestName.equalsIgnoreCase("Total Calcium Test")) 
            return 350.0;
        else if(TestName.equalsIgnoreCase("Ionized Calcium Test")) 
            return 600.0;
        else 
            return 0.0;
    }
}
