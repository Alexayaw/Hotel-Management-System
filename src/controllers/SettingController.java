/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Project.Popup;
import Project.User;
import Project.Validate;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author user
 */
public class SettingController {

    User user = new User();
    @FXML
    Pane NewUserPane;
    @FXML
    TableView tbvUser;
    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    @FXML
    private CheckBox chkAdmin;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnRefreshUser;

    @FXML
    private TextField txtDeleteUserID;

    @FXML
    private Button btnDelete;

    Validate validate = new Validate();
    String ID, Name, Password;
    Popup popup = new Popup();

    @FXML
    void initialize() throws SQLException {
        user.DisplayUserDetails(tbvUser);
    }

    @FXML
    void NewUser(ActionEvent event) throws SQLException {
        if (validate.txtfield(txtUserID) && validate.txtfield(txtUserName) && validate.txtfield(txtPassword)) {
          ID = txtUserID.getText();
            if (!user.verifyUserID(ID)) {
                  
            Name = txtUserName.getText();
            Password = txtPassword.getText();
             System.out.println(ID);
                 System.out.println(Name);
                   System.out.println(Password);
                    user.AddAdminUser(ID, Name, Password);
                    popup.confirmation("User: "+ID+"has been created succesfully!");
                if (chkAdmin.isSelected()) {
                   user.UpdateToAdmin(ID);
                } 
                
            } else {
                popup.displayError("UserID already exist");
            }
        } else {
            popup.displayError("Cannot leave blank");

        }
    }

    @FXML
    void DeleteUser(ActionEvent event) throws SQLException {
        if (validate.txtfield(txtDeleteUserID)) {
             ID = txtDeleteUserID.getText();
            if(user.verifyUserID(ID)){
           
            if(popup.ActionConfirm("Are you sure you want to delete User: "+ID +" ? This action is irreversible")){
            user.DeleteUser(ID);
            popup.confirmation("User has been succesfully deleted");
            }
            }
            else{
            popup.displayError("UserID not found");
            }
        }

    }

    @FXML
    void Refresh(ActionEvent event) throws SQLException {
        tbvUser.getColumns().clear();
        user.DisplayUserDetails(tbvUser);
    }

}
