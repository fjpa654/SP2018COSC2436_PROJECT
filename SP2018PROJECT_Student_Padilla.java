package sp2018project_padilla;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author francisco
 */
public class SP2018PROJECT_Student_Padilla
{
    private String Id;
    private String lastName;
    private String firstName;
    private String socialSecurity;
    private String dateOfBirth;
    private String phone;
    private String address;
    private List<SP2018PROJECT_Enrollment_Padilla> enrollments = new ArrayList<SP2018PROJECT_Enrollment_Padilla>();

    public SP2018PROJECT_Student_Padilla
               (String Id, String lastName, 
                String firstName, String socialSecurity, 
                String dateOfBirth, String phone, 
                String address,List<SP2018PROJECT_Enrollment_Padilla> enrollments) 
    {
        this.Id = Id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.socialSecurity = socialSecurity;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.enrollments = enrollments;
    }//end of Student constructor
 
    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
        public String getKey()
    {
        return Id;
    }

    public List<SP2018PROJECT_Enrollment_Padilla> getEnrollments()
    {   
        return  enrollments;
    }

    public void setEnrollments(List<SP2018PROJECT_Enrollment_Padilla> enrollments) {
        this.enrollments = enrollments;
    }
    
    public void addEnrollment(SP2018PROJECT_Enrollment_Padilla dummy)
    {
        enrollments.add(dummy);
        this.setEnrollments(enrollments);
    }
               
        public SP2018PROJECT_Student_Padilla deepCopy()
    {
        SP2018PROJECT_Student_Padilla clone = new SP2018PROJECT_Student_Padilla
        (this.Id, this.lastName, this.firstName, this.socialSecurity, 
                this.dateOfBirth, this.phone, this.address, this.enrollments);
        return clone;
    }
        
        public int compareTo(String targetKey)
   {  
       return(this.Id.compareTo(targetKey));
   }
        
        @Override
    public String toString() 
    {
        return 
                "\nID:          "+this.getId()+
                "\nLast Name:   "+this.getLastName()+
                "\nFirst Name:  "+this.getFirstName()+
                "\nSS:          "+this.getSocialSecurity()+
                "\nBirthday:    "+this.getDateOfBirth()+
                "\nPhone:       "+this.getPhone()+
                "\nAddress:     "+this.getAddress();
    }
}
