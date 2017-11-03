/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import java.util.ArrayList;

/**
 *
 * @author CCannon
 */
public class PrinterQueue {
    private ArrayList<PrintJob> queue;
    
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
}
