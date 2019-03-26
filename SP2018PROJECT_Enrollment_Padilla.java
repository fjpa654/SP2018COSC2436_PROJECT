package sp2018project_padilla;
/**
 * @author francisco
 */
public class SP2018PROJECT_Enrollment_Padilla
{   private String studentId;
    private String code;
    private String grade;
    private String notPass = "X";

    public SP2018PROJECT_Enrollment_Padilla
        (String studentId, String code, String grade) 
    {
        this.code = code;
        this.studentId = studentId;
        this.grade = grade;
    }

    public String getCode() 
    {
        return code;
    }

    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getStudentId() 
    {
        return studentId;
    }

    public void setStudentId(String studentId) 
    {
        this.studentId = studentId;
        
    }

    public String getGrade() 
    {      
        if(grade.equalsIgnoreCase(this.notPass))
        {
           return this.grade = "Not Complete";
        }
        else
        {
            return this.grade = grade;
        }
    }

    public void setGrade(String grade) 
    {      
            this.grade = grade;  
    }

       public String getKey()
    {
        return this.getStudentId()+this.getCode();
    }
       
    public int compareTo(String targetKey)
    {  
       return(this.studentId.compareTo(targetKey));
    }
    
        public SP2018PROJECT_Enrollment_Padilla deepCopy()
    {
        SP2018PROJECT_Enrollment_Padilla clone = 
                new SP2018PROJECT_Enrollment_Padilla
        (this.studentId, this.code, this.grade);
        return clone;
    }//end of course deepCopy
        
    @Override
    public String toString() 
    {
        return this.getCode()+" "+this.getGrade();
                
    }
}