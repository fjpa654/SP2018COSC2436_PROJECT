package sp2018project_padilla;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author francisco
 */
public class SP2018PROJECT_StudentService 
{
    public static void main(String[] args) throws IOException 
    {
        Scanner input = new Scanner(System.in);
        
        String id,course;
        String grade = "X";
        int option;
        boolean exit = false;
        
        //Initializing all the structures
            //structure for students objects
        SP2018PROJECT_LQHashedStudent_Padilla studentsInfoLQHash = 
                new SP2018PROJECT_LQHashedStudent_Padilla(100);
            //structure for couses objects
        SP2018PROJECT_LQHashedCourses_Padilla coursesListLQHash = 
                new SP2018PROJECT_LQHashedCourses_Padilla(100);
            //structure for enrollments object
        SP2018PROJECT_LQHashedEnrollment_Padilla enrollmentInfoLQHash = 
                new SP2018PROJECT_LQHashedEnrollment_Padilla(350);
        
        /*Fill all the structures with objects made from the files*/
            //fill with  students
        studentsInfoLQHash = importStudents();
            //fill with all the possible classes
        coursesListLQHash = importCourses(); 
            //fill with enrollments
        enrollmentInfoLQHash = importEnrollments();
        
        /*The follwoing code will populate the list of objects type enrollment
        for each student. Each Student object holds a array list type enrolment
        that holds all the classes in wich the students has been enrolled*/
        
        /*In the following for loop the program will get all the objects of 
        the hash strucuters of the students info in the form of an array.
        The array may contain fields with student objects or empty fields 
        represented as 'null'. Since each students has there own array list
        type enrolement we will declare a dummy array list type enrollment that 
        will be assigned the the corresponding student.*/
        for(int s =0; s < studentsInfoLQHash.getAll().length; s++)
        {
            // dummy arrayList type enrollment.
            List<SP2018PROJECT_Enrollment_Padilla> dummyArrayOfEnrollments =
                    new ArrayList<SP2018PROJECT_Enrollment_Padilla>();
            
            //if the position 's' is not null, then contains an object type Student.
            if(studentsInfoLQHash.getAll()[s] != null)
            {
                /*Here the program will get all the elements of the hash table type enrollment
                in the form of an array, and we will look into each field of the array.
                this array cotains either null values or objects type enrollment*/
                for(int e = 0; e < enrollmentInfoLQHash.getAll().length; e++)
                {
                    //if the position 'e' of the array is not null, then cotains an object type enrollment 
                    if(enrollmentInfoLQHash.getAll()[e] != null)
                    {
                        /*Since the object student contains an attribute type ID aswell as 
                        the object enrollment, we will proceed to compare both these attributes.*/
                        
                        /*if the ID of the object 'STUDENTS' is equal as the student ID of the object 'ENROLLMENT' 
                        Then the student was enrolled in a class. The object ENROLLMENT ontains the details*/
                        if(studentsInfoLQHash.getAll()[s].getId().equals(enrollmentInfoLQHash.getAll()[e].getStudentId()))
                       {
                           /*since the previous statement is true, the program will now add 
                           the object in position 'e' to the dummy array*/
                           dummyArrayOfEnrollments.add(enrollmentInfoLQHash.getAll()[e]); 
                       }//end of comparison between ID of student object and studentID of enrollment.
                    }//the program has evaluated if the element in position 'e' of the array retrieved from the enrollmentInfoLQHash is not null
                }//the program has gone through every field of the array retrieved from the enrolmentInfoLQHash
                
                //set the dummyArrayOfEnrollments as the enrollments of object on position 's'
                studentsInfoLQHash.getAll()[s].setEnrollments(dummyArrayOfEnrollments);   
                
                //declare a dummyStudent to replace the student without enrolments in the students hash table
                SP2018PROJECT_Student_Padilla dummyStudent = studentsInfoLQHash.getAll()[s];
                
               //update the hash table with the new student
                studentsInfoLQHash.update(dummyStudent.getId(), dummyStudent);
           }//the program has evaluated if the element in position 's' of the array retrived from the hash table of students is not null         
        }//The program has gone through every position on the array retrived from the hash table of students
        
        
        for(int c = 0; c < onlyCoursesArray(coursesListLQHash.getAll()).length;c++)
        {
            // dummy arrayList type enrollment.
            List<SP2018PROJECT_Enrollment_Padilla> dummyArrayOfEnrollments =
                    new ArrayList<SP2018PROJECT_Enrollment_Padilla>();
            
                for(int e = 0; e < onlyEnrollmentsArray(enrollmentInfoLQHash.getAll()).length;e++ )
                {
                    if(onlyCoursesArray(coursesListLQHash.getAll())[c].getClassCode().equals(onlyEnrollmentsArray(enrollmentInfoLQHash.getAll())[e].getCode()))
                    {
                        dummyArrayOfEnrollments.add(onlyEnrollmentsArray(enrollmentInfoLQHash.getAll())[e]);
                    }
                }
            //set dummy array to the course at position 'c'
            onlyCoursesArray(coursesListLQHash.getAll())[c].setStudents(dummyArrayOfEnrollments);
            
            //declare dummy course
            SP2018PROJECT_Course_Padilla dummyCourse = onlyCoursesArray(coursesListLQHash.getAll())[c];
            
            coursesListLQHash.update(dummyCourse.getClassCode(), dummyCourse);
            
        }
        
        while(exit != true)
        {
            
        menu1();//flash the menu on the screen
        option = input.nextInt();//user inputs an integer to choose from the menu
        
            switch (option)
            {
                case 0:
                    {
                        System.out.print("\n*****EXIT*****");
                        exit = true;
                    break;
                    }

                case 1://add student
                    {
                    System.out.print("\n*****ADD ONE NEW STUDENT*****");
                    studentsInfoLQHash.insert(addStudent(onlyStudentsArray(studentsInfoLQHash.getAll())));      
                    break;
                    }//and of case 1 add student

                case 2://remove a student
                    {
                    System.out.print("\n*****REMOVE ONE STUDENT*****");
                    System.out.print("\nPlease Enter the ID of the student that you would like to remove:");
                            for(int i = 0; i < onlyStudentsArray(studentsInfoLQHash.getAll()).length; i++)
                            {
                                if ( i%5 == 0)//new line every 5th item is output.
                                {
                                    System.out.print("\n");
                                }
                                System.out.print(onlyStudentsArray(studentsInfoLQHash.getAll())[i].getId()+" ");
                            }
                            System.out.print("\nStudent ID:\t");
                            id=input.next();
                            if(studentsInfoLQHash.fetch(id) == null)
                            {
                                System.out.print("No student found in structure with such id: "+ id+"\n");
                            }
                            else
                            {
                                studentsInfoLQHash.delete(id);
                                System.out.print("Student removed succesfully!\n**********\n");
                            }         
                    break;
                    }//end of remove student

                case 3://find student
                    {
                    id = null;
                    System.out.print("\n*****FIND A STUDENT BY ID*****");
                    System.out.print("\nPlease Enter the ID of the student that you would like to find:");
                            for(int i = 0; i < onlyStudentsArray(studentsInfoLQHash.getAll()).length; i++)
                            {
                                if ( i%5 == 0)//new line every 5th item is output.
                                {
                                    System.out.print("\n");
                                }
                                System.out.print(onlyStudentsArray(studentsInfoLQHash.getAll())[i].getId()+" ");
                            }
                            System.out.print("\nStudent ID:\t");
                            id=input.next();
                            if(studentsInfoLQHash.fetch(id) == null)
                            {
                                System.out.print("No student found in structure with such id: "+ id+"\n");
                            }
                            else
                            {
                                System.out.print(studentsInfoLQHash.fetch(id)+"\n**********\n");
                            }
                    break;
                    }//end of case 3: find student

                case 4://add a class for a student
                    {
                    //create a dummyEnrollment object to update all the structures.   
                    boolean endWhile =false;
                    boolean exit4 = false;
                    id = null;
                    course = null;
                    SP2018PROJECT_Enrollment_Padilla dummyEnrollment = new SP2018PROJECT_Enrollment_Padilla(id, course, "X");
                    System.out.print("\n*****ADD CLASS FOR ONE STUDENT*****");
                        do//do while the id is valid
                        {
                            System.out.print("\nPlease Enter one of the following ID's:");

                        //For loop shoows all the available options for the user.
                        //if an invalid ID is enter loop continues
                        //if "x" or valid id enter loop ends.
                            for(int i = 0; i < onlyStudentsArray(studentsInfoLQHash.getAll()).length; i++)
                            {
                                if ( i%5 == 0)//new line every 5th item is output.
                                {
                                    System.out.print("\n");
                                }
                                System.out.print(onlyStudentsArray(studentsInfoLQHash.getAll())[i].getId()+" ");
                            }
                            System.out.print("\n\n");

                        id = input.next().toUpperCase();//Id is initialize with capital letter

                             if (id.equalsIgnoreCase("X"))
                                { 
                                    endWhile =true;//user wants to exit case 4
                                }
                             else if (studentsInfoLQHash.fetch(id) == null)
                                {
                                 System.out.print("There is no student in the Strucutre with id\nor enter 'X' to exit: "+id);
                                }//end of validatio

                             else
                                {
                                    endWhile =true;
                                }

                        }while(endWhile == false);//end of id validation
                            if (id.equalsIgnoreCase("X"))
                            { 
                                break;
                            }//if the user enter X insed the do while loop exit the case 4

                        endWhile = false;//re-using flag for the next do while loop

                     sortCourses(onlyCoursesArray(coursesListLQHash.getAll()));

                        do{                       
                            course = null;    
                            System.out.print("\nPlease Select one of the following courses\nor enter 'X' to exit: ");
                            /*do the follwing code to validate the course code.
                            if the course input by the user equals to "x" end of  do while loop*/
                            for (int i= 0; i < onlyCoursesArray(coursesListLQHash.getAll()).length; i++)
                            {    
                                if(i%5 == 0)
                                    {
                                        System.out.print("\n");
                                    }
                                    System.out.print(sortCourses(onlyCoursesArray(coursesListLQHash.getAll()))[i] +" ");
                            }                        
                            System.out.print("\n");

                            course = input.next().toUpperCase();
                            if(course.equalsIgnoreCase("X"))
                                {                           
                                    endWhile = true;
                                }
                            else if(coursesListLQHash.fetch(course)== null)//no course exist in the structure
                                {
                                    System.out.print("\nThis course does not exist in the structure: "+course);
                                }
                            else if(coursesListLQHash.fetch(course)!= null)//course is found in the structure!
                            {
                                endWhile = true;//get ready to exit the while loop
                                for(int i = 0; i<studentsInfoLQHash.fetch(id).getEnrollments().size();i++)//run through the enrollment structure
                                {   //compare each enrolment Code with the course
                                    //if is equal then the  student is already enrolled in this course
                                   if(studentsInfoLQHash.fetch(id).getEnrollments().get(i).getCode().equalsIgnoreCase(course)) 
                                    {                                
                                        System.out.println("The Student is allready Enrolled in this class");   
                                        endWhile = false;//the while loop continues
                                    }
                                }//end of for
                            }//end of else if
                        }while(endWhile != true/*coursesListLQHash.fetch(course) != null  &&  elementFound == null*/);

                            if(course.equalsIgnoreCase("X")||exit4)
                            {
                                break;
                            }    
                    dummyEnrollment = new SP2018PROJECT_Enrollment_Padilla(id, course, "X");

                    //add the new enrollment object to the hash structure  

                    //dummy student to update the student hash table structure
                    SP2018PROJECT_Student_Padilla dummyStudent = studentsInfoLQHash.fetch(id);
                    //add the new enrolment to the student's list of classes
                    dummyStudent.addEnrollment(dummyEnrollment);
                    //update the student hashtable replacing the old student with the updated dummy

                    //dummy course to update the course hash table structure
                    SP2018PROJECT_Course_Padilla dummyCourse = coursesListLQHash.fetch(course);
                    //add the new enrollment to the course's list of student
                    dummyCourse.addEnrollment(dummyEnrollment);
                    //update the course hashtable replacing the old course with the updated dummy course

                    if(coursesListLQHash.update(course, dummyCourse)
                    && studentsInfoLQHash.update(id, dummyStudent)
                    && enrollmentInfoLQHash.insert(dummyEnrollment))
                    {
                        System.out.print("\nClass added succesfully!");
                    }
                    else
                    {
                        System.out.print("\nClass added unsuccesfully.");
                        if(!coursesListLQHash.update(course, dummyCourse))
                        {
                            System.out.print("\nFAIL to update COURSES LQHASH table");
                        }
                        if(!studentsInfoLQHash.update(id, dummyStudent))
                        {
                            System.out.print("\nFAIL to update STUDENTS LQHASH table");
                        }
                        if(enrollmentInfoLQHash.insert(dummyEnrollment))
                        {
                            System.out.print("\nFAIL to update ENROLLMENT LQHASH table");
                        }
                    }

                    break;
                    }//end of case 4: add class for a student

                case 5://Drop a class for a student
                    {

                    //create a dummyEnrollment object to update all the structures.   
                    boolean endWhile =false;
                    boolean exit5 = false;
                    id = null;
                    course = null;
                    System.out.print("\n*****DROP CLASS FOR ONE STUDENT*****");
                        do//do while the id is valid
                        {
                            System.out.print("\nPlease Enter one of the following ID's:");

                        //For loop shoows all the available options for the user.
                        //if an invalid ID is enter loop continues
                        //if "x" or valid id enter loop ends.
                            for(int i = 0; i < onlyStudentsArray(studentsInfoLQHash.getAll()).length; i++)
                            {
                                if ( i%5 == 0)//new line every 5th item is output.
                                {
                                    System.out.print("\n");
                                }
                                System.out.print(onlyStudentsArray(studentsInfoLQHash.getAll())[i].getId()+" ");
                            }
                            System.out.print("\n\n");

                        id = input.next().toUpperCase();//Id is initialize with capital letter

                             if (id.equalsIgnoreCase("X"))
                                { 
                                    endWhile =true;//user wants to exit case 4
                                }
                             else if (studentsInfoLQHash.fetch(id) == null)
                                {
                                 System.out.print("There is no student in the Strucutre with id\nor enter 'X' to exit: "+id);
                                }//end of validatio

                             else
                                {
                                    endWhile =true;
                                }

                        }while(endWhile == false);//end of id validation
                            if (id.equalsIgnoreCase("X"))
                            { 
                                break;
                            }//if the user enter X insed the do while loop exit the case 4

                        endWhile = false;//re-using flag for the next do while loop

                     sortCourses(onlyCoursesArray(coursesListLQHash.getAll()));

                        do{                       
                            course = null;    
                            System.out.print("\nPlease Select one of the following courses\nor enter 'X' to exit: ");
                            /*do the follwing code to validate the course code.
                            if the course input by the user equals to "x" end of  do while loop*/
                            for (int i= 0; i < onlyCoursesArray(coursesListLQHash.getAll()).length; i++)
                            {    
                                if(i%5 == 0)
                                    {
                                        System.out.print("\n");
                                    }
                                    System.out.print(sortCourses(onlyCoursesArray(coursesListLQHash.getAll()))[i] +" ");
                            }                        
                            System.out.print("\n");

                            course = input.next().toUpperCase();
                            if(course.equalsIgnoreCase("X"))
                                {                           
                                    endWhile = true;
                                }
                            else if(coursesListLQHash.fetch(course)== null)//no course exist in the structure
                                {
                                    System.out.print("This course does not exist in the structure: "+course+"\n");
                                }
                            else if(coursesListLQHash.fetch(course)!= null)//course is found in the structure!
                            {boolean noStudent = true;
                                endWhile = false;//get ready to exit the while loop
                                for(int i = 0; i<studentsInfoLQHash.fetch(id).getEnrollments().size();i++)//run through the enrollment structure
                                {   //compare each enrollment Code with the course
                                    //if is equal then the student is  enrolled in this course
                                    if(studentsInfoLQHash.fetch(id).getEnrollments().get(i).getCode().equalsIgnoreCase(course))
                                    {
                                        noStudent = false;
                                    }
                                   if(studentsInfoLQHash.fetch(id).getEnrollments().get(i).getCode().equalsIgnoreCase(course)
                                           && studentsInfoLQHash.fetch(id).getEnrollments().get(i).getGrade().equalsIgnoreCase("Not Complete")) 
                                    {    
                                        endWhile = true;//the while loop continues                                                                              
                                    }
                                   if(studentsInfoLQHash.fetch(id).getEnrollments().get(i).getCode().equalsIgnoreCase(course)
                                       && !studentsInfoLQHash.fetch(id).getEnrollments().get(i).getGrade().equalsIgnoreCase("Not Complete"))
                                        {
                                            System.out.println("The Class is completed, cannot be dropped");                                            
                                        }
                                   //if the student is not enrolled                               
                                }//end of for                            
                                   if(noStudent)
                                        {
                                            System.out.println("The Student is not enrolled in this class");
                                        }//if this is true the while loop continues until user exits find an enrollment     
                            }//end of else if
                        }while(!endWhile);

                            if(course.equalsIgnoreCase("X")||exit5)
                            {
                                break;//exit case 5
                            }

                            //to update the enrollment for the student
                            List<SP2018PROJECT_Enrollment_Padilla> dummyListToStudent = 
                                    studentsInfoLQHash.fetch(id).getEnrollments();
                            //to update the enrollments for the course
                            List<SP2018PROJECT_Enrollment_Padilla> dummyListToCourse = 
                                    coursesListLQHash.fetch(course).getStudents();
                            //dummy enrollment to update de enrollment structure
                            SP2018PROJECT_Enrollment_Padilla dummyEnrollment = null;
                            //Find the enrollment object in the student list
                            for(int i = 0; i < studentsInfoLQHash.fetch(id).getEnrollments().size(); i++)
                                {
                                    if(dummyListToStudent.get(i).getCode().equals(course))
                                        {   //enrollment found!
                                            //set the dummy enrollment to the object that has been found
                                            dummyEnrollment = dummyListToStudent.get(i);
                                            //remove the object found from dummy list for the course
                                            dummyListToCourse.remove(dummyListToStudent.get(i));
                                            //remove the object found from dummy list for the student
                                            dummyListToStudent.remove(i);
                                        }
                                }
                            //dummy student to update the structure and set the updated dummyList
                            SP2018PROJECT_Student_Padilla dummyStudent = studentsInfoLQHash.fetch(id);
                                //set the new list for the student
                                dummyStudent.setEnrollments(dummyListToStudent);
                                //update the structure of students
                                studentsInfoLQHash.delete(id);

                            //dummy course to update the structure and set the updated dummyList
                            SP2018PROJECT_Course_Padilla dummyCourse = coursesListLQHash.fetch(course);
                                //set the new list for the course
                                dummyCourse.setStudents(dummyListToCourse);
                                //update the structuer of courses
                                coursesListLQHash.delete(course);

                            if(studentsInfoLQHash.insert(dummyStudent)
                                    && coursesListLQHash.insert(dummyCourse))
                            {
                                System.out.print("\nClass DROPPED succesfully!\n**********\n");
                            }
                            else
                            {
                                System.out.print("\nClass NOT DROPPED.\n**********\n");
                            }
                            //reusing this list of enrollments to update the enrollment structure hash table.        
                            dummyListToCourse.clear();
                            /*I could not find a way to fixed the keys for the enrolment hash table structure
                            since the key I use is conformed by the id of the student and the course code, 
                            so here I get the entire array of enrollment from the structure,
                            set it to a dummy array type enrollment, remove the dummy enrollment found in the 
                            previous for loop, and then re-set this array to the structure.*/
                            //dummy arrayList type enrollment to update the enrollment structure
                            SP2018PROJECT_Enrollment_Padilla[] dummyArrayOfEnrollments = enrollmentInfoLQHash.getAll();
                            //run through all the dummy array of enrollments
                            for(int i = 0; i < dummyArrayOfEnrollments.length; i++)
                            {
                                //add enrollment element at position i to the Arraylist
                                dummyListToCourse.add(dummyArrayOfEnrollments[i]);
                                /*if the dummyEnrolment is the same as the enrollment
                                at position i in the array of enrollments is because we are
                                at the enrollment element we want to delete,
                                and since we already add it in the link list
                                we know we must remove it */
                                if(dummyEnrollment.equals(dummyArrayOfEnrollments[i]))
                                {   
                                    //the dummyEnrollment is removed.
                                   dummyListToCourse.remove(dummyEnrollment);
                                }
                            }
                            /*Now we must transform back the list of enrollments
                            to an array of enrollments and set it back to
                            the LQHash table of enrollments */
                            //running through the arraylist one by one
                            for(int i = 0; i < dummyListToCourse.size(); i++ )
                            { 
                                /*array of enrollments at position [i] 
                                is element of array list of enrollment at position (i)*/
                                dummyArrayOfEnrollments[i]=dummyListToCourse.get(i);
                            }
                            enrollmentInfoLQHash.setAll(dummyArrayOfEnrollments);
                    break;
                    }//end of case 5: drop a class for a student

                case 6:// print the list of students in one class
                    {
                    boolean courseExist = false;
                    System.out.print("\n*****LIST OF STUDENTS IN A CLASS*****");
                    do{
                    System.out.print("\nPlease Enter one of the following class Codes:");
                        for (int i= 0; i < onlyCoursesArray(coursesListLQHash.getAll()).length; i++)
                        {
                            if(i%5 == 0)
                            {
                                System.out.print("\n");
                            }
                            System.out.print(onlyCoursesArray(coursesListLQHash.getAll())[i] +" ");
                        }
                        System.out.print("\n\n");                
                        course = input.next().toUpperCase();
                        for (int i= 0; i < onlyCoursesArray(coursesListLQHash.getAll()).length; i++)
                        {
                            if(onlyCoursesArray(coursesListLQHash.getAll())[i].getClassCode().equals(course))
                            {
                               courseExist = true;
                            }
                        }
                        if(!courseExist)
                        {
                            System.out.print("\nCourse code: "+course+" does not exist in the  structure.");
                        }
                    }while(!courseExist);
                    if(!coursesListLQHash.fetch(course).getStudents().isEmpty())
                    {
                        System.out.print("\nLIST OF STUDENT IN CLASS: "+coursesListLQHash.fetch(course)+"\n");                
                            for (int i= 0; i < coursesListLQHash.fetch(course).getStudents().size(); i++)
                                {
                                    String temp = coursesListLQHash.fetch(course).getStudents().get(i).getStudentId();
                                    if(studentsInfoLQHash.fetch(temp)!= null)
                                    {
                                        System.out.println(studentsInfoLQHash.fetch(temp).getFirstName()+" "+studentsInfoLQHash.fetch(temp).getLastName());
                                    }
                                }
                        System.out.print("\n**********\n");
                    }
                    else
                    {
                        System.out.print("No Students in this class\n**********\n");
                    }
                    break;
                    }//end of case 6: print list of students in one class

                case 7://transcripts of a student
                    {
                    boolean transcriptsExist = false;

                    System.out.print("\n*****SCHOOL ABC - TRANSCRIPT*****");
                    do
                    {
                        System.out.print("\nPlease Enter one of the following ID's:");
                       for(int i = 0; i < onlyStudentsArray(studentsInfoLQHash.getAll()).length; i++)
                       {
                           if ( i%5 == 0)
                           {
                               System.out.print("\n");
                           }
                           System.out.print(onlyStudentsArray(studentsInfoLQHash.getAll())[i].getId()+" ");
                       }
                       System.out.print("\n\n");
                       id = input.next();   
                       for(int i = 0; i < onlyStudentsArray(studentsInfoLQHash.getAll()).length; i++)
                       {
                           if (onlyStudentsArray(studentsInfoLQHash.getAll())[i].getId().equals(id))
                           {
                               transcriptsExist = true;
                           }
                       }
                       if(!transcriptsExist)
                       {
                           System.out.print("\nNo Student with such ID: "+id);
                       }
                    }while(!transcriptsExist);
                     if(studentsInfoLQHash.fetch(id).getEnrollments().isEmpty())
                        {
                            transcriptsExist = false;
                            System.out.println("This Student is not enroll in any class");
                        }


                    if(transcriptsExist)
                        {
                            SP2018PROJECT_Course_Padilla[] toSortArrayOfCourses = 
                                    new SP2018PROJECT_Course_Padilla[studentsInfoLQHash.fetch(id).getEnrollments().size()];
                            System.out.print("\n-----------------------------"
                                    +"\nStudent:\t"+studentsInfoLQHash.fetch(id).getLastName()+" "+studentsInfoLQHash.fetch(id).getFirstName()
                                    +"\nStudent ID:\t"+studentsInfoLQHash.fetch(id).getId()
                                    +"\n"+studentsInfoLQHash.fetch(id).getDateOfBirth()
                                    +"\n-----------------------------\n");

                            System.out.print("\n-----------------------------"
                                    +"\nStudent:\t"+studentsInfoLQHash.fetch(id).getLastName()+" "+studentsInfoLQHash.fetch(id).getFirstName()
                                    +"\nStudent ID:\t"+studentsInfoLQHash.fetch(id).getId()
                                    +"\n"+studentsInfoLQHash.fetch(id).getDateOfBirth()
                                    +"\n-----------------------------\n");

                            for(int i = 0; i < sortEnrollments(studentsInfoLQHash.fetch(id).getEnrollments()).length;i++)
                            {
                                System.out.println(sortEnrollments(studentsInfoLQHash.fetch(id).getEnrollments())[i]);
                            }
                        }
                    break;
                    }//end of case 7: print transcript of a student

                case 8:
                    {
                        System.out.print("\n*****SHOW ALL STUDENT*****");
                      studentsInfoLQHash.showAll();  
                      System.out.print("\n**********\n");
                    break;
                    }            
            }//end of switch
        }//end of while 
        //update files type csv
        String COMMA_DELIMITER = ",";
        String NEW_LINE = "\n";
        
        try
        {//update file of studentInformation
//            FileWriter fileWriter = new FileWriter("C:\\Users\\francisco\\Documents\\NetBeansProjects\\SP2018PROJECT_PADILLA\\src\\sp2018project_padilla\\studentInformationUpdated.cvs");
            FileWriter fileWriter = new FileWriter("studentInformationUpdate.cvs");
            for(SP2018PROJECT_Student_Padilla student : onlyStudentsArray(studentsInfoLQHash.getAll()))
            {                   
                fileWriter.append(student.getId());//write students ID on csv
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getLastName());//lastname on csv
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getFirstName());//firstname on csv
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getSocialSecurity());//ss number on csv
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getDateOfBirth());//date of birth on csv
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getPhone());//phone number on csv
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(student.getAddress());//address on on csv
                fileWriter.append(NEW_LINE);//new line for the next student on csv
            }
                fileWriter.flush();
                fileWriter.close();
        }//end of try
        catch(Exception e)
        {
            System.out.print("\n"+e+"\n");
        }
        
        try
        {
//            FileWriter fileWriter2 = new FileWriter("C:\\Users\\francisco\\Documents\\NetBeansProjects\\SP2018PROJECT_PADILLA\\src\\sp2018project_padilla\\studentGradeUpdated.cvs");
            FileWriter fileWriter2 = new FileWriter("studentGradeUpdate.cvs");
            for(SP2018PROJECT_Student_Padilla grades : onlyStudentsArray(studentsInfoLQHash.getAll()))
            {//write the id of the student follow by a comma
                fileWriter2.append(grades.getId());
                fileWriter2.append(COMMA_DELIMITER);
                for(int i = 0; i < grades.getEnrollments().size();i++)
                {//write 'i' class follow by a comma, write 'i' grade follwo by a comma
                    // until for loop ends
                    fileWriter2.append(grades.getEnrollments().get(i).getCode());
                    fileWriter2.append(COMMA_DELIMITER);
                    if(grades.getEnrollments().get(i).getGrade().equals("Not Complete"))
                    {
                        fileWriter2.append("X");
                    }
                    else
                    {
                      fileWriter2.append(grades.getEnrollments().get(i).getGrade());  
                    }
                    
                    fileWriter2.append(COMMA_DELIMITER);
                }
                //endter a line and repeate the firs loop until iterates all the  array list of students.
                fileWriter2.append(NEW_LINE);
            }//end of for loop 
                fileWriter2.flush();
                fileWriter2.close();
        }//end of try
        catch(Exception e)
                {
                    System.out.print("\n"+e+"\n");
                }
    } //end of main    
    
    public static void menu1()
    {
        System.out.print(
                         "\n input an integer:"+
                         "\n0. exit"+
                         "\n1. Add a Student"+
                         "\n2. Remove a Student"+
                         "\n3. Find a Student"+
                         "\n4. Add a class for a Student"+
                         "\n5. Drop a class for one Student"+
                         "\n6. Print the list of students in one class"+
                         "\n7. Print out the transcript of one student"+
                         "\n8. Show All\n");
    }//end of menu2 function: flash menu.
    
    public static SP2018PROJECT_LQHashedStudent_Padilla importStudents() throws IOException
    {
        SP2018PROJECT_LQHashedStudent_Padilla studentList = new SP2018PROJECT_LQHashedStudent_Padilla(100);
        
//            String StudentsFileLocation =  File.separator + "Users" + File.separator + "francisco" + File.separator + "Documents" + File.separator + "NetBeansProjects" + File.separator + "SP2018PROJECT_PADILLA" + File.separator + "src" + File.separator + "sp2018project_padilla" + File.separator + "studentInformation.csv";
            String StudentsFileLocation =  "studentInformation.csv";
//            String GradesFileLocation = File.separator+"Users" + File.separator + "francisco" + File.separator + "Documents" + File.separator + "NetBeansProjects" + File.separator + "SP2018PROJECT_PADILLA" + File.separator + "src" + File.separator + "sp2018project_padilla" + File.separator + "studentGrade.csv";
            String GradesFileLocation = "studentGrade.csv";
            
            // the file with the Students information and grades
            File StudentsInfo = new File(StudentsFileLocation);
            File StudentsGrades = new File(GradesFileLocation);
          try 
        {  
            // The scanner that will read the file
           FileReader fileReader = new FileReader(StudentsInfo);
           BufferedReader br;
           br = new BufferedReader(fileReader);
           String fileLine;
           //while (fileReader.hasNext())
           while((fileLine = br.readLine())!= null)
           {
//                    System.out.print(fileLine);
                 //read a line from the file

                //split the file into parts
                String names [] = fileLine.split(",");

               SP2018PROJECT_Student_Padilla student = new SP2018PROJECT_Student_Padilla 
                    (names[0], names[1],
                     names[2], names[3],
                     names[4], names[5],
                     names[6],null);

               studentList.insert(student);
                }//end of while loop
           } //end of try
        
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }//end of catch
          return studentList;
        }//end of importoStudents
    
    public static SP2018PROJECT_LQHashedCourses_Padilla importCourses() throws IOException
    {
        SP2018PROJECT_LQHashedCourses_Padilla coursesListHashed = 
                new SP2018PROJECT_LQHashedCourses_Padilla(100); 
        
        ArrayList<String> coursesArrayList =
                new ArrayList<String>();//One list of courses
        
        //file location declaration
//        String GradesFileLocation = File.separator+"Users" + File.separator + "francisco" + File.separator + "Documents" + File.separator + "NetBeansProjects" + File.separator + "SP2018PROJECT_PADILLA" + File.separator + "src" + File.separator + "sp2018project_padilla" + File.separator + "studentGrade.csv";
          String GradesFileLocation = "studentGrade.csv";
        //inizialisation of file type File
        File file = new File(GradesFileLocation);

                try 
                {
                    //object that scans the file
                   Scanner scanner = new Scanner(file);

                   //while there is a next line
                    while (scanner.hasNextLine())
                        {   
                            //read one line
                            String line = scanner. nextLine();
                            //split elements of each line every time a comma appears
                            String[] parts = line.split(",");                            
                            //run through the array of elements
                            for(int counter = 0;counter<parts.length;counter++)
                            {      /*if:
                                    -The index "counter" is an odd number,
                                    -is not equal to zero
                                    -the value of that cell in the arraylist is not empty,
                                    -and the arraylist does not contain such value
                                    the value at index "counter" is a new String to become a course.*/                                    
                                    if (counter % 2 == 1 
                                        && counter != 0 
                                        && parts[counter] != null
                                        && !coursesArrayList.contains(parts[counter])) 
                                    {
                                        coursesArrayList.add(parts[counter]);
                                    }//end of if
                            }//end of for    
                        }//end of while
                    
        /*Travers the courseArrayList and create
        objects of the SP2018PROJECT_Course_Padilla class,
        then insert the object into a course hashed structure*/
        for (int i = 0; i < coursesArrayList.size(); i++)
            {
                //creation of object type SP2018PROJECT_Course_Padilla
                SP2018PROJECT_Course_Padilla course =
                        new SP2018PROJECT_Course_Padilla (coursesArrayList.get(i),null);
                
                //insert the new SP2018PROJECT_Course_Padilla into a hashed structure
                coursesListHashed.insert(course);
            }
                //close the file
                scanner.close();
                } 
                catch ( FileNotFoundException e) 
                {
                    System.out. println("File not found");
                    System.exit(0);
                }
                
        return coursesListHashed;//return coursesListHashed
    }//end of importCourses function
    
    public static SP2018PROJECT_LQHashedEnrollment_Padilla importEnrollments() throws IOException
    {
        SP2018PROJECT_LQHashedEnrollment_Padilla enrollmentListHashed = 
                new SP2018PROJECT_LQHashedEnrollment_Padilla(350); 
        
        ArrayList<SP2018PROJECT_Enrollment_Padilla> enrollmentArrayList =
                new ArrayList<SP2018PROJECT_Enrollment_Padilla>();//One list of courses
        
        //file location declaration
//        String GradesFileLocation = File.separator+"Users" + File.separator + "francisco" + File.separator + "Documents" + File.separator + "NetBeansProjects" + File.separator + "SP2018PROJECT_PADILLA" + File.separator + "src" + File.separator + "sp2018project_padilla" + File.separator + "studentGrade.csv";
        String GradesFileLocation = "studentGrade.csv";
//String GradesFileLocation = "studentGrade.csv";
        
        //inizialisation of file type File
        File file = new File(GradesFileLocation);

                try 
                {
                    //object that scans the file
                   Scanner scanner = new Scanner(file);

                   //while there is a next line
                    while (scanner.hasNextLine())
                        {   
                            //read one line
                            String line = scanner. nextLine();
                            //split elements of each line every time a comma appears
                            String[] parts = line.split(",");                            
                            //run through the array of elements
                            for(int counter = 0;counter<parts.length;counter++)
                            {   
                                //the first element of the line will allways be the ID
                                String studentId = parts[0];
                                String courseCode;
                                String studentGrade;
                                    /*if:
                                    -The index "counter" is an odd number,
                                    -is not equal to zero
                                    -the value of that cell in the arraylist is not empty,
                                    -and the arraylist does not contain such value
                                    the value at index "counter" is a new String to become a course.*/                                    
                                    if (counter % 2 == 1 
                                        && counter != 0 
                                        && parts[counter] != null) 
                                    {
                                        int gradeIndex = counter + 1;
                                        courseCode = (parts[counter]);
                                        studentGrade = (parts[gradeIndex]);
                                                    //create an enrolment object everytime this loops
                                         SP2018PROJECT_Enrollment_Padilla enroll =
                                                 new SP2018PROJECT_Enrollment_Padilla
                                                    (studentId,courseCode,studentGrade);
                                        
                                        /*insert the object SP2018project_Enrollment_Paddilla
                                         to the hashed structure*/
                                          enrollmentListHashed.insert(enroll);
                                    }//end of if
                            }//end of for    
                        }//end of while
                //close the file
                scanner.close();
                } 
                catch ( FileNotFoundException e) 
                {
                    System.out. println("File not found");
                    System.exit(0);
                }
                
        return enrollmentListHashed;//return coursesListHashed
    }//end of importCourses function
     
    public static SP2018PROJECT_Student_Padilla addStudent(SP2018PROJECT_Student_Padilla[] theArray)
    {
        List<SP2018PROJECT_Enrollment_Padilla> enrollments = new ArrayList<SP2018PROJECT_Enrollment_Padilla>();
        Scanner input = new Scanner(System.in);        
        String  Id,
                lastName, 
                firstName,
                socialSecurity, 
                dateOfBirth,
                phone,
                address;
        boolean valid;
        do
        {
            valid = true;
            System.out.print("\nEnter new student ID:\t\t");
            Id = input.next();
            if(Id.length() != 7)
            {
                valid = false;
                System.out.print("ID needs to be seven(7) digits long.\n");                           
            }
            else if(Id.length() == 7)
            {
              for(int i =0; i< theArray.length;i++)
                {   
                    if(theArray[i].getId().equals(Id))
                        {
                            valid = false;
                            System.out.println("This ID is already being used.");                                              
                        }
                }
            }
            else
            {
                System.out.print("\n");
                valid = true;
            }
        }while(!valid);
        
        System.out.print("Enter student Last Name:\t");
        lastName = input.next();
        System.out.print("Enter student First Name:\t");
        firstName = input.next();
        System.out.print("Enter student SS:\t\t");
        socialSecurity = input.next();
        System.out.print("Enter student birth date:\t");
        dateOfBirth = input.next();
        System.out.print("Enter student  phone:\t\t");
        phone = input.next();
        System.out.print("Enter student Address:\t\t");
        address = input.next();
        
        SP2018PROJECT_Student_Padilla newStudent = new SP2018PROJECT_Student_Padilla
                (Id,
                lastName, 
                firstName,
                socialSecurity, 
                dateOfBirth,
                phone,
                address,
                enrollments);
        
        return newStudent;
    }//end of add student function
    
    public static SP2018PROJECT_Student_Padilla[] onlyStudentsArray(SP2018PROJECT_Student_Padilla[] theArray)
    {
        List <SP2018PROJECT_Student_Padilla> students = new ArrayList <SP2018PROJECT_Student_Padilla>();
                    for(int i = 0; i<theArray.length; i++)
                    {
                        if(theArray[i] != null)
                        {
                            if(!theArray[i].getId().isEmpty())
                                {students.add(theArray[i]);}
                        }
                    }
        
        SP2018PROJECT_Student_Padilla [] onlyStudents = 
                new SP2018PROJECT_Student_Padilla[students.size()];
                    for(int i = 0; i < students.size();i++)
                        {
                            onlyStudents[i] = students.get(i);
                        }
        
        return onlyStudents;
    }//end of onlyStudentsArray function
    
    public static SP2018PROJECT_Course_Padilla[] onlyCoursesArray(SP2018PROJECT_Course_Padilla[] theArray)
    {
        List <SP2018PROJECT_Course_Padilla> courses = new ArrayList <SP2018PROJECT_Course_Padilla>();
        for(int i = 0; i<theArray.length; i++)
        {
            if(theArray[i] != null)
            {
                courses.add(theArray[i]);
            }
        }
        
        SP2018PROJECT_Course_Padilla [] onlyCourses = 
                new SP2018PROJECT_Course_Padilla[courses.size()];
        for(int i = 0; i < courses.size();i++)
        {
            onlyCourses[i] = courses.get(i);
        }
        
        return onlyCourses;
    }//end of onlyCoursesArray function
        
    public static SP2018PROJECT_Enrollment_Padilla[] onlyEnrollmentsArray(SP2018PROJECT_Enrollment_Padilla[] theArray)
    {

     List <SP2018PROJECT_Enrollment_Padilla> enrollments = new ArrayList <SP2018PROJECT_Enrollment_Padilla>();
     for(int i = 0; i<theArray.length; i++)
     {
         if(theArray[i] != null)
         {
             enrollments.add(theArray[i]);
         }
     }

     SP2018PROJECT_Enrollment_Padilla [] onlyEnrollments = 
             new SP2018PROJECT_Enrollment_Padilla[enrollments.size()];
     for(int i = 0; i < enrollments.size();i++)
     {
         onlyEnrollments[i] = enrollments.get(i);
     }

     return onlyEnrollments;            
     }//end of onlyEnrollmentArray function
    
    public static SP2018PROJECT_Course_Padilla[] sortCourses(SP2018PROJECT_Course_Padilla[] array)
    {
        int n = array.length;
        for (int pass=1; pass < n; pass++)// count how many times 
        { 
            // This next loop becomes shorter and shorter
            for (int x=0; x < n-pass; x++) 
            {
                if(array[x].getYear()>array[x+1].getYear())
                {   //swap
                    SP2018PROJECT_Course_Padilla temp = array[x]; 
                    array[x]   = array[x+1];
                    array[x+1] = temp;    
                }
           else if(array[x].getYear() == array[x+1].getYear())
                {
                    if(array[x].getSemester().equals("FA") && array[x+1].getSemester().equals("SP"))
                    {   //swap 
                        SP2018PROJECT_Course_Padilla temp = array[x]; 
                        array[x]   = array[x+1];
                        array[x+1] = temp;    
                    }
               else if(array[x].getSemester().equals(array[x+1].getSemester()))
                    {
                        if(array[x].getCourseLetters().compareTo(array[x+1].getCourseLetters()) > 0)
                        {//swap 
                            SP2018PROJECT_Course_Padilla temp = array[x]; 
                            array[x]   = array[x+1];
                            array[x+1] = temp;  
                        } 
                   else if(array[x].getClassCode().equals(array[x+1].getCourseLetters()))
                        { 
                            if(array[x].getCourseNumber()<array[x+1].getCourseNumber())
                                 {//swap 
                                     SP2018PROJECT_Course_Padilla temp = array[x]; 
                                     array[x]   = array[x+1];
                                     array[x+1] = temp;    
                                 }
                        }//end of course lletter in x equals course letters in x+1
                    }//end of semester in x is equal to semester in x+1                        
                }//and of 'if year in x is equal to year in x+1                 
            }//end of for loop 2
        }//end of for loop 1
        return array;
    }//end of sortEnrollments function
    
    public static SP2018PROJECT_Enrollment_Padilla[] sortEnrollments(List<SP2018PROJECT_Enrollment_Padilla> theList)
    { 
        SP2018PROJECT_Enrollment_Padilla[] array = new SP2018PROJECT_Enrollment_Padilla[theList.size()];
        
        for(int i = 0; i < array.length; i++)
        {
            array[i] = theList.get(i);
        }
        
        int n = array.length;
        for (int pass=1; pass < n; pass++)// count how many times 
        { 
            // This next loop becomes shorter and shorter
            for (int x=0; x < n-pass; x++) 
            {
                String courseLetter2 = array[x+1].getCode().substring(7, 11);
                String courseLetter = array[x].getCode().substring(7, 11);
                String semester2 = array[x+1].getCode().substring(0, 2);
                String semester = array[x].getCode().substring(0, 2);
                String grade2 = array[x+1].getGrade();
                String grade = array[x].getGrade();
                
                if(grade.equals("Not Complete") && !grade2.equals("Not Complete"))
                {//swap
                    SP2018PROJECT_Enrollment_Padilla temp = array[x]; 
                    array[x]   = array[x+1];
                    array[x+1] = temp;                     
                }
           else if(grade.equals("Not Complete") && grade2.equals("Not Complete")
                   || !grade.equals("Not Complete") && !grade2.equals("Not Complete"))
                {
                    if(Integer.parseInt(array[x].getCode().substring(2,6)) > Integer.parseInt(array[x+1].getCode().substring(2,6)))
                    {   //swap
                        SP2018PROJECT_Enrollment_Padilla temp = array[x]; 
                        array[x]   = array[x+1];
                        array[x+1] = temp;    
                    }
               else if(Integer.parseInt(array[x].getCode().substring(2,6)) == Integer.parseInt(array[x+1].getCode().substring(2,6)))
                    {
                        if(semester.equals("FA") && semester2.equals("SP"))
                        {   //swap 
                            SP2018PROJECT_Enrollment_Padilla temp = array[x]; 
                            array[x]   = array[x+1];
                            array[x+1] = temp;    
                        }
                   else if(semester.equals(semester2))
                        {
                            if(courseLetter.compareTo(courseLetter2) > 0)
                            {//swap 
                                SP2018PROJECT_Enrollment_Padilla temp = array[x]; 
                                array[x]   = array[x+1];
                                array[x+1] = temp;  
                            } 
                       else if(courseLetter.equals(courseLetter2))
                            { 
                                if(Integer.parseInt(array[x].getCode().substring(11,14)) >
                                        Integer.parseInt(array[x+1].getCode().substring(11,14)))
                                     {//swap 
                                         SP2018PROJECT_Enrollment_Padilla temp = array[x]; 
                                         array[x]   = array[x+1];
                                         array[x+1] = temp;    
                                     }
                            }//end of course lletter in x equals course letters in x+1
                        }//end of semester in x is equal to semester in x+1                        
                    }//and of 'if year in x is equal to year in x+1   
                }//end of grade if grade comparasion
            }//end of for loop 2
        }//end of for loop 1
        return array;
    }//end of sortEnrollments
}
