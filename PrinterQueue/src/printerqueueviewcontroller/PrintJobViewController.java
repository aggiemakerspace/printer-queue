/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueueviewcontroller;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import printerqueue.PrintStatus;
import printerqueue.PrintType;

/**
 *
 * @author CCannon
 */
public class PrintJobViewController extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        
        Label stlPathLabel = new Label(".stl File Path");
        TextField stlPathTextField = new TextField();
        Label statusLabel = new Label("Print Status");
        ComboBox statusComboBox = new ComboBox();
        ArrayList<String> values = new ArrayList();
        for(PrintType type : PrintType.values()){
            values.add(type.name());
        }
        statusComboBox.setItems(FXCollections.observableList(values));
        Label typeLabel = new Label("Print Type");
        TextField typeTextField = new TextField();
        Label dueDateLabel = new Label("Due Date");
        TextField dueDateTextField = new TextField();
        Label requestCommentsLabel = new Label("Request Comments");
        TextArea requestCommentsTextArea = new TextArea();
        
        Label requestingStudentNameLabel = new Label("Requesting Student");
        TextField requestingStudentNameTextField = new TextField();
        Label requestingStudentEmailLabel = new Label("Email");
        TextField requestingStudentEmailTextField = new TextField();
        Label requestingStudentPhoneLabel = new Label("Phone Number");
        TextField requestingStudentPhoneTextField = new TextField();
        
        Label printCommentsLabel = new Label("Print Comments");
        TextArea printCommentsTextArea = new TextArea();
        
        Button savePrintCommentsButton = new Button("Save comments");
        Button transitionButton = new Button("Ready for Pickup");
        Button closeButton = new Button("Close");
        
        root.add(stlPathLabel, 0, 0);
        root.add(stlPathTextField, 1, 0);
        root.add(statusLabel, 0, 1);
        root.add(statusComboBox, 1, 1);
        root.add(typeLabel, 0, 2);
        root.add(typeTextField, 1, 2);
        root.add(dueDateLabel, 0, 3);
        root.add(dueDateTextField, 1, 3);
        root.add(requestCommentsLabel, 0, 4);
        root.add(requestCommentsTextArea, 1, 4);
        
        root.add(requestingStudentNameLabel, 3, 0);
        root.add(requestingStudentNameTextField, 4, 0);
        root.add(requestingStudentPhoneLabel, 3, 1);
        root.add(requestingStudentPhoneTextField, 4, 1);
        root.add(requestingStudentEmailLabel, 3, 2);
        root.add(requestingStudentEmailTextField, 4, 2);
        
        root.add(printCommentsLabel, 0, 8);
        root.add(printCommentsTextArea, 1, 8);
        
        root.add(savePrintCommentsButton, 1, 9);
        root.add(transitionButton, 2, 9);
        root.add(closeButton, 3, 9);
    }
    
}
