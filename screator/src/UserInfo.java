public class UserInfo {
    String Creatorfname;
    static String Creatorlname;
    String CreatorUname;
    String CreatoriD;
    String DateOB;
    String faclty;
    String genDer;
    String emaldress;
    String phonNo;

    //Overloaded Constructors

    //constructor for View User
    public UserInfo (String Creatorfname, String Creatorlname, String CreatorUname, String CreatoriD, String DateOB, String faclty, String genDer, String emaldress, String phonNo) {
          this.Creatorfname = Creatorfname;
          this.Creatorlname = Creatorlname;
          this.CreatorUname = CreatorUname;
          this.CreatoriD = CreatoriD;
          this.DateOB = DateOB;
          this.faclty = faclty;
          this.genDer = genDer;
          this.emaldress = emaldress;
          this.phonNo = phonNo;

    }

    //constructor for Edit User
    public UserInfo (String Creatorfname, String Creatorlname, String CreatorUname, String faclty, String emaldress, String phonNo) {
          this.Creatorfname = Creatorfname;
          this.Creatorlname = Creatorlname;
          this.CreatorUname = CreatorUname;
          this.faclty = faclty;
          this.emaldress = emaldress;
          this.phonNo = phonNo;

    }
    //Constructor to store Username
     public UserInfo ( String CreatorUname) {
          this.CreatorUname = CreatorUname;
     }
    //Getter methods
    public String getCreatorfname() {
        return Creatorfname;
    }

    public String getCreatorlname() {
        return Creatorlname;
    }

    public String getCreatorUname() {
        return CreatorUname;
    }

    public String getCreatoriD() {
        return CreatoriD;
    }

    public String getDateOB() {
        return DateOB;
    }

    public String getfaclty() {
        return faclty;
    }

    public String getgenDer() {
        return genDer;
    }

    public String getemaldress() {
        return emaldress;
    }

    public String getphonNo() {
        return phonNo;
    }

    //Setter methods
    
public void SetCreatorfname(String SCfname) {
        Creatorfname = SCfname;
    }

    public void SetCreatorlname(String SClname) {
        Creatorlname = SClname;
    }

    public void SetCreatorUname(String ScUname) {
        CreatorUname = ScUname;
    }

    public void SetCreatoriD(String SCiD) {
        CreatoriD = SCiD;
    }

    public void SetDateOB(String ScDOB) {
        DateOB = ScDOB;
    }

    public void Setfaclty(String Scfclty) {
        faclty = Scfclty;
    }

    public void SetgenDer(String Scgender) {
        genDer = Scgender;
    }

    public void Setemaldress(String Scemail) {
       emaldress = Scemail;
    }

    public void SetphonNo(String ScphoneNo) {
        phonNo = ScphoneNo;
    }

}

