import java.util.*;

public class P_PATIENTniRey {
    private static int ID = 1000;
    private String PatientID;
    private String Name;
    private int Age;
    private String Gender;
    private List<P_LABTESTniRey> Tests;

    public P_PATIENTniRey(String Name, int Age, String Gender){
        ID = ID + 1;
        this.PatientID = String.valueOf(ID);
        this.Name = Name;
        this.Age = Age;
        this.Gender = Gender;
        this.Tests = new ArrayList<>();
    }

    public String GETPATIENTID(){
        return PatientID;
    }
    public String GETNAME(){
        return Name;
    }
    public int GETAGE(){
        return Age;
    }
    public String GETGENDER(){
        return Gender;
    }
    public List<P_LABTESTniRey> GETTESTS(){
        return Tests;
    }
    public void ADDTEST(P_LABTESTniRey Test){
        Tests.add(Test);
    }

    public void PROCESSTESTS(){
        System.out.println("Patient: " + Name + " (ID: " + PatientID + ")");
        if(Tests.isEmpty()){
            System.out.println("NO RECORDED TESTS");
            return;
        }
        for(P_LABTESTniRey Test : Tests){
            Test.DISPLAYRESULT();
        }
    }
}
