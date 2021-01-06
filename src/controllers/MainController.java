/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Project.Booking;
import Project.Guest;
import Project.Invoice;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Project.Popup;
import Project.Room;
import Project.SceneChange; //to import the changescreen method from SceneChange Class
import Project.User;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class MainController {
    
    SceneChange scene = new SceneChange();
    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private Button btnBook;
    
    @FXML
    private Button btnManage;
    
    @FXML
    private Button btnGuest;
    
    @FXML
    private Button btnInvoice;
    
    @FXML
    private ImageView guestIcon;
    @FXML
    private Pane expandedMenu;
    @FXML
            private Label lblTodayDate,lblGuest,lblRoom,lblCheckOut,lblInvoice;
    
    Popup popup = new Popup();
    Room room = new Room();
    Booking book = new Booking();
    Guest guest = new Guest();
    Invoice invoice = new Invoice();
    User user = new User();

    //scene change
    @FXML
    void initialize() throws SQLException{
    LocalDate todayDate = LocalDate.now();
    lblTodayDate.setText(String.valueOf(todayDate));
    lblRoom.setText(String.valueOf(room.getRoomCount()));
    lblGuest.setText(String.valueOf(guest.getActiveGuestCount()));
    lblCheckOut.setText(String.valueOf(book.getTodayCheckOut()));
    lblInvoice.setText("RM " + String.valueOf(invoice.getTotalEarning()));
    
    }
    @FXML
    void Main(ActionEvent event) throws IOException {
        scene.Main(mainPane);
    }

    @FXML //open "make reservation" page
    void BookRoom(ActionEvent event) throws IOException {
//         
        scene.BookRoom(mainPane);
    }
    
    @FXML//open "guest information" page
    void Guest(ActionEvent event) throws IOException {
        scene.Guest(mainPane);
    }
    
    @FXML//open "view invoice" page
    void Invoice(ActionEvent event) throws IOException {
        scene.Invoice(mainPane);
    }
    
    @FXML//open "view invoice" page
    void Invoice2(MouseEvent event) throws IOException {
        scene.Invoice(mainPane);
    }
    
    @FXML//open "manage reservation" page
    void ManageBooking(ActionEvent event) throws IOException {
        scene.ManageBooking(mainPane);
    }
      @FXML//open "manage reservation" page
    void ManageBooking2(MouseEvent event) throws IOException {
        scene.ManageBooking(mainPane);
    }

    @FXML
    void LogOut(ActionEvent event) throws IOException {
        if (popup.logoutConfirm())
        scene.LogOut(mainPane);
    }
     @FXML
    void LogOut2(MouseEvent event) throws IOException {
        
        if (popup.logoutConfirm())
        scene.LogOut(mainPane);
    }
    @FXML
    void Setting (MouseEvent event) throws IOException, SQLException{
     
        if (user.CheckIsAdmin(user.UserID1)){
            
    popup.SettingPopup();
        }
        else{
        popup.displayError("You don't have admin priviledge to view this");
        }
    }


    @FXML
    void guest(MouseEvent event) throws IOException {
        scene.Guest(mainPane);
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
