/*
 * Copyright (C) 2017 North Carolina A&T State University
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
public class PrintJob implements Comparable<PrintJob>{
    private String stlPath;
    private PrintType type;
    private Date dueDate;
    private String requestComments;
    private Student requestingStudent;
    private String printComments;
    private PrintStatus status;
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
    public PrintJob(String stlPath, PrintType type, Date dueDate, PrintStatus status, String comments, Student student) {
        this(stlPath, type, dueDate, status, student);
        this.requestComments = comments;
    }
    
    public PrintJob(String stlPath, PrintType type, Date dueDate, PrintStatus status, Student student) {
        this.status = status;
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
    
    public void setComments(String comments){
        requestComments = comments;
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
        String returnString = String.format("%s, %s, %s, %s" + System.lineSeparator() + "%s" + System.lineSeparator() + "%s" + System.lineSeparator() + "BREAK" + System.lineSeparator() + "%s" + System.lineSeparator() + "END", stlPath, type.name(), dateFormat.format(dueDate), status.name(), requestingStudent.toString(), requestComments, printComments);
        
        return returnString;
    }
    
    private Date getDueDateForPersonalProject(){ 
        GregorianCalendar cal = new GregorianCalendar();
        
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 5);
        
        return cal.getTime();
    }

    @Override
    public int compareTo(PrintJob o) {
        if(this.dueDate.equals(o.dueDate)) {
            return this.status.compareTo(o.status);
        }
        return dueDate.compareTo(dueDate);
    }

}
