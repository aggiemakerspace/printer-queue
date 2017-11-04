/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author CCannon
 */
public class PrintJob {
    private String stlPath;
    private PrintType type;
    private Date dueDate;
    private String comments;
    private Student requestingStudent;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    public PrintJob(String stlPath, PrintType type, Date dueDate, String comments, Student student) {
        this(stlPath, type, dueDate, student);
        this.comments = comments;
    }
    
    public PrintJob(String stlPath, PrintType type, Date dueDate, Student student) {
        this.stlPath = stlPath;
        this.type = type;
        this.requestingStudent = student;
        switch(type) {
            case PERSONAL:
                this.dueDate = incrementDateFiveDays(new Date());
                break;
            default:
                this.dueDate = dueDate;
        }
    }
    
    /**
     * @return the stlPath
     */
    public String getStlPath() {
        return stlPath;
    }

    /**
     * @return the type
     */
    public PrintType getType() {
        return type;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }
    
    /**
     * @return the Student who requested this print
     */
    public Student getStudent() {
        return requestingStudent;
    }
    
    private Date incrementDateFiveDays(Date date){ 
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 5);
        
        return cal.getTime();
    }
    
}
