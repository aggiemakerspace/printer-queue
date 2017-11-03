/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printerqueue;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 *
 * @author CCannon
 */
public class AddPrintJobTextInputDialog extends Dialog<PrintJob>{
    private ButtonType add = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    
    //Add constructor
    //See https://stackoverflow.com/questions/31230228/get-multiple-results-from-custom-dialog-javafx
}
