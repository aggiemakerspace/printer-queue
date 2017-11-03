/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author CCannon
 */
public class PrintJob {
    private String stlPath;
    private PrintType type;
    private Date dueDate;
    private String comments;
    private Student student;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public PrintJob(String stlPath, PrintType type, String dueDate, String comments) {
        this(stlPath, type, dueDate);
        this.comments = comments;
    }
    
    public PrintJob(String stlPath, PrintType type, String dueDate) {
        Date formattedDueDate = new Date();
        try {
            formattedDueDate = dateFormat.parse(dueDate);
        } catch (ParseException ex) {
            System.err.println("Failed to convert due date " + dueDate);
            TextInputDialog dialog = new TextInputDialog(dateFormat.format(new Date()));
            dialog.setTitle("Date Input Error");
            dialog.setHeaderText("Dates must be entered in the format MM/DD/YYYY");
            dialog.setContentText("Please enter the due date:");
            
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()) {
                try {
                    formattedDueDate = dateFormat.parse(result.get());
                } catch (ParseException ex1) {
                    System.err.println("Failed to convert due date " + dueDate);
                    
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Date Input Error");
                    alert.setHeaderText("Your due date has failed to parse");
                    alert.setContentText("Due date will be set to 5 days from today");
                    
                    
                    
                    formattedDueDate = incrementDateFiveDays(new Date());
                    
                    Logger.getLogger(PrintJob.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            
            Logger.getLogger(PrintJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.stlPath = stlPath;
        this.type = type;
        switch(type) {
            case PERSONAL:
                this.dueDate = incrementDateFiveDays(new Date());
                break;
            default:
                this.dueDate = formattedDueDate;
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
        return student;
    }
    
    private Date incrementDateFiveDays(Date date){ 
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 5);
        
        return cal.getTime();
    }
    
}
