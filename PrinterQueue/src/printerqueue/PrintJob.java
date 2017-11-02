/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import java.util.Date;

/**
 *
 * @author CCannon
 */
public class PrintJob {
    private String stlPath;
    private PrintType type;
    private Date dueDate;
    private String comments;

    /**
     * @return the stlPath
     */
    public String getStlPath() {
        return stlPath;
    }

    /**
     * @param stlPath the stlPath to set
     */
    public void setStlPath(String stlPath) {
        this.stlPath = stlPath;
    }

    /**
     * @return the type
     */
    public PrintType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(PrintType type) {
        this.type = type;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    
}
