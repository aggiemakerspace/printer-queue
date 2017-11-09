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
    
    @Override
    public void start(Stage primaryStage) {
        queue = new PrinterQueue();
        directory = new StudentDirectory();
        
        BorderPane root = new BorderPane();

        Label topLabel = new Label("3-D Printer Queue");
        HBox contentPane = new HBox();
        VBox commandPane = new VBox();

        ListView queueListView = new ListView();
        queueListView.setItems(FXCollections.observableList(queue.getPrintQueue()));
        contentPane.getChildren().add(queueListView);

        Button addJobButton = new Button("Add Print Job");
        addJobButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                AddPrintJobTextInputDialog addJobDialog = new AddPrintJobTextInputDialog();
                Optional<PrintJob> result = addJobDialog.showAndWait();

                if(result.isPresent()){
                    PrintJob newJob = result.get();
                    queue.addPrintJob(newJob);
                    queueListView.refresh();
                    if(!directory.containsStudent(newJob.getStudent().getStudentID()))
                        directory.putStudent(newJob.getStudent().getStudentID(), newJob.getStudent());
                }
            }
        });
        commandPane.getChildren().add(addJobButton);

        root.setTop(topLabel);
        root.setCenter(contentPane);
        root.setLeft(commandPane);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Aggie Makerspace Print Queue");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
