package sp2018project_padilla;

import java.util.ArrayList;
import java.util.List;

/**
 * @author francisco
 */
public class SP2018PROJECT_Course_Padilla 
{    
    private String classCode;
    private String semester;
    private int year;
    private String courseLetters;
    private int courseNumber;
    private List<SP2018PROJECT_Enrollment_Padilla> students = new ArrayList<SP2018PROJECT_Enrollment_Padilla>();  
    
    public SP2018PROJECT_Course_Padilla(String classCode, List<SP2018PROJECT_Enrollment_Padilla> students) 
    {
        this.classCode = classCode;
        this.students = students;
    }//end of class constructor

    public String getClassCode() 
    {
        return classCode;
    }//end of getClassCode

    public void setClassCode(String classCode) 
    {
        this.classCode = classCode;
    }//end of setClassCode

    public List<SP2018PROJECT_Enrollment_Padilla> getStudents() {
        return students;
    }

    public void setStudents(List<SP2018PROJECT_Enrollment_Padilla> students) {
        this.students = students;
    }
    
    public void addEnrollment(SP2018PROJECT_Enrollment_Padilla dummy){
        students.add(dummy);
        this.setStudents(students);
    }

    public String getSemester() {
        return this.getClassCode().substring(0, 2);
    }

    public int getYear() {
        return Integer.parseInt(this.getClassCode().substring(2, 6));
    }

    public String getCourseLetters() {
        return this.getClassCode().substring(7, 11);
    }

    public int getCourseNumber() {
        return Integer.parseInt(this.getClassCode().substring(11, 15));
    }
        
    public SP2018PROJECT_Course_Padilla deepCopy()
    {
        SP2018PROJECT_Course_Padilla clone = new SP2018PROJECT_Course_Padilla
        (this.classCode, this.students);
        return clone;
    }//end of course deepCopy
    
       public String getKey()
    {
        return classCode;
    }
       
    public int compareTo(String targetKey)
   {  
       return(this.classCode.compareTo(targetKey));
   }

    @Override
    public String toString() {
        return this.getClassCode();
    }   
    
}//end of class course
