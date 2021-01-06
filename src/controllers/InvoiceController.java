package controllers;

import Project.Invoice;
import Project.Popup;
import Project.SceneChange;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class InvoiceController {

    SceneChange scene = new SceneChange();

    @FXML
    private AnchorPane invoicePane;

    @FXML
    private Button btnBook, btnManage, btnGuest, btnInvoice;
    @FXML
    private Pane expandedMenu;
    @FXML
    private TableView tbvInvoice;
    @FXML
    private ComboBox<String> cmbInvoiceFilter;

    Invoice invoice = new Invoice();

    Popup popup = new Popup();
    String InvoiceFilter;

    @FXML
    void initialize() throws SQLException {
        invoice.DisplayInvoiceDetails(tbvInvoice);
        cmbInvoiceFilter.getItems().addAll("All", "Active", "Checked-Out");

    }

    @FXML
    void RefreshInvoice(ActionEvent event) throws SQLException {
        tbvInvoice.getColumns().clear();
        invoice.DisplayInvoiceDetails(tbvInvoice);
    }

    @FXML
    void InvoiceFilter(ActionEvent event) throws SQLException {
        InvoiceFilter = cmbInvoiceFilter.getSelectionModel().getSelectedItem();
        switch (InvoiceFilter) {
            case "Active":
                tbvInvoice.getColumns().clear();
                invoice.DisplayActiveInvoiceDetails(tbvInvoice);
                break;
            case "Checked-Out":
                tbvInvoice.getColumns().clear();
                invoice.DisplayCheckOutInvoiceDetails(tbvInvoice);
                break;
            default:
                tbvInvoice.getColumns().clear();
                invoice.DisplayInvoiceDetails(tbvInvoice);

        }
    }

    //scene change
    @FXML
    void Main(ActionEvent event) throws IOException {
        scene.Main(invoicePane);
    }

    @FXML //open "make reservation" page
    void BookRoom(ActionEvent event) throws IOException {
//         
        scene.BookRoom(invoicePane);
    }

    @FXML//open "guest information" page
    void Guest(ActionEvent event) throws IOException {
        scene.Guest(invoicePane);
    }

    @FXML//open "view invoice" page
    void Invoice(ActionEvent event) throws IOException {
        scene.Invoice(invoicePane);
    }

    @FXML//open "manage reservation" page
    void ManageBooking(ActionEvent event) throws IOException {
        scene.ManageBooking(invoicePane);
    }

    @FXML
    void LogOut(ActionEvent event) throws IOException {
        if (popup.logoutConfirm()) {
            scene.LogOut(invoicePane);
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
