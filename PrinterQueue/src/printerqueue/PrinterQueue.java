/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
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
    
    private PrintJob lastPrintJobMoved;
    
    private final String queueConfigFileName = "queueConfig.txt";
    private final String waitingForPickupConfigFileName = "waitingForPickupConfig.txt";
    private final String completedConfigFileName = "completedConfig.txt";
    
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
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
    
    public ArrayList<PrintJob> getPrintQueue() {
        return queue;
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
    
    public ArrayList<PrintJob> getWaitingForPickupQueue() {
        return waitingForPickup;
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
    
    public void loadPrinterQueue() {
        try {
            Scanner reader = new Scanner(new File(queueConfigFileName));
            while(reader.hasNext()){
                String[] jobLine = reader.nextLine().split(",");
                String[] studentLine = reader.nextLine().split(",");
                
                String requestComments = "";
                boolean moreRequestComments = true;
                while(moreRequestComments) {
                    String nextLine = reader.nextLine();
                    if(nextLine.trim().equals("BREAK"))
                        moreRequestComments = false;
                    else
                        requestComments += nextLine;
                }
                
                String printComments = "";
                boolean morePrintComments = true;
                while(morePrintComments) {
                    String nextLine = reader.nextLine();
                    if(nextLine.trim().equals("END"))
                        morePrintComments = false;
                    else
                        printComments += nextLine;
                }
                
                Student requestingStudent = new Student(studentLine[0], studentLine[1], studentLine[2], studentLine[3], studentLine[4]);
                
                try {
                    PrintJob newJob = new PrintJob(jobLine[0].trim(), PrintType.valueOf(jobLine[1].trim()), dateFormat.parse(jobLine[2].trim()), PrintStatus.valueOf(jobLine[3].trim()), requestComments, requestingStudent);
                    newJob.setPrintComments(printComments);
                    queue.add(newJob);
                } catch (ParseException ex) {
                    System.err.println("Unable to parse date " + jobLine[2] + " for print job. Job not added.");
                    Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open Printer Queue Config File for reading");
            Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadWaitingForPickup() {
        try {
            Scanner reader = new Scanner(new File(this.waitingForPickupConfigFileName));
            while(reader.hasNext()){
                String[] jobLine = reader.nextLine().split(",");
                String[] studentLine = reader.nextLine().split(",");
                
                String requestComments = "";
                boolean moreRequestComments = true;
                while(moreRequestComments) {
                    String nextLine = reader.nextLine();
                    if(nextLine.trim().equals("BREAK"))
                        moreRequestComments = false;
                    else
                        requestComments += nextLine;
                }
                
                String printComments = "";
                boolean morePrintComments = true;
                while(morePrintComments) {
                    String nextLine = reader.nextLine();
                    if(nextLine.trim().equals("END"))
                        morePrintComments = false;
                    else
                        printComments += nextLine;
                }
                
                Student requestingStudent = new Student(studentLine[0], studentLine[1], studentLine[2], studentLine[3], studentLine[4]);
                
                try {
                    PrintJob newJob = new PrintJob(jobLine[0].trim(), PrintType.valueOf(jobLine[1].trim()), dateFormat.parse(jobLine[2].trim()), PrintStatus.valueOf(jobLine[3].trim()), requestComments, requestingStudent);
                    newJob.setPrintComments(printComments);
                    waitingForPickup.add(newJob);
                } catch (ParseException ex) {
                    System.err.println("Unable to parse date " + jobLine[2] + " for print job. Job not added.");
                    Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open Waiting for Pickup Config File for reading");
            Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadCompleted() {
        try {
            Scanner reader = new Scanner(new File(this.completedConfigFileName));
            while(reader.hasNext()){
                String[] jobLine = reader.nextLine().split(",");
                String[] studentLine = reader.nextLine().split(",");
                
                String requestComments = "";
                boolean moreRequestComments = true;
                while(moreRequestComments) {
                    String nextLine = reader.nextLine();
                    if(nextLine.trim().equals("BREAK"))
                        moreRequestComments = false;
                    else
                        requestComments += nextLine;
                }
                
                String printComments = "";
                boolean morePrintComments = true;
                while(morePrintComments) {
                    String nextLine = reader.nextLine();
                    if(nextLine.trim().equals("END"))
                        morePrintComments = false;
                    else
                        printComments += nextLine;
                }
                
                Student requestingStudent = new Student(studentLine[0], studentLine[1], studentLine[2], studentLine[3], studentLine[4]);
                
                try {
                    PrintJob newJob = new PrintJob(jobLine[0].trim(), PrintType.valueOf(jobLine[1].trim()), dateFormat.parse(jobLine[2].trim()), PrintStatus.valueOf(jobLine[3].trim()), requestComments, requestingStudent);
                    newJob.setPrintComments(printComments);
                    completed.add(newJob);
                } catch (ParseException ex) {
                    System.err.println("Unable to parse date " + jobLine[2] + " for print job. Job not added.");
                    Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Unable to open Completed Config File for reading");
            Logger.getLogger(PrinterQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the lastPrintJobMoved
     */
    public PrintJob getLastPrintJobMoved() {
        return lastPrintJobMoved;
    }

    /**
     * @param lastPrintJobMoved the lastPrintJobMoved to set
     */
    public void setLastPrintJobMoved(PrintJob lastPrintJobMoved) {
        this.lastPrintJobMoved = lastPrintJobMoved;
    }
    
    public void sortPrintQueue() {
        Collections.sort(this.queue);
    }
}
