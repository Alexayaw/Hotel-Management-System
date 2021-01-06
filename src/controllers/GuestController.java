/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Project.Guest;
import Project.Popup;
import Project.SceneChange;
import Project.Validate;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author user
 */
public class GuestController {

    Guest guest = new Guest();
    Popup popup = new Popup();
    Validate validate = new Validate();
    String IC_PASS, GuestName, MobileNo, Email, Address,GuestFilter;
    int GuestID;

    SceneChange scene = new SceneChange();
    @FXML
    private AnchorPane guestPane;
    @FXML
    private Pane expandedMenu;
    @FXML
    private CheckBox chkEdit;
    @FXML
    private Button btnUpdateGuest, btnSearchGuest, btnRefreshGuest;
    @FXML
    private TableView tbvGuest;

    @FXML
    private TextField txtSearchICPASS;

    @FXML
    private TextField txtGuestID, txtName, txtEmail, txtMobileNo, txtICPASS, txtAddress;

    @FXML
    private ComboBox <String>cmbGuestFilter;
    @FXML
    void initialize() throws SQLException {
        btnUpdateGuest.setVisible(false);
        guest.DisplayGuestDetails(tbvGuest);
        cmbGuestFilter.getItems().addAll("All","Active","Inactive");
    }

    @FXML
    void EnableUpdateGuest(ActionEvent event) {
        if (chkEdit.isSelected()) {
            btnUpdateGuest.setVisible(true);
            txtName.setDisable(false);
            txtMobileNo.setDisable(false);
            txtEmail.setDisable(false);
            txtAddress.setDisable(false);
            txtICPASS.setDisable(false);
        } else {
            btnUpdateGuest.setVisible(false);
             txtName.setDisable(true);
            txtMobileNo.setDisable(true);
            txtEmail.setDisable(true);
            txtAddress.setDisable(true);
            txtICPASS.setDisable(true);
        }
    }

    @FXML
    void RefreshGuest(ActionEvent event) throws SQLException {
        tbvGuest.getColumns().clear();
        guest.DisplayGuestDetails(tbvGuest);

    }

    @FXML
    void SearchGuest(ActionEvent event) throws SQLException {
        if (validate.txtfield(txtSearchICPASS)) {
            if (validate.inputValidate(txtSearchICPASS)) {
                IC_PASS = txtSearchICPASS.getText();
                if (guest.verifyOldICPASS(IC_PASS)) {
                    tbvGuest.getColumns().clear();
                    guest.SearchGuestDetails(tbvGuest, IC_PASS);
                    GuestID = guest.getGuestID(IC_PASS);
                    GuestName = guest.getGuestName(GuestID);
                    MobileNo = guest.getMobileNo(GuestID);
                    Email = guest.getGuestEmail(GuestID);
                    Address = guest.getGuestAddress(GuestID);
                    
                    txtGuestID.setText(String.valueOf(GuestID));
                    txtICPASS.setText(IC_PASS);
                    txtName.setText(GuestName);
                    txtEmail.setText(Email);
                    txtMobileNo.setText(MobileNo);
                    txtAddress.setText(Address);

                }
            }
        } else {
            popup.displayError("Guest IC or Passport cannot leave blank!");
        }

    }

    @FXML
    void UpdateGuest(ActionEvent event) throws SQLException {
        if(validate.txtfield(txtICPASS)&&validate.txtfield(txtName)&&validate.txtfield(txtEmail)&&validate.txtfield(txtMobileNo)&&validate.txtfield(txtAddress)){
                    GuestName = txtName.getText();
                            IC_PASS=txtICPASS.getText();
                    MobileNo = txtMobileNo.getText();
                    Email = txtEmail.getText();
                    Address = txtAddress.getText();
            guest.updateGuest(GuestID, GuestName, IC_PASS, MobileNo, Email, Address);
           popup.confirmation("Update Sucess!");
        }else{
        popup.displayError("Cannot leave field(s) blank!");
        }
    }
    @FXML 
    void GuestFilter (ActionEvent event) throws SQLException{
        GuestFilter=cmbGuestFilter.getSelectionModel().getSelectedItem();
       
    switch (GuestFilter){
        case "Active" : 
            tbvGuest.getColumns().clear();
            guest.DisplayActiveGuestDetails(tbvGuest);
            break;
        case "Inactive":
             tbvGuest.getColumns().clear();
            guest.DisplayCheckedOutGuestDetails(tbvGuest);
            break;
           
            default: 
                 tbvGuest.getColumns().clear();
                guest.DisplayGuestDetails(tbvGuest);
    
    }
    }
    //scene change
    //scene change
    @FXML
    void Main(ActionEvent event) throws IOException {
        scene.Main(guestPane);
    }

    @FXML //open "make reservation" page
    void BookRoom(ActionEvent event) throws IOException {
//         
        scene.BookRoom(guestPane);
    }

    @FXML//open "guest information" page
    void Guest(ActionEvent event) throws IOException {
        scene.Guest(guestPane);
    }

    @FXML//open "view invoice" page
    void Invoice(ActionEvent event) throws IOException {
        scene.Invoice(guestPane);
    }

    @FXML//open "manage reservation" page
    void ManageBooking(ActionEvent event) throws IOException {
        scene.ManageBooking(guestPane);
    }

    @FXML
    void LogOut(ActionEvent event) throws IOException {
        if (popup.logoutConfirm()) {
            scene.LogOut(guestPane);
        }
    }

    @FXML
    void menuExpand(ActionEvent event) {
        expandedMenu.setVisible(true);
    }

    @FXML
    void menuCollapse(ActionEvent event) {
        expandedMenu.setVisible(false);
    }
}
