import java.util.*;

public class P_PATIENTniRey {
    private static int ID = 1000;
    private String PatientID;
    private String Name;
    private int Age;
    private String Gender;
    private List<P_LABTESTniRey> Tests;
    private boolean PWD;

    public P_PATIENTniRey(String Name, int Age, String Gender, boolean PWD){
        ID = ID + 1;
        this.PatientID = String.valueOf(ID);
        this.Name = Name;
        this.Age = Age;
        this.Gender = Gender;
        this.Tests = new ArrayList<>();
        this.PWD = PWD;
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
    public boolean isPWD() {
    return PWD;
    }
    public List<P_LABTESTniRey> GETTESTS(){
        return Tests;
    }
    public void ADDTEST(P_LABTESTniRey Test){
        Tests.add(Test);
    }
}
