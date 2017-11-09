/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueueviewcontroller;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
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

/**
 *
 * @author CCannon
 */
public class PrinterQueueViewController extends Application {

    @Override
    public void start(Stage primaryStage) {
        QueueButtonEventHandler buttonHandler = new QueueButtonEventHandler();
        BorderPane root = new BorderPane();

        Label topLabel = new Label("3-D Printer Queue");
        HBox contentPane = new HBox();
        VBox commandPane = new VBox();

        ListView queueListView = new ListView();
        contentPane.getChildren().add(queueListView);

        Button addJobButton = new Button("Add Print Job");
        addJobButton.setOnAction(buttonHandler);
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

    public class QueueButtonEventHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            AddPrintJobTextInputDialog addJobDialog = new AddPrintJobTextInputDialog();
            addJobDialog.showAndWait();
        }
    }
}
