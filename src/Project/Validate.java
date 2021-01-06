/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author user
 */
public class Validate {
    Popup popup = new Popup();

    public boolean datePicker(DatePicker datePicker) {
        if (datePicker.getValue() != null) {
            return true;
        }
        return false;
    }

    public boolean comboBox(ComboBox cmb) {
        if (cmb.getValue() != null) {
            return true;
        }
        return false;
    }

    public boolean guestDetails(TextField txtname, TextField txtICPASS, TextField txtMobileNo, TextField txtEmail, TextField txtAddress) {
        if (txtname.getText().isEmpty()|| txtICPASS.getText().isEmpty()  || txtMobileNo.getText().isEmpty()  || txtEmail.getText().isEmpty() || txtAddress.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    public boolean txtfield(TextField txt) {
        if (txt.getText().isEmpty()) {
            return false;
        }
        return true;
    }
    public boolean inputValidate(TextField txt) {
        String text = txt.getText();
        if (text.matches("\\d+")) // Reads: "Any digit one or more times"
            return true;
        else{
        popup.displayError("Please enter numeric value only!");
        }
        return false;
    }


}
