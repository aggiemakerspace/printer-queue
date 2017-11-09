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
    private String requestComments;
    private Student requestingStudent;
    private String printComments;
    private PrintStatus status;
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    public PrintJob(String stlPath, PrintType type, Date dueDate, String comments, Student student) {
        this(stlPath, type, dueDate, student);
        this.requestComments = comments;
    }
    
    public PrintJob(String stlPath, PrintType type, Date dueDate, Student student) {
        this.status = PrintStatus.READY_TO_PRINT;
        this.stlPath = stlPath;
        this.type = type;
        this.requestingStudent = student;
        switch(type) {
            case PERSONAL:
                this.dueDate = getDueDateForPersonalProject();
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
        return requestComments;
    }
    
    /**
     * @return the Student who requested this print
     */
    public Student getStudent() {
        return requestingStudent;
    }
    
    /**
     * @return the printComments
     */
    public String getPrintComments() {
        return printComments;
    }

    /**
     * @param printComments the printComments to set
     */
    public void setPrintComments(String printComments) {
        this.printComments = printComments;
    }

    /**
     * @return the status
     */
    public PrintStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(PrintStatus status) {
        this.status = status;
    }
    
    public String toString() {
        String returnString = String.format("%s, %s, %s" + System.lineSeparator() + "%s" + System.lineSeparator() + "%s" + System.lineSeparator() + "BREAK" + System.lineSeparator() + "%s" + System.lineSeparator() + "END", stlPath, type.name(), dateFormat.format(dueDate), requestingStudent.toString(), requestComments, printComments);
        
        return returnString;
    }
    
    private Date getDueDateForPersonalProject(){ 
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 5);
        
        return cal.getTime();
    }

}
