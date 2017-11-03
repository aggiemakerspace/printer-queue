/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

/**
 *
 * @author CCannon
 */
public class AddPrintJobTextInputDialog extends Dialog<PrintJob> {

    private ButtonType add = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    private Date dueDate;
    
    //Add constructor
    //See https://stackoverflow.com/questions/31230228/get-multiple-results-from-custom-dialog-javafx
    public AddPrintJobTextInputDialog() {
        this.dueDate = new Date();
        
        setTitle("Add Print Job");
        setHeaderText("Complete the following form to add your print job to the queue");

        GridPane printInfoPane = new GridPane();
        Label stlPathLabel = new Label("Path to .stl file");
        Label printTypeLabel = new Label("What is this print for?");
        Label dueDateLabel = new Label("When is this print needed by?");
        Label studentLabel = new Label("Please select your name");
        Label commentsLabel = new Label("Enter any additional instructions for printing here");
        
        TextField stlPathTextField = new TextField();
        ComboBox printTypeComboBox = new ComboBox();
        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setDueDate(dueDatePicker.getValue());
            }
        });
        ComboBox studentComboBox = new ComboBox();
        TextArea commentsTextArea = new TextArea();
        
        
        Button stlPathBrowseButton = new Button("Browse");
        stlPathBrowseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser chooser = new FileChooser();
                File file = chooser.showOpenDialog(null);
                if (file != null) {
                    stlPathTextField.setText(file.getAbsolutePath());
                }
            }
        });
        Button newStudentButton = new Button("Register new student");
        
        printInfoPane.add(stlPathLabel, 0, 0);
        printInfoPane.add(printTypeLabel, 0, 1);
        printInfoPane.add(dueDateLabel, 0, 2);
        printInfoPane.add(studentLabel, 0, 3);
        printInfoPane.add(commentsLabel, 0, 4);
        printInfoPane.add(stlPathTextField, 1, 0);
        printInfoPane.add(printTypeComboBox, 1, 1);
        printInfoPane.add(dueDatePicker, 1, 2);
        printInfoPane.add(studentComboBox, 1, 3);
        printInfoPane.add(commentsTextArea, 1, 4);
        printInfoPane.add(stlPathBrowseButton, 2, 0);
        printInfoPane.add(newStudentButton, 2, 3);
        
        getDialogPane().getButtonTypes().addAll(add, cancel);
        getDialogPane().setContent(printInfoPane);
    }
    
    private void setDueDate(LocalDate selectedDate) {
        dueDate = Date.from(selectedDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
