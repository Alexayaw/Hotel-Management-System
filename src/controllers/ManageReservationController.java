/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Project.Booking;
import Project.Guest;
import Project.Popup;
import Project.Room;
import Project.SceneChange;
import Project.Table;
import Project.Validate;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ManageReservationController {

    SceneChange scene = new SceneChange();
    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane managePane;
    @FXML
    private Pane expandedMenu, AddRoomPane, DeleteRoomPane;

    @FXML
    private TableView<?> tbvBooking, tbvRoom;

    @FXML
    private Button btnSearchBooking, btnRefreshBooking, btnDeleteBooking, btnCheckOut;

    @FXML
    private TextField txtSearchBookID, txtBookingID, txtRoomID;

    @FXML
    private RadioButton radDeleteBook, radCheckOut, radAddRoom, radDeleteRoom;

    @FXML
    private ToggleGroup bookingGrp, RoomGrp;

    @FXML
    private Button btnSearchRoom1, btnRefreshRoom;

    @FXML
    private TextField txtSearchRoomID, txtAddRoomID, txtDeleteRoomID;

    @FXML
    private ComboBox<String> cmbRoomType, cmbBookingFilter;

    Popup popup = new Popup();
    Table table = new Table();
    Booking booking = new Booking();
    Room room = new Room();
    Guest guest = new Guest();
    Validate validate = new Validate();
    int BookingID, RoomID, GuestID, RoomPrice;
    String RoomType, BookingFilter;

    @FXML
    void initialize() throws SQLException {
        //booking
        booking.DisplayActiveBookingDetails(tbvBooking);
        btnDeleteBooking.setVisible(false);
        btnCheckOut.setVisible(false);
        //room
        AddRoomPane.setDisable(true);
        DeleteRoomPane.setDisable(true);
        room.DisplayRoomDetails(tbvRoom);
        cmbRoomType.getItems().addAll("Queen", "Twin", "Family", "VIP");
        cmbBookingFilter.getItems().addAll("Active", "Checked-Out", "Cancelled","All");

    }

    @FXML
    void BookingFilter(ActionEvent event) throws SQLException {
        BookingFilter = cmbBookingFilter.getSelectionModel().getSelectedItem();
        switch (BookingFilter) {
            case "Active":
                tbvBooking.getColumns().clear();
                booking.DisplayActiveBookingDetails(tbvBooking);
                break;
            case "Checked-Out":
                tbvBooking.getColumns().clear();
                booking.DisplayCheckedOutBookingDetails(tbvBooking);
                break;
            case "Cancelled":
                tbvBooking.getColumns().clear();
                booking.DisplayCheckedOutBookingDetails(tbvBooking);
                break;
              case"All":
                  tbvBooking.getColumns().clear();
                  booking.DisplayAllBookingDetails(tbvBooking);
                  break;
              default:
                  tbvBooking.getColumns().clear();
                  booking.DisplayActiveBookingDetails(tbvBooking);

        }
    }

    @FXML
    void bookingSelection(ActionEvent event) {

        if (radDeleteBook.isSelected()) {
            btnDeleteBooking.setVisible(true);
            btnCheckOut.setVisible(false);
        } else if (radCheckOut.isSelected()) {
            btnCheckOut.setVisible(true);
            btnDeleteBooking.setVisible(false);
        }
    }

    @FXML
    void searchBooking(ActionEvent event) throws SQLException {

        if (validate.txtfield(txtSearchBookID)) {

            if (validate.inputValidate(txtSearchBookID)) {
                BookingID = Integer.valueOf(txtSearchBookID.getText());
                if (booking.verifyBookingID(BookingID)) {
                    tbvBooking.getColumns().clear(); //clear previous stacked column before loading in new data
                    booking.searchBookingID(tbvBooking, BookingID);
                    txtBookingID.setText(String.valueOf(BookingID));
                    txtRoomID.setText(String.valueOf(room.getRoomID(BookingID)));
                }

            }
        } else {
            popup.displayError("Booking ID cannot be blank");
        }
    }

    @FXML
    void refreshBooking(ActionEvent event) throws SQLException {
        tbvBooking.getColumns().clear();
        booking.DisplayActiveBookingDetails(tbvBooking);
        txtSearchBookID.clear();
        txtBookingID.clear();
    }

    @FXML
    void checkout(ActionEvent event) throws SQLException {
        if(validate.txtfield(txtBookingID)){
             if (popup.ActionConfirm("Do you want to check out Room: " + txtRoomID.getText() + "?")){
            //update booking status 
            booking.CheckOut(BookingID);
            //update guest status
            GuestID = guest.getGuestID(BookingID);
            System.out.println(GuestID);
            guest.updateGuestStatusInactive(GuestID);

            //update room status
            RoomID = Integer.valueOf(txtRoomID.getText());
            room.UpdateRoomStatusAvailable(RoomID);

            System.out.println("Succesfully checked-out");
            }
            else{
            popup.displayError("Booking ID cannot leave blank");
            }
        }

    }

    @FXML
    void deleteBooking(ActionEvent event) throws SQLException {
        GuestID = guest.getGuestID(BookingID);
        if(validate.txtfield(txtBookingID)) {
           if(popup.ActionConfirm("Are you sure you want to delete?This action is irreversible")){
        //update booking status
            booking.DeleteBooking(BookingID); //set bookingstatus to cancelled 
            //update guest status
            guest.updateGuestStatusInactive(GuestID);
            //update room status 
            RoomID = Integer.valueOf(txtRoomID.getText());
            room.UpdateRoomStatusAvailable(RoomID);
            System.out.println("Delete successful");
           }
        }
        else{
        popup.displayError("Booking ID cannot leave blank");
        }
    }

    @FXML
    void searchRoom(ActionEvent event) throws SQLException {
        if (validate.txtfield(txtSearchRoomID)) {
            if (validate.inputValidate(txtSearchRoomID)) {
                RoomID = Integer.valueOf(txtSearchRoomID.getText());
                if (room.VerifyRoomID(RoomID)) {
                    tbvRoom.getColumns().clear();
                    room.SearchRoomDetails(tbvRoom, RoomID);
                } else {
                    popup.displayError("RoomID does not exist!");
                }
            }
        } else {
            popup.displayError("RoomID cannot leave blank!");
        }
    }

    @FXML
    void refreshRoom(ActionEvent event) throws SQLException {
        tbvRoom.getColumns().clear();
        room.DisplayRoomDetails(tbvRoom);
        txtSearchRoomID.clear();

    }

    @FXML
    void RoomAction(ActionEvent event) {
        if (radAddRoom.isSelected()) {
            AddRoomPane.setDisable(false);
            DeleteRoomPane.setDisable(true);
        } else if (radDeleteRoom.isSelected()) {
            AddRoomPane.setDisable(true);
            DeleteRoomPane.setDisable(false);
        }
    }

    @FXML
    void RoomTypeSelection(ActionEvent event) { //to determine price for each room type
        RoomType = String.valueOf(cmbRoomType.getSelectionModel().getSelectedItem());
        switch (RoomType) {
            case "Queen":
                RoomPrice = 0;
                break;
            case "Twin":
                RoomPrice = 50;
                break;
            case "Family":
                RoomPrice = 80;
                break;
            case "VIP":
                RoomPrice = 100;
                break;
        }

    }

    @FXML
    void AddRoom(ActionEvent event) throws SQLException {
        if (validate.txtfield(txtAddRoomID) && validate.comboBox(cmbRoomType)) {
            RoomType = String.valueOf(cmbRoomType.getSelectionModel().getSelectedItem());
            if (validate.inputValidate(txtAddRoomID)) {
                RoomID = Integer.valueOf(txtAddRoomID.getText());
                if (!room.VerifyRoomID(RoomID)) {

                    room.AddRoom(RoomID, RoomType, RoomPrice);
                    popup.confirmation("Room " + RoomID + "(" + RoomType + ")" + "has been created succesfully");
                } else {
                    popup.displayError("Room ID already exist, try another ID.");
                }
            }

        } else {
            popup.displayError("Room ID cannot leave blank!");
        }
    }

    @FXML
    void DeleteRoom(ActionEvent event) throws SQLException {
        if (validate.txtfield(txtDeleteRoomID)) {
            if (validate.inputValidate(txtDeleteRoomID)) {
                RoomID = Integer.valueOf(txtDeleteRoomID.getText());
                if (room.VerifyRoomID(RoomID)) {
                    if (popup.ActionConfirm("Are you sure you want to delete Room:" + RoomID + "?" + "This action is irreversible!")) {
                        room.DeleteRoom(RoomID);
                    }
                } else {
                    popup.displayError("Room ID does not exist");
                }

            }
        } else {
            popup.displayError("Room ID cannot leave blank!");
        }

    }

    //scene change
    @FXML
    void Main(ActionEvent event) throws IOException {
        scene.Main(managePane);
    }

    @FXML //open "make reservation" page
    void BookRoom(ActionEvent event) throws IOException {
//  
        scene.BookRoom(managePane);
    }

    @FXML//open "guest information" page
    void Guest(ActionEvent event) throws IOException {
        scene.Guest(managePane);
    }

    @FXML//open "view invoice" page
    void Invoice(ActionEvent event) throws IOException {
        scene.Invoice(managePane);
    }

    @FXML//open "manage reservation" page
    void ManageBooking(ActionEvent event) throws IOException {
        scene.ManageBooking(managePane);
    }

    @FXML
    void LogOut(ActionEvent event) throws IOException {
        if (popup.logoutConfirm()) {
            scene.LogOut(managePane);
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
