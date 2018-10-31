/*
 * Copyright (C) 2018 derie
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
package printerqueueviewcontroller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import printerqueue.PrintJob;
import printerqueue.PrinterQueue;
/**
 *
 * @author derie
 */
public class CompletedPrintJobViewController extends Stage{
    private ListView completedJobsListView;
    
    public CompletedPrintJobViewController(PrinterQueue queue){
        
        System.out.println(queue.toString());
        this.setTitle("View Completed Jobs");
        completedJobsListView = new ListView();
        completedJobsListView.setItems(FXCollections.observableArrayList(queue.getCompletedQueue()));
        
        
        
        VBox CompletedJobsPane = new VBox();
        Scene CompletedJobsScene = new Scene(CompletedJobsPane);
        Button viewJobsButton = new Button("View Completed Job");
    CompletedJobsPane.getChildren().addAll(completedJobsListView, viewJobsButton);
    this.setScene(CompletedJobsScene);
    viewJobsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PrintJobViewController viewPrint = new PrintJobViewController();
                viewPrint.setPrintJob((PrintJob) completedJobsListView.getSelectionModel().getSelectedItem());
                
                viewPrint.setTitle("View Print Job");
                viewPrint.show();
                viewPrint.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("CLOSING");
                        PrintJob returnPrint = viewPrint.getPrintJob();
                        switch(returnPrint.getStatus()) {
                            case READY_TO_PRINT:
                                queue.setPrintJob(completedJobsListView.getSelectionModel().getSelectedIndex(), viewPrint.getPrintJob());
                                break;
                            case READY_FOR_PICKUP:
                                queue.addPrintJobWaitingForPickup(queue.removePrintJob(completedJobsListView.getSelectionModel().getSelectedIndex()));
                                queue.setLastPrintJobMoved(queue.getPrintJobWaitingForPickup(queue.getNumPrintJobsWaitingForPickup() - 1));
                                break;
                            case COMPLETED:
                                queue.addCompletedPrintJob(queue.removePrintJob(completedJobsListView.getSelectionModel().getSelectedIndex()));
                                queue.setLastPrintJobMoved(queue.getCompletedPrintJob(queue.getNumCompletedPrintJobs() - 1));
                                break;
                        }
                 
                    }
                    
                });
            }
            
        });
    }
}

