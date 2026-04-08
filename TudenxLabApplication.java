import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TudenxLabApplication {
    private static Scanner Input = new Scanner(System.in);
    private static List<P_PATIENTniRey> Patients = new ArrayList<>();

    public static void main(String[] args){
        boolean Run = true;
        while (Run) {
            System.out.println();
            System.out.println("====================================================================");
            System.out.println("                      === CURRENT PATIENTS ===");
            if (Patients.isEmpty()){
                System.out.println("====================================================================");
                System.out.println("                      NO PATIENTS ADDED YET....");
                System.out.println("====================================================================");
            } else {
                for(P_PATIENTniRey Pat : Patients){
                    System.out.println("====================================================================");
                    System.out.println("ID: " + Pat.GETPATIENTID() +
                    " | Name: " + Pat.GETNAME() +
                    " | Age: " + Pat.GETAGE() +
                    " | Gender: " + Pat.GETGENDER());
                    System.out.println("====================================================================");

                }
                System.out.println("Total Patients: " + Patients.size());
                System.out.println("====================================================================");
            }
            System.out.println();
            System.out.println("    === MAIN MENU ===");
            System.out.println("    1. Add Patient");
            System.out.println("    2. Remove Patient");
            System.out.println("    3. Laboratory Menu");
            System.out.println("    4. Process Results");
            System.out.println("    5. Exit");
            System.out.println();
            System.out.print("Enter Choice: ");
            int Choice = Input.nextInt();
            Input.nextLine();
            
            if(Choice == 1) 
                ADDPATIENT();
            else if(Choice == 2)
                REMOVEPATIENT();
            else if(Choice == 3)
                LABORATORYMENU();
            else if(Choice == 4)
                PROCESSRESULTS();
            else if(Choice == 5)
                Run = false;
            else
                continue;
        }
        Input.close();
    }

    private static void ADDPATIENT(){
        System.out.println();
        System.out.print("Enter Patient Name: ");
        String Name = Input.nextLine();

        System.out.println();
        System.out.print("Enter Age: ");
        int Age = Input.nextInt();
        Input.nextLine();

        System.out.println();
        System.out.print("Enter Gender (M/F): ");
        String Gender = Input.nextLine();

        P_PATIENTniRey P = new P_PATIENTniRey(Name, Age, Gender);
        Patients.add(P);
        System.out.println();
        System.out.println("Patient Added With ID: " + P.GETPATIENTID());
    }

    private static void REMOVEPATIENT(){
        System.out.println();
        System.out.print("Enter Patient ID to Remove: ");
        String ID = Input.nextLine();
        Patients.removeIf(P -> P.GETPATIENTID().equals(ID));
        System.out.println();
        System.out.println("PATIENT REMOVED IF ID EXISTED");
    }

    private static void PROCESSRESULTS(){
        System.out.println();
        System.out.print("Enter Patient ID to View Results: ");
        String ID = Input.nextLine();
        P_PATIENTniRey P = FINDPATIENTBYID(ID);

        if(P == null){
            System.out.println("PATIENT NOT FOUND");
            return;
        }
        System.out.println();
        System.out.println("Processing results... please wait 10 seconds.");
        try {
            Thread.sleep(10000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LocalDateTime ResultAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd | HH:mm");

        System.out.println("============================================================================================================");
        System.out.println("RESULTS FOR PATIENT: " + P.GETNAME() + " (ID: " + P.GETPATIENTID() + ")");

        Map<String, List<String>> GROUPEDRESULTS = new HashMap<>();
        double totalCost = 0.0;

        for(P_LABTESTniRey Test : P.GETTESTS()){
            String Category;
            if(Test instanceof S_CLINICALMICROSCOPYniRey)
                Category = "MICROSCOPY";
            else if(Test instanceof S_MICROBIOLOGYniRey)
                Category = "MICROBIOLOGY";
            else if(Test instanceof S_MOLECULARPCRniRey)
                Category = "MOLECULAR";
            else if(Test instanceof S_CLINICALCHEMISTRYniRey)
                Category = "Clinical Chemistry";
            else
                Category = "OTHER"; 

            GROUPEDRESULTS.putIfAbsent(Category, new ArrayList<>());

            double cost = GETTESTCOST(Test.TestName);
            totalCost += cost;

            GROUPEDRESULTS.get(Category).add( 
                Test.TestName + " -> " + Test.EVALUATERESULT() +
                " | " + Test.InputValue + " " + Test.Unit +
                (Test.CONVERTTOSI().isEmpty() ? "" : " | " + Test.CONVERTTOSI()) +
                " | Taken: " + Test.GETTAKENAT().format(formatter) +
                " | Result Ready: " + ResultAt.format(formatter) +
                " | Cost: PhP " + String.format("%.2f", cost)
            );
        }

        for (Map.Entry<String, List<String>> entry : GROUPEDRESULTS.entrySet()) {
            System.out.println("============================================================================================================");
            System.out.println("===" + entry.getKey() + "===");
            System.out.println("------------------------------------------------------------------------------------------------------------");
            for (String result : entry.getValue()) {
                System.out.println(result);
            }
        }
        System.out.println("============================================================================================================");
        System.out.println("TOTAL COST: PhP " + String.format("%.2f", totalCost));
        System.out.println("============================================================================================================");
    }

    private static double GETTESTCOST(String testName){

        if(testName.equalsIgnoreCase("Urinalysis - Protein")) 
            return 200;  
        else if(testName.equalsIgnoreCase("Fat Test")) 
            return 600;        
        else if(testName.equalsIgnoreCase("Pregnancy Test")) 
            return 250;   
        else if(testName.equalsIgnoreCase("Urinalysis - Blood")) 
            return 200;

        else if(testName.equalsIgnoreCase("Urine Culture")) 
            return 900;   
        else if(testName.equalsIgnoreCase("Sputum Culture")) 
            return 1000;  
        else if(testName.equalsIgnoreCase("Throat Swab Culture")) 
            return 950;
        else if(testName.equalsIgnoreCase("Wound Culture")) 
            return 1150;   

        else if(testName.equalsIgnoreCase("COVID-19 RT-PCR")) 
            return 2500;  
        else if(testName.equalsIgnoreCase("HIV Viral Load")) 
            return 6000;   
        else if(testName.equalsIgnoreCase("Hepatitis B DNA")) 
            return 5000;  
        else if(testName.equalsIgnoreCase("HPV DNA")) 
            return 4500;         

        else if(testName.equalsIgnoreCase("FBS Test")) 
            return 180;        
        else if(testName.equalsIgnoreCase("RBS Test")) 
            return 180;
        else if(testName.equalsIgnoreCase("Total Cholesterol")) 
            return 300; 
        else if(testName.equalsIgnoreCase("HDL Test")) 
            return 350;          
        else if(testName.equalsIgnoreCase("LDL Test")) 
            return 350;
        else if(testName.equalsIgnoreCase("Triglycerides Test")) 
            return 300;
        else if(testName.equalsIgnoreCase("Creatinine Test")) 
            return 200;   
        else if(testName.equalsIgnoreCase("Uric Acid Test")) 
            return 200;
        else if(testName.equalsIgnoreCase("BUN Test")) 
            return 200;
        else if(testName.equalsIgnoreCase("AST / SGOT Test")) 
            return 300;   
        else if(testName.equalsIgnoreCase("ALT / SGPT Test")) 
            return 300;
        else if(testName.equalsIgnoreCase("Sodium Test")) 
            return 325;       
        else if(testName.equalsIgnoreCase("Potassium Test")) 
            return 325;
        else if(testName.equalsIgnoreCase("Chloride Test")) 
            return 325;
        else if(testName.equalsIgnoreCase("Total Calcium Test")) 
            return 350;
        else if(testName.equalsIgnoreCase("Ionized Calcium Test")) 
            return 600;

        else return 0.0;
    }

        private static P_PATIENTniRey FINDPATIENTBYID(String ID){
            for(P_PATIENTniRey P : Patients){
                if(P.GETPATIENTID().equals(ID))
                    return P;
            }
            return null;
        }

        private static void LABORATORYMENU(){
            System.out.println();
            System.out.print("Enter Patient ID: ");
            String ID = Input.nextLine();
            P_PATIENTniRey P = FINDPATIENTBYID(ID);

            if(P == null){
                System.out.println("PATIENT NOT FOUND");
                return;
            }

            boolean Run = true;
            while(Run){
                System.out.println();
                System.out.println("    === LABORATORY MENU ===");
                System.out.println("    1. Clinical Microscopy");
                System.out.println("    2. Microbiology");
                System.out.println("    3. Molecular / PCR");
                System.out.println("    4. Clinical Chemistry");
                System.out.println("    5. Back to Main Menu");
                System.out.println();
                System.out.print("Enter Choice: ");
                int Choice = Input.nextInt();
                Input.nextLine();

                if(Choice == 1) 
                    CLINICALMICROSCOPYMENU(P);
                else if(Choice == 2) 
                    MICROBIOLOGYMENU(P);
                else if(Choice == 3) 
                    MOLECULARMENU(P);
                else if(Choice == 4) 
                    CHEMISTRYMENU(P);
                else if(Choice == 5) 
                    Run = false;
                else 
                    continue;
            }
        }

        private static void CLINICALMICROSCOPYMENU(P_PATIENTniRey P){
            boolean Run = true;
            while(Run){
                System.out.println();
                System.out.println("    === CLINICAL MICROSCOPY TESTS ===");
                System.out.println("    1. Urinalysis - Protein");
                System.out.println("    2. Fat Test");
                if(P.GETGENDER().equalsIgnoreCase("F"))
                    System.out.println("    3. Pregnancy Test");
                System.out.println("    4. Urinalysis - Blood");
                System.out.println("    5. Back to Laboratory Menu");
                System.out.println();
                System.out.print("Enter Choice: ");
                int Choice = Input.nextInt();
                Input.nextLine();

                if(Choice == 1){
                    System.out.println();
                    System.out.print("Enter Protein (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALMICROSCOPYniRey("Urinalysis - Protein", Value, "mg/dL"));

                } else if(Choice == 2){
                    System.out.println();
                    System.out.print("Enter Fat %: ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALMICROSCOPYniRey("Fat Test", Value, "%"));

                } else if(Choice == 3 && P.GETGENDER().equalsIgnoreCase("F")){
                    System.out.println();
                    System.out.print("Enter hCG (mIU/mL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALMICROSCOPYniRey("Pregnancy Test", Value, "mIU/mL"));

                } else if(Choice == 4){
                    System.out.println();
                    System.out.print("Enter RBC/HPF: ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALMICROSCOPYniRey("Urinalysis - Blood", Value, "RBC/HPF"));

                } else if(Choice == 5){
                    Run = false;

                }else 
                    continue;
            }
        }

        private static void MICROBIOLOGYMENU(P_PATIENTniRey P){
            boolean Run = true;
            while(Run){
                System.out.println();
                System.out.println("    === MICROBIOLOGY TESTS ===");
                System.out.println("    1. Urine Culture");
                System.out.println("    2. Sputum Culture");
                System.out.println("    3. Throat Swab Culture");
                System.out.println("    4. Wound Culture");
                System.out.println("    5. Back to Laboratory Menu");
                System.out.println();
                System.out.print("Enter Choice: ");
                int Choice = Input.nextInt();
                Input.nextLine();

                if(Choice >= 1 && Choice <= 4){
                    System.out.println();
                    System.out.print("Enter CFU/mL: ");
                    double Value = Input.nextDouble();
                    String TestName = (Choice == 1) ? "Urine Culture" :
                                    (Choice == 2) ? "Sputum Culture" :
                                    (Choice == 3) ? "Throat Swab Culture" : "Wound Culture";
                    P.ADDTEST(new S_MICROBIOLOGYniRey(TestName, Value, "CFU/mL"));

                } else if(Choice == 5){
                    Run = false;

                }else 
                    continue;
            }
        }

        private static void MOLECULARMENU(P_PATIENTniRey P){
            boolean Run = true;
            while(Run){
                System.out.println();
                System.out.println("    === MOLECULAR / PCR TESTS ===");
                System.out.println("    1. COVID-19 RT-PCR");
                System.out.println("    2. HIV Viral Load");
                System.out.println("    3. Hepatitis B DNA");
                System.out.println("    4. HPV DNA");
                System.out.println("    5. Back to Laboratory Menu");
                System.out.println();
                System.out.print("Enter Choice: ");
                int Choice = Input.nextInt();
                Input.nextLine();

                if(Choice == 1){
                    System.out.println();
                    System.out.print("Enter Ct Value: ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_MOLECULARPCRniRey("COVID-19 RT-PCR", Value, "Ct"));

                } else if(Choice == 2){
                    System.out.println();
                    System.out.print("Enter HIV Viral Load (copies/mL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_MOLECULARPCRniRey("HIV Viral Load", Value, "copies/mL"));

                } else if(Choice == 3){
                    System.out.println();
                    System.out.print("Enter Hepatitis B DNA (IU/mL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_MOLECULARPCRniRey("Hepatitis B DNA", Value, "IU/mL"));

                } else if(Choice == 4){
                    System.out.println();
                    System.out.print("Enter HPV DNA (copies/mL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_MOLECULARPCRniRey("HPV DNA", Value, "copies/mL"));

                } else if(Choice == 5){
                    Run = false;

                }else
                    continue;
            }
        }

        private static void CHEMISTRYMENU(P_PATIENTniRey P){
            boolean Run = true;
            while(Run){
                System.out.println("    === CLINICAL CHEMISTRY TESTS ===");
                System.out.println("    1. FBS Test");
                System.out.println("    2. RBS Test");
                System.out.println("    3. Total Cholesterol Test");
                System.out.println("    4. HDL Test");
                System.out.println("    5. LDL Test");
                System.out.println("    6. Triglycerides Test");
                System.out.println("    7. Creatinine Test");
                System.out.println("    8. Uric Acid Test");
                System.out.println("    9. BUN Test");
                System.out.println("    10. AST Test");
                System.out.println("    11. ALT Test");
                System.out.println("    12. Sodium Test");
                System.out.println("    13. Potassium Test");
                System.out.println("    14. Chloride Test");
                System.out.println("    15. Total Calcium Test");
                System.out.println("    16. Ionized Calcium Test");
                System.out.println("    17. Back to Laboratory Menu");
                System.out.println();
                System.out.print("Enter Choice: ");
                int Choice = Input.nextInt();
                Input.nextLine();

                if(Choice == 1){
                    System.out.println();
                    System.out.print("Enter FBS (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("FBS Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 2){
                    System.out.println();
                    System.out.print("Enter RBS (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("RBS Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 3){
                    System.out.println();
                    System.out.print("Enter Total Cholesterol (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Total Cholesterol", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 4){
                    System.out.println();
                    System.out.print("Enter HDL (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("HDL Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 5){
                    System.out.println();
                    System.out.print("Enter LDL (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("LDL Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 6){
                    System.out.println();
                    System.out.print("Enter Triglycerides (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Triglycerides Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 7){
                    System.out.println();
                    System.out.print("Enter Creatinine (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Creatinine Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 8){
                    System.out.println();
                    System.out.print("Enter Uric Acid (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Uric Acid Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 9){
                    System.out.println();
                    System.out.print("Enter BUN (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("BUN Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 10){
                    System.out.println();
                    System.out.print("Enter AST (U/L): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("AST / SGOT Test", Value, "U/L", P.GETGENDER()));

                } else if(Choice == 11){
                    System.out.println();
                    System.out.print("Enter ALT (U/L): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("ALT / SGPT Test", Value, "U/L", P.GETGENDER()));

                } else if(Choice == 12){
                    System.out.println();
                    System.out.print("Enter Sodium (mEq/L): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Sodium Test", Value, "mEq/L", P.GETGENDER()));

                } else if(Choice == 13){
                    System.out.println();
                    System.out.print("Enter Potassium (mEq/L): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Potassium Test", Value, "mEq/L", P.GETGENDER()));

                } else if(Choice == 14){
                    System.out.println();
                    System.out.print("Enter Chloride (mEq/L): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Chloride Test", Value, "mEq/L", P.GETGENDER()));

                } else if(Choice == 15){
                    System.out.println();
                    System.out.print("Enter Total Calcium (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Total Calcium Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 16){
                    System.out.println();
                    System.out.print("Enter Ionized Calcium (mg/dL): ");
                    double Value = Input.nextDouble();
                    P.ADDTEST(new S_CLINICALCHEMISTRYniRey("Ionized Calcium Test", Value, "mg/dL", P.GETGENDER()));

                } else if(Choice == 17){
                    Run = false;
                
                }else
                    continue;
            }
        }
    }
