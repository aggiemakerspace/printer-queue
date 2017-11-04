/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CCannon
 */
public class PrinterQueue {
    private ArrayList<PrintJob> queue;
    private ArrayList<PrintJob> waitingForPickup;
    private ArrayList<PrintJob> completed;
    
    private final String queueConfigFileName = "queueConfig.txt";
    private final String waitingForPickupConfigFileName = "waitingForPickupConfig.txt";
    private final String completedConfigFileName = "completedConfig.txt";
    
    public PrinterQueue() {
        queue = new ArrayList<PrintJob>();
        waitingForPickup = new ArrayList<PrintJob>();
        completed = new ArrayList<PrintJob>();
    }
    
    public int getNumPrintJobs() {
        return queue.size();
    }
    
    public void addPrintJob(PrintJob job) {
        queue.add(job);
    }
    
    public void setPrintJob(int index, PrintJob job) {
        queue.set(index, job);
    }
    
    public PrintJob getPrintJob(int index) {
        return queue.get(index);
    }
    
    public PrintJob removePrintJob(int index) {
        return queue.remove(index);
    }
    
    public int getNumPrintJobsWaitingForPickup() {
        return waitingForPickup.size();
    }
    
    public void addPrintJobWaitingForPickup(PrintJob job) {
        waitingForPickup.add(job);
    }
    
    public void setPrintJobWaitingForPickup(int index, PrintJob job) {
        waitingForPickup.set(index, job);
    }
    
    public PrintJob getPrintJobWaitingForPickup(int index) {
        return waitingForPickup.get(index);
    }
    
    public PrintJob removePrintJobWaitingForPickup(int index) {
        return waitingForPickup.remove(index);
    }
    
    public int getNumCompletedPrintJobs() {
        return completed.size();
    }
    
    public void addCompletedPrintJob(PrintJob job) {
        completed.add(job);
    }
    
    public void setCompletedPrintJob(int index, PrintJob job) {
        completed.set(index, job);
    }
    
    public PrintJob getCompletedPrintJob(int index) {
        return completed.get(index);
    }
    
    public PrintJob removeCompletedPrintJob(int index) {
        return completed.remove(index);
    }
    
    public String toString(){
        String returnString = "q";
        for(PrintJob job : queue) {
            returnString += System.lineSeparator() + job.toString();
        }
        
        returnString += System.lineSeparator() + "w";
        for(PrintJob job: waitingForPickup) {
            returnString += System.lineSeparator() + job.toString();
        }
        
        returnString += System.lineSeparator() + "c";
        for(PrintJob job: completed) {
            returnString += System.lineSeparator() + job.toString();
        }
        
        return returnString;
    }
    
    public void savePrinterQueue(){
        try {
            PrintWriter pw = new PrintWriter(new File(this.queueConfigFileName));
            
            for(PrintJob job: this.queue) {
                pw.println(job.toString());
            }
            
            pw.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open Printer Queue Config File for writing");
            Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveWaitingForPickup(){
        try {
            PrintWriter pw = new PrintWriter(new File(this.waitingForPickupConfigFileName));
            
            for(PrintJob job: this.waitingForPickup) {
                pw.println(job.toString());
            }
            
            pw.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open Waiting For Pickup Config File for writing");
            Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveCompleted(){
        try {
            PrintWriter pw = new PrintWriter(new File(this.completedConfigFileName));
            
            for(PrintJob job: this.completed) {
                pw.println(job.toString());
            }
            
            pw.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open Completed Config File for writing");
            Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
