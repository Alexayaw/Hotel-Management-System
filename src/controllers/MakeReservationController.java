package controllers;

import Project.Booking;
import Project.Guest;
import Project.Invoice;
import Project.Popup;
import Project.Room;
import Project.SceneChange;
import Project.Validate;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MakeReservationController {

//Menu 
    @FXML
    private AnchorPane bookPane;
    @FXML
    private Button btnBook, btnManage, btnGuest, btnInvoice, btnLogout;
    @FXML
    private Pane expandedMenu;
//Menu

//*************Tab and Pane**************************
    @FXML
    private Pane queenPane, twinPane, FamPane, VIPPane, cashPane, cardPane, searchPane, nextPane, cancelPane;

    @FXML
    private TabPane bookTabPane;
    @FXML
    private Tab searchTab, bookTab, payTab, invoiceTab;
    //***************************************
//***********button*****************
    @FXML
    private RadioButton radCash, radCard;

    @FXML
    private ComboBox<String> cmbQueen, cmbTwin, cmbFamily, cmbVIP, cmbCard;

    @FXML
    private Button btnNextBook, btnNextPay, btnPaySubmit, btnSave, btnCancel, btnPrint, btnDisplayInvoice, btnNewGuest,btnOldGuest, btnNextOldGuest;
//***********end of button***********

    @FXML
    private TextField txtBookingID, txtBookingID1, txtRoomType, txtRoomID, txtDuration, txtGuestID, txtGuestID1, txtName, txtICPASS, txtMobileNo, txtEmail, txtAddress;
    @FXML
    private TextField txtRates, txtServiceTax, txtTourismTax, txtTotalFee, txtRoomPrice, txtTotalPaid, txtCardNo, txtCVC;
    @FXML
    private DatePicker dateCheckIn, dateCheckout;
    @FXML
    private TextArea txtInvoice;

    SceneChange scene = new SceneChange();
    Popup popup = new Popup();
    ToggleGroup grp1 = new ToggleGroup();
    Validate validate = new Validate();
    Room room = new Room();
    Booking book = new Booking();
    Guest guest = new Guest();
    Invoice invoice = new Invoice();
    ObservableList<String> listQueen, listTwin, listFamily, listVIP;
    int RoomPrice, stayDuration, Rates, TourismTax, TotalPaid, CardNo, CVC;
    double ServiceTax, TotalFee;
    String RoomType;
    String GuestName, IC_Pass, MobileNo, GuestEmail, GuestAddress;
    String guestCount;
    String BookingDate, CheckInDate, CheckOutDate;
    String PaymentMethod;
    int OLDGuestID, GuestID, BookingID, InvoiceID, RoomID;
    double TotalTax = ServiceTax + TourismTax;
    boolean IsOldGuest, IsSaved;

    @FXML
    void initialize() throws SQLException {
        cmbCard.getItems().addAll("Credit Card", "Debit Card");
        String bookCount = String.valueOf(book.getMaxBookingID());
        txtBookingID.setText("B00" + bookCount);
        txtBookingID1.setText(bookCount);
        guestCount = String.valueOf(guest.getMaxGuestID());
        txtGuestID.setText("G00" + guestCount);
//        txtGuestID1.setText(guestCount);
        btnNextOldGuest.setVisible(false);
      
    }

    @FXML
    void searchRoom(ActionEvent event) throws SQLException {
        //load available room ID from database into combo box
        listQueen = FXCollections.observableArrayList(room.getRoomID("Queen"));
        listTwin = FXCollections.observableArrayList(room.getRoomID("Twin"));
        listFamily = FXCollections.observableArrayList(room.getRoomID("Family"));
        listVIP = FXCollections.observableArrayList(room.getRoomID("VIP"));
        cmbQueen.setItems(listQueen);
        cmbTwin.setItems(listTwin);
        cmbFamily.setItems(listFamily);
        cmbVIP.setItems(listVIP);
//        ************************************************

        if (validate.datePicker(dateCheckIn) && validate.datePicker(dateCheckout)) { //success case
            LocalDate checkInDate = dateCheckIn.getValue();
            LocalDate checkOutDate = dateCheckout.getValue();
            Period period = Period.between(checkInDate, checkOutDate);

            if (period.getDays() <= 0) { //failed case
                popup.displayError("Invlid date entered, please make sure checkout date is at least one day later ");

            } else { //sucess case
                stayDuration = period.getDays();
                book.setStayDuration(stayDuration);
                txtDuration.setText(String.valueOf(stayDuration));
                scene.Enable(queenPane, twinPane, FamPane, VIPPane);

                scene.Enable(nextPane);
                scene.Enable(cancelPane);
                scene.Disable(searchPane);

            }

        } else {

            popup.displayError("Please enter Date ");
        }

    }

    @FXML
    void confirmPayDetails(ActionEvent event) {

        radCash.setToggleGroup(grp1);
        radCard.setToggleGroup(grp1); //set to togglegroup so that user can only choose either 1 rad button only
        if (radCash.isSelected()) {
            PaymentMethod = "Cash";
            cashPane.setDisable(false);
            cardPane.setDisable(true);
            txtCardNo.clear();
            txtCVC.clear();
            scene.cmbClear(cmbCard);
        } else {
            PaymentMethod = "Card";
            cardPane.setDisable(false);
            cashPane.setDisable(true);
            txtTotalPaid.clear();

        }

        btnPaySubmit.setDisable(false);

    }
//*********tab change*********************

    //*******search tab********
    @FXML
    void goToBookTab(ActionEvent event) throws SQLException { //from the next button in search pane
        if (validate.comboBox(cmbQueen) || validate.comboBox(cmbTwin) || validate.comboBox(cmbFamily) || validate.comboBox(cmbVIP)) {
            bookTab.setDisable(false);
          
            bookTabPane.getSelectionModel().select(bookTab);

            RoomID = Integer.valueOf(txtRoomID.getText());
            RoomType = room.getRoomType(RoomID);
            txtRoomType.setText(RoomType);
//            //update room status to booked
//            room.UpdateRoomStatusBooked(RoomID);
        } else {
            popup.displayError("Please select a room ID before booking");
        }
    }
//***********guest and booking tab***************************

    @FXML
    void goToPayment(ActionEvent event) throws SQLException { //from the next buttion in guest tab 

        if (validate.guestDetails(txtName, txtICPASS, txtMobileNo, txtEmail, txtAddress)) {

            GuestName = txtName.getText();
            IC_Pass = txtICPASS.getText();
            MobileNo = txtMobileNo.getText();
            GuestEmail = txtEmail.getText();
            GuestAddress = txtAddress.getText();

            if (guest.verifyGuestName(GuestName) && guest.verifyICPASS(IC_Pass)) { //see if name and ic exist 
                GuestID = guest.getMaxGuestID();
//                //insert guest details into database 
//                guest.insertGuestDetails(GuestID, GuestName, IC_Pass, MobileNo, GuestEmail, GuestAddress);

                //insert booking details 
                BookingID = book.getMaxBookingID();
                ZoneId zonedId = ZoneId.of("Asia/Kuala_Lumpur");

                //!!!!!!!!!!!!!!!!!!!!!!!!!DATES NEED TO BE RESOLVED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                LocalDate today = LocalDate.now(zonedId);
                java.sql.Date todayDate = Date.valueOf(today);
                System.out.println(todayDate);
                BookingDate = String.valueOf(today);
                CheckInDate = String.valueOf(dateCheckIn.getValue());
                CheckOutDate = String.valueOf(dateCheckout.getValue());
                System.out.println(CheckInDate);
                System.out.println(CheckOutDate);

//                book.insertBookDetails(BookingID, BookingDate, CheckInDate, CheckOutDate, RoomID, GuestID);
                btnPaySubmit.setDisable(true);

                //goes to payment page
                btnNextPay.setDisable(true);
                payTab.setDisable(false);
                bookTabPane.getSelectionModel().select(payTab);

                //display payment details 
                RoomPrice = room.getRoomPrice(RoomID);
                Rates = stayDuration * 350;
                ServiceTax = (RoomPrice + Rates) * 0.10;
                TourismTax = stayDuration * 10;
                TotalFee = RoomPrice + Rates + ServiceTax + TourismTax;

                txtRoomPrice.setText("RM" + String.valueOf(RoomPrice));
                txtRates.setText("RM" + String.valueOf(Rates));
                txtServiceTax.setText("RM" + String.valueOf(ServiceTax));
                txtTourismTax.setText("RM" + String.valueOf(TourismTax));
                txtTotalFee.setText("RM" + String.valueOf(TotalFee));
            }
        } else {
            popup.displayError("Cannot leave blank!");
        }
    }
//***************payment tab(submit button)****************************

    @FXML
    void goToInvoice(ActionEvent event) throws SQLException {
        InvoiceID = invoice.getMaxInvoiceID();

        RoomPrice = room.getRoomPrice(RoomID);
        Rates = stayDuration * 350;
        ServiceTax = (RoomPrice + Rates) * 0.10;
        TourismTax = stayDuration * 10;
        TotalTax = TourismTax + ServiceTax;
        TotalFee = RoomPrice + Rates + ServiceTax + TourismTax;
        System.out.println(TotalTax);
        //goes to invoice tab
        if (radCash.isSelected() && txtTotalPaid.getText().isEmpty() != true) {
            if (validate.inputValidate(txtTotalPaid)) {
                if (Integer.valueOf(txtTotalPaid.getText()) < TotalFee) {

                    popup.displayError("Paid amount is not sufficient!");

                } else {
                    //insert payment details 

                    popup.confirmation("Payment success!");
                    invoiceTab.setDisable(false);
                    bookTabPane.getSelectionModel().select(invoiceTab);
                    btnPaySubmit.setDisable(true);
                }

            }

        } else if (radCard.isSelected() && txtCardNo.getText().isEmpty() != true && validate.txtfield(txtCVC) && validate.comboBox(cmbCard)) {
            if (validate.inputValidate(txtCVC) && validate.inputValidate(txtCardNo)) {
                CardNo = Integer.valueOf(txtCardNo.getText());

                //insert payment details 
//                invoice.InsertInvoiceDetails(InvoiceID, PaymentMethod, CardNo, BookingID, RoomPrice, Rates, TotalTax, TotalFee);
                System.out.println(TotalFee);
                System.out.println(TotalTax);
                popup.confirmation("Payment success!");
                invoiceTab.setDisable(false);
                bookTabPane.getSelectionModel().select(invoiceTab);
            } else {
                popup.displayError("Please enter numeric value only!");

            }
        }

    }

    @FXML
    void displayInvoice(ActionEvent event) {
        String line1 = "𝗕𝗼𝗼𝗸𝗶𝗻𝗴 𝗜𝗗: " + BookingID + "                      InvoiceID: " + InvoiceID;
        String space = "";
        String line2 = "  ̲ ̲ ̲R̲o̲o̲m̲ 𝗥𝗼𝗼𝗺̲";
        String line3 = "RoomID: " + RoomID;
        String line4 = "Room type:" + RoomType;
        String line5 = "Room Charges: " + RoomPrice;
        String line61 = "𝗦𝘁𝗮𝘆 𝗗𝘂𝗿𝗮𝘁𝗶𝗼𝗻:" + stayDuration;
        String line6 = "Check-in Date: " + dateCheckIn.getValue();
        String line7 = "Check-out Date: " + dateCheckout.getValue();

        txtInvoice.setText(line1 + space + "\r\n" + line2 + "\r\n" + line3 + "\r\n" + line4 + "\r\n" + line5 + "\r\n" + line61 + "\r\n" + line7);

        btnDisplayInvoice.setVisible(false);
    }

    @FXML
    void registerOldGuest(ActionEvent event) throws SQLException {
        TextInputDialog dialog = new TextInputDialog("GuestIC");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Existing guest check-in");
        dialog.setContentText("Please enter guest IC:");

// Traditional way to get the response value.
        Optional<String> identification = dialog.showAndWait();
        String ID;
        if (identification.isPresent()) {
            System.out.println("Your name: " + identification.get());

            ID = identification.get();
            if (guest.verifyOldICPASS(ID)) {
                OLDGuestID = guest.getGuestID(ID);
                txtGuestID.setText("G00" + OLDGuestID);
                txtName.setText(guest.getGuestName(OLDGuestID));
                txtICPASS.setText(ID);
                txtMobileNo.setText(guest.getMobileNo(OLDGuestID));
                txtEmail.setText(guest.getGuestEmail(OLDGuestID));
                txtAddress.setText(guest.getGuestAddress(OLDGuestID));
                scene.Disable(txtName);
                scene.Disable(txtICPASS);
                scene.Disable(txtMobileNo);
                scene.Disable(txtEmail);
                scene.Disable(txtAddress);
                btnNextPay.setVisible(false);
                btnNextOldGuest.setVisible(true);
                btnNewGuest.setVisible(true);
            } else {
                scene.Enable(txtName);
                scene.Enable(txtICPASS);
                scene.Enable(txtMobileNo);
                scene.Enable(txtEmail);
                scene.Enable(txtAddress);
                txtName.clear();
                txtICPASS.clear();
                txtMobileNo.clear();
                txtEmail.clear();
                txtAddress.clear();
                txtGuestID.setText("G00" + guestCount);
                btnNextPay.setVisible(true);
                btnNextOldGuest.setVisible(false);
            }
        }

    }
    
    @FXML
    void clearOldGuest (ActionEvent event)
    {
         btnNewGuest.setVisible(false);
         btnNextPay.setDisable(false);
        scene.Enable(txtName);
                scene.Enable(txtICPASS);
                scene.Enable(txtMobileNo);
                scene.Enable(txtEmail);
                scene.Enable(txtAddress);
                txtName.clear();
                txtICPASS.clear();
                txtMobileNo.clear();
                txtEmail.clear();
                txtAddress.clear();
                txtGuestID.setText("G00" + guestCount);
                btnNextPay.setVisible(true);
                btnNextOldGuest.setVisible(false);
    }

    @FXML
    void goToPaymentOldGuest(ActionEvent event) throws SQLException {

        //confirm is old Guest
        IsOldGuest = true;
        //insert booking details 

        BookingID = book.getMaxBookingID();
        ZoneId zonedId = ZoneId.of("Asia/Kuala_Lumpur");
        LocalDate today = LocalDate.now(zonedId);
        BookingDate = String.valueOf(today);
        CheckInDate = String.valueOf(dateCheckIn.getValue());
        CheckOutDate = String.valueOf(dateCheckout.getValue());

        //goes to payment page
        btnNextPay.setDisable(true);
        payTab.setDisable(false);
        bookTabPane.getSelectionModel().select(payTab);

        //display payment details 
        RoomPrice = room.getRoomPrice(RoomID);
        Rates = stayDuration * 350;
        ServiceTax = (RoomPrice + Rates) * 0.10;
        TourismTax = stayDuration * 10;
        TotalFee = RoomPrice + Rates + ServiceTax + TourismTax;

        txtRoomPrice.setText("RM" + String.valueOf(RoomPrice));
        txtRates.setText("RM" + String.valueOf(Rates));
        txtServiceTax.setText("RM" + String.valueOf(ServiceTax));
        txtTourismTax.setText("RM" + String.valueOf(TourismTax));
        txtTotalFee.setText("RM" + String.valueOf(TotalFee));
    }

    @FXML
    void InsertAllDetails(ActionEvent event) throws SQLException {
        if (popup.ActionConfirm("Confirm Save? Changes can still be made")) {
            //insert booking details
            if (!IsOldGuest) {
                //insert guest details into database 
                guest.insertGuestDetails(GuestID, GuestName, IC_Pass, MobileNo, GuestEmail, GuestAddress);
                book.insertBookDetails(BookingID, BookingDate, CheckInDate, CheckOutDate, RoomID, GuestID);

            } else {
                guest.updateGuestStatusActive(OLDGuestID);
                book.insertBookDetails(BookingID, BookingDate, CheckInDate, CheckOutDate, RoomID, OLDGuestID);
            }

            //update room status to booked
            room.UpdateRoomStatusBooked(RoomID);
            //insert invoice details
            invoice.InsertInvoiceDetails(InvoiceID, PaymentMethod, CardNo, BookingID, RoomPrice, Rates, TotalTax, TotalFee);
            
             //turn saved status on 
        IsSaved = true;
        //Increment ID
         String bookCount = String.valueOf(book.getMaxBookingID());
        txtBookingID.setText("B00" + bookCount);
        txtBookingID1.setText(bookCount);
        guestCount = String.valueOf(guest.getMaxGuestID());
        txtGuestID.setText("G00" + guestCount);
        //reset tab pane
        searchTab.setDisable(false);
        bookTabPane.getSelectionModel().select(searchTab);
        payTab.setDisable(true);
        bookTab.setDisable(true);
        invoiceTab.setDisable(true);
        //reset search tab 
        dateCheckIn.getEditor().clear();
        dateCheckout.getEditor().clear();
        
        scene.Enable(searchPane);
        scene.cmbClear(cmbQueen);
        scene.cmbClear(cmbTwin);
        scene.cmbClear(cmbFamily);
        scene.cmbClear(cmbVIP);
        scene.Disable(nextPane);
        scene.Disable(cancelPane);
        //reset booking tab
         scene.Enable(txtName);
                scene.Enable(txtICPASS);
                scene.Enable(txtMobileNo);
                scene.Enable(txtEmail);
                scene.Enable(txtAddress);
                txtName.clear();
                txtICPASS.clear();
                txtMobileNo.clear();
                txtEmail.clear();
                txtAddress.clear();
                txtGuestID.setText("G00" + guestCount);
                btnNextPay.setVisible(true);
                btnNextPay.setDisable(false);
                btnNextOldGuest.setVisible(false);
                  btnNewGuest.setVisible(false);
                //reset Payment Tab
                btnPaySubmit.setDisable(false);
                txtTotalPaid.clear();
                txtCardNo.clear();
                txtCVC.clear();
                scene.cmbClear(cmbCard);
                //resetInvoiceTab
                txtInvoice.clear();
                btnDisplayInvoice.setVisible(true);
        //refresh content
        }
        
       
        
    }

//*********end of tab change*********************
    @FXML
    void QSelected(ActionEvent event) {
        scene.Disable(queenPane, twinPane, FamPane, VIPPane);

        txtRoomID.setText(String.valueOf(cmbQueen.getSelectionModel().getSelectedItem()));

    }

    @FXML
    void TSelected(ActionEvent event) {
        scene.Disable(twinPane, queenPane, FamPane, VIPPane);
        txtRoomID.setText(String.valueOf(cmbTwin.getSelectionModel().getSelectedItem()));

    }

    @FXML
    void FamSelected(ActionEvent event) {
        scene.Disable(FamPane, queenPane, twinPane, VIPPane);
        txtRoomID.setText(String.valueOf(cmbFamily.getSelectionModel().getSelectedItem()));
    }

    @FXML
    void Vselected(ActionEvent event) {
        scene.Disable(VIPPane, queenPane, FamPane, twinPane);
        txtRoomID.setText(String.valueOf(cmbVIP.getSelectionModel().getSelectedItem()));
    }

    @FXML
    void cancelSelection(ActionEvent event) {
        if (validate.comboBox(cmbQueen) || validate.comboBox(cmbTwin) || validate.comboBox(cmbFamily) || validate.comboBox(cmbVIP)) {
            scene.cmbClear(cmbQueen);
            scene.cmbClear(cmbTwin);
            scene.cmbClear(cmbFamily);
            scene.cmbClear(cmbVIP);
            scene.Enable(queenPane, twinPane, FamPane, VIPPane);
            scene.Enable(searchPane);
        }

    }

    //**************scene change*****************************
    @FXML
    void Main(ActionEvent event) throws IOException {
        if (bookTab.isSelected() || payTab.isSelected() || invoiceTab.isSelected()) {
            if (popup.ActionConfirm("Incomplete booking process detected .Any unsaved changes will be discarded once you leave this page. Continue?")) {
                scene.Main(bookPane);
            }

        } else if (IsSaved) {

            scene.Main(bookPane);
        } else {
            scene.Main(bookPane);
        }
    }

    @FXML //open "make reservation" page
    void BookRoom(ActionEvent event) throws IOException {
//         if (bookTab.isSelected() || payTab.isSelected() || invoiceTab.isSelected() ) {
        if (popup.ActionConfirm("Incomplete booking process detected .Any unsaved changes will be discarded once you leave this page. Continue?")) 
        {
            scene.BookRoom(bookPane);
        }

        else if (IsSaved) {

            scene.BookRoom(bookPane);
     }

    
        else {
            scene.BookRoom(bookPane);
    }

}

@FXML//open "guest information" page
    void Guest(ActionEvent event) throws IOException {
         if (bookTab.isSelected() || payTab.isSelected() || invoiceTab.isSelected()) {
            if (popup.ActionConfirm("Incomplete booking process detected .Any unsaved changes will be discarded once you leave this page. Continue?")) {
                scene.Guest(bookPane);
            }

        } else if (IsSaved) {

           scene.Guest(bookPane);
        } else {
             scene.Guest(bookPane);
        }
        
    }

    @FXML//open "view invoice" page
    void Invoice(ActionEvent event) throws IOException {
         if (bookTab.isSelected() || payTab.isSelected() || invoiceTab.isSelected()) {
            if (popup.ActionConfirm("Incomplete booking process detected .Any unsaved changes will be discarded once you leave this page. Continue?")) {
                scene.Invoice(bookPane);
            }

        } else if (IsSaved) {

            scene.Invoice(bookPane);
        } else {
             scene.Invoice(bookPane);
        }
       
    }

    @FXML//open "manage reservation" page
    void ManageBooking(ActionEvent event) throws IOException {
     if (bookTab.isSelected() || payTab.isSelected() || invoiceTab.isSelected()) {
            if (popup.ActionConfirm("Incomplete booking process detected .Any unsaved changes will be discarded once you leave this page. Continue?")) {
                scene.ManageBooking(bookPane);
            }

        } else if (IsSaved) {

            scene.ManageBooking(bookPane);
        } else {
            scene.ManageBooking(bookPane);
        }
       
    }

    @FXML
    void LogOut(ActionEvent event) throws IOException {
        if (popup.logoutConfirm()) {
            scene.LogOut(bookPane);
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
    //**********end of scene change***************************
}
