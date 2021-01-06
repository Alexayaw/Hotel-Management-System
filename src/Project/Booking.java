/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *
 * @author user
 */
public class Booking {

    private int BookingID;
    private Date BookingDate;
    private Date CheckInDate;
    private Date CheckOutDate;
    private int RoomID;
    private int GuestID;
    private static int StayDuration;
    String url = "jdbc:sqlite:C:/sqlite1/hotel.db";
    TableView tableview = new TableView();
    Table table = new Table();
    Popup popup = new Popup();
    private ObservableList<ObservableList> data;
    


    public void insertBookDetails(int BookingID, String BookingDate, String CheckInDate, String CheckOutDate, int RoomID, int GuestID) throws SQLException {
DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "INSERT INTO Booking (BookingID,BookingDate,CheckInDate,CheckOutDate,StayDuration,RoomID,GuestID) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, BookingID);
            statement.setString(2, BookingDate);
            statement.setString(3, CheckInDate);
            statement.setString(4, CheckOutDate);
            statement.setInt(5,StayDuration);
            statement.setInt(6, RoomID);
            statement.setInt(7, GuestID);
            statement.executeUpdate();
            System.out.println("Insert Booking details success!");
        } catch (SQLException e) {
            System.out.println("Failed to insert booking details");
        } finally {
            con.close();
        }
    }

    public static void main(String[] args) throws SQLException {
        Booking book = new Booking();

    }
    
    public void setStayDuration(int stayDuration){
    this.StayDuration = stayDuration;
    }

    public int getMaxBookingID() throws SQLException {
        int MaxID = 0;
        Connection con = DriverManager.getConnection(url);
        int count;
        ResultSet rs;
        try {

            String query = "SELECT MAX(BookingID)+1 FROM Booking";
            PreparedStatement statement = con.prepareStatement(query);
            rs = statement.executeQuery();
            while (rs.next()) {
                MaxID = rs.getInt(1);
            }
            count = Integer.valueOf(MaxID);

        } catch (SQLException e) {

        } finally {

            con.close();
            return MaxID;
        }

    }
    
    public int getTodayCheckOut() throws SQLException{
        
    
    Connection con = DriverManager.getConnection(url);
    int CheckOutCount = 0;
    LocalDate todayDate = LocalDate.now();
    String date = String.valueOf(todayDate);
    try{
        String query="SELECT Count(BookingID) AS CheckOutCount FROM Booking WHERE CheckOutDate = ?";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, date);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
        CheckOutCount = rs.getInt("CheckOutCount");
        }
    
    }catch(SQLException e){
        System.out.println("Unable to get Check Out Count for today");
    
    }finally{
    con.close();
    return CheckOutCount;
    }
    }

    public boolean verifyBookingID(int BookingID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT BookingID FROM Booking WHERE BookingID=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, BookingID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) { //if exist then
                con.close();
                return true;
            } else {
                con.close();
                popup.displayError("Booking ID does not exist!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Failed to verify BookingID");
        }
        return false;
    }

    public void DisplayAllBookingDetails(TableView tableview) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT * FROM Booking";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tableview);

        } catch (SQLException e) {
            System.out.println("Failed to load data into booking table");
        } finally {
            con.close();
        }

    }
    public void DisplayActiveBookingDetails(TableView tableview) throws SQLException{
    Connection con = DriverManager.getConnection(url);
            try{
                String query="SELECT * FROM Booking Where BookingStatus = 'ACTIVE'";
                PreparedStatement statement = con.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                table.LoadData(rs, tableview);
            }catch(SQLException e){
                 System.out.println("Failed to load data into booking table");
            }finally{
                con.close();
            }
            
    
    }
     public void DisplayCheckedOutBookingDetails(TableView tableview) throws SQLException{
    Connection con = DriverManager.getConnection(url);
            try{
                String query="SELECT * FROM Booking Where BookingStatus = 'CHECKED-OUT'";
                PreparedStatement statement = con.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                table.LoadData(rs, tableview);
            }catch(SQLException e){
                 System.out.println("Failed to load data into booking table");
            }finally{
                con.close();
            }
            
    
    }
      public void DisplayCancelledBookingDetails(TableView tableview) throws SQLException{
    Connection con = DriverManager.getConnection(url);
            try{
                String query="SELECT * FROM Booking Where BookingStatus = 'CANCELLED'";
                PreparedStatement statement = con.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                table.LoadData(rs, tableview);
            }catch(SQLException e){
                 System.out.println("Failed to load data into booking table");
            }finally{
                con.close();
            }
            
    
    }
    
    public void searchBookingID(TableView tableview, int BookingID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT * FROM Booking WHERE BookingID = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, BookingID);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tableview);

        } catch (SQLException e) {
            System.out.print("Failed to retrieve data");
        } finally {
            con.close();
        }

    }

    public void CheckOut(int BookingID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query="UPDATE [Booking] SET BookingStatus = 'CHECKED-OUT' WHERE BookingID = ? " ;
            PreparedStatement statement=con.prepareStatement(query);
            statement.setInt(1, BookingID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to checkOut Room");
        } finally {
            con.close();
        }
    }
    
    public void DeleteBooking(int BookingID) throws SQLException{
    Connection con = DriverManager.getConnection(url);
        try {
            String query="UPDATE [Booking] SET BookingStatus = 'CANCELLED' WHERE BookingID = ? " ;
            PreparedStatement statement=con.prepareStatement(query);
            statement.setInt(1, BookingID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to cancel booking");
        } finally {
            con.close();
        }
    
    }

}
