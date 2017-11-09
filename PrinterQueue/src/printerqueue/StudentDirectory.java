/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CCannon
 */
public class StudentDirectory {
    private HashMap<String, Student> directory;
    private final String configFileName = "studentDirectoryConfigFile";
    
    public StudentDirectory() {
        directory = new HashMap();
    }
    
    public void saveStudentDirectory() {
        try {
            PrintWriter writer = new PrintWriter(new File(configFileName));
            
            Collection<Student> outputDir = directory.values();
            
            for(Student student : outputDir) {
                writer.println(student.toString());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open " + configFileName + " for writing.");
            Logger.getLogger(StudentDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadStudentDirectory() {
        try {
            Scanner reader = new Scanner(new File(configFileName));
            while(reader.hasNext()) {
                String[] studentString = reader.nextLine().split(",");
                
                if(!directory.containsKey(studentString[2].trim())){
                    Student newStudent = new Student(studentString[0].trim(), studentString[1].trim(), studentString[2].trim(), studentString[3].trim(), studentString[4].trim());
                    directory.put(newStudent.getStudentID(), newStudent);
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open " + configFileName + " for reading.");
            Logger.getLogger(StudentDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
