/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javafx.util.Pair;
import printerqueue.PrintJob;
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
        viewQueuePrintButton.setPrefWidth(500);
        queuePane.getChildren().add(queueLabel);
        queuePane.getChildren().add(queueListView);
        queuePane.getChildren().add(viewQueuePrintButton);
        
        VBox waitingForPickupPane = new VBox();
        waitingForPickupListView = new ListView();
        Label waitingForPickupLabel = new Label("Waiting for Pickup");
        Button viewWaitingForPickupPrintButton = new Button("View Finished Job");
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
                refreshListViews();
            }
            
        });
        commandPane.getChildren().add(pickedupButton);
        
        Button undoButton = new Button("Undo");
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
        queueListView.setItems(FXCollections.observableList(queue.getPrintQueue()));
        waitingForPickupListView.setItems(FXCollections.observableArrayList(queue.getWaitingForPickupQueue()));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
