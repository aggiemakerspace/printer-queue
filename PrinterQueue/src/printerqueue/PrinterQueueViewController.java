/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author CCannon
 */
public class PrinterQueueViewController extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        
        Label topLabel = new Label("3-D Printer Queue");
        HBox contentPane = new HBox();
        VBox commandPane = new VBox();
        
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
