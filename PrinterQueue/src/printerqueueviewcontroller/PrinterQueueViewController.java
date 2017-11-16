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
package printerqueueviewcontroller;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import printerqueue.PrintJob;
import printerqueue.PrintStatus;
import printerqueue.PrinterQueue;
import printerqueue.StudentDirectory;

/**
 *
 * @author CCannon
 */
public class PrinterQueueViewController extends Application {
    private PrinterQueue queue;
    private StudentDirectory directory;
    private ListView queueListView;
    private ListView waitingForPickupListView;
    
    /**
     * 
     * @param primaryStage The stage you will display
     */
    @Override
    public void start(Stage primaryStage) {
        queue = new PrinterQueue();
        queue.loadPrinterQueue();
        queue.loadWaitingForPickup();
        directory = new StudentDirectory();
        directory.loadStudentDirectory();
        
        BorderPane root = new BorderPane();

        Label topLabel = new Label("3-D Printer Queue");
        HBox contentPane = new HBox();
        VBox commandPane = new VBox();

        VBox queuePane = new VBox();
        queueListView = new ListView();
        Label queueLabel = new Label("Print Queue");
        Button viewQueuePrintButton = new Button("View Print Job");
        viewQueuePrintButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PrintJobViewController viewPrint = new PrintJobViewController();
                viewPrint.setPrintJob((PrintJob) queueListView.getSelectionModel().getSelectedItem());
                
                viewPrint.setTitle("View Print Job");
                viewPrint.show();
                viewPrint.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("CLOSING");
                        PrintJob returnPrint = viewPrint.getPrintJob();
                        switch(returnPrint.getStatus()) {
                            case READY_TO_PRINT:
                                queue.setPrintJob(queueListView.getSelectionModel().getSelectedIndex(), viewPrint.getPrintJob());
                                break;
                            case READY_FOR_PICKUP:
                                queue.addPrintJobWaitingForPickup(queue.removePrintJob(queueListView.getSelectionModel().getSelectedIndex()));
                                queue.setLastPrintJobMoved(queue.getPrintJobWaitingForPickup(queue.getNumPrintJobsWaitingForPickup() - 1));
                                break;
                            case COMPLETED:
                                queue.addCompletedPrintJob(queue.removePrintJob(queueListView.getSelectionModel().getSelectedIndex()));
                                queue.setLastPrintJobMoved(queue.getCompletedPrintJob(queue.getNumCompletedPrintJobs() - 1));
                                break;
                        }
                        refreshListViews();
                    }
                    
                });
            }
            
        });
        viewQueuePrintButton.setPrefWidth(500);
        queuePane.getChildren().add(queueLabel);
        queuePane.getChildren().add(queueListView);
        queuePane.getChildren().add(viewQueuePrintButton);
        
        VBox waitingForPickupPane = new VBox();
        waitingForPickupListView = new ListView();
        Label waitingForPickupLabel = new Label("Waiting for Pickup");
        Button viewWaitingForPickupPrintButton = new Button("View Finished Job");
        viewWaitingForPickupPrintButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PrintJobViewController viewPrint = new PrintJobViewController();
                viewPrint.setPrintJob((PrintJob) waitingForPickupListView.getSelectionModel().getSelectedItem());
                
                viewPrint.setTitle("View Print Job");
                viewPrint.show();
                viewPrint.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        System.out.println("CLOSING");
                        PrintJob returnJob = viewPrint.getPrintJob();
                        switch(returnJob.getStatus()) {
                            case READY_TO_PRINT:
                                queue.addPrintJob(queue.removePrintJobWaitingForPickup(waitingForPickupListView.getSelectionModel().getSelectedIndex()));
                                queue.setLastPrintJobMoved(queue.getPrintJob(queue.getNumPrintJobs() - 1));
                                break;
                            case READY_FOR_PICKUP:
                                queue.setPrintJobWaitingForPickup(waitingForPickupListView.getSelectionModel().getSelectedIndex(), viewPrint.getPrintJob());
                                break;
                            case COMPLETED:
                                queue.addCompletedPrintJob(queue.removePrintJobWaitingForPickup(waitingForPickupListView.getSelectionModel().getSelectedIndex()));
                                queue.setLastPrintJobMoved(queue.getCompletedPrintJob(queue.getNumCompletedPrintJobs() - 1));
                                break;
                        }
                        refreshListViews();
                    }
                    
                });
            }
        });
        viewWaitingForPickupPrintButton.setPrefWidth(500);
        waitingForPickupPane.getChildren().add(waitingForPickupLabel);
        waitingForPickupPane.getChildren().add(waitingForPickupListView);
        waitingForPickupPane.getChildren().add(viewWaitingForPickupPrintButton);
        
        refreshListViews();
        contentPane.getChildren().add(queuePane);
        contentPane.getChildren().add(waitingForPickupPane);

        Button addJobButton = new Button("Add Print Job");
        addJobButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AddPrintJobTextInputDialog addJobDialog = new AddPrintJobTextInputDialog();
                Optional<PrintJob> result = addJobDialog.showAndWait();

                if(result.isPresent()){
                    PrintJob newJob = result.get();
                    queue.addPrintJob(newJob);
                    refreshListViews();
                    if(!directory.containsStudent(newJob.getStudent().getStudentID()))
                        directory.putStudent(newJob.getStudent().getStudentID(), newJob.getStudent());
                    
                    queue.savePrinterQueue();
                }
            }
        });
        commandPane.getChildren().add(addJobButton);
        
        Button readyForPickupButton = new Button("Print Ready For Pickup");
        readyForPickupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                queue.setLastPrintJobMoved((PrintJob) queueListView.getSelectionModel().getSelectedItem());
                queue.addPrintJobWaitingForPickup(queue.removePrintJob(queueListView.getSelectionModel().getSelectedIndex()));
                queue.getPrintJobWaitingForPickup(queue.getNumPrintJobsWaitingForPickup() - 1).setStatus(PrintStatus.READY_FOR_PICKUP);
                refreshListViews();
            }
            
        });
        commandPane.getChildren().add(readyForPickupButton);
        
        Button pickedupButton = new Button("Print Picked Up");
        pickedupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                queue.setLastPrintJobMoved((PrintJob) waitingForPickupListView.getSelectionModel().getSelectedItem());
                queue.addCompletedPrintJob(queue.removePrintJobWaitingForPickup(waitingForPickupListView.getSelectionModel().getSelectedIndex()));
                queue.getCompletedPrintJob(queue.getNumCompletedPrintJobs() - 1).setStatus(PrintStatus.COMPLETED);
                refreshListViews();
            }
            
        });
        commandPane.getChildren().add(pickedupButton);
        
        Button undoButton = new Button("Undo");
        undoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                undoMove();
            }
        
        });
        commandPane.getChildren().add(undoButton);
        
        

        root.setTop(topLabel);
        root.setCenter(contentPane);
        root.setLeft(commandPane);

        Scene scene = new Scene(root, 800, 500);

        primaryStage.setTitle("Aggie Makerspace Print Queue");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void refreshListViews(){
        queue.sortPrintQueue();
        queueListView.setItems(FXCollections.observableList(queue.getPrintQueue()));
        waitingForPickupListView.setItems(FXCollections.observableArrayList(queue.getWaitingForPickupQueue()));
    }
    
    private void undoMove() {
        PrintStatus undesiredState = queue.getLastPrintJobMoved().getStatus();
        switch(undesiredState) {
            case COMPLETED:
                queue.addPrintJobWaitingForPickup(queue.removeCompletedPrintJob(queue.getNumCompletedPrintJobs() - 1));
                queue.getPrintJobWaitingForPickup(queue.getNumPrintJobsWaitingForPickup() -1).setStatus(PrintStatus.READY_FOR_PICKUP);
                break;
            case READY_FOR_PICKUP:
                queue.addPrintJob(queue.removePrintJobWaitingForPickup(queue.getNumPrintJobsWaitingForPickup() - 1));
                queue.getPrintJob(queue.getNumPrintJobs() - 1).setStatus(PrintStatus.READY_TO_PRINT);
                break;
        }
        this.refreshListViews();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
