/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.sql.*;
import java.time.LocalDate;
import javafx.scene.control.TableView;

/**
 *
 * @author user
 */
public class Invoice {

    private int InvoiceID;

    private String PaymentMethod;
    private int CardNum;
    private int BookingID;
    private int AddOnFee;
    private int TotalRates;
    private int TotalTax;
    private int TotalPaid;
    private String InvoiceStatus;
    String url = "jdbc:sqlite:C:/sqlite1/hotel.db";
    Table table = new Table();

    public void InsertInvoiceDetails(int InvoiceID, String PaymentMethod, int CardNum, int BookingID, int AddOnFee, int TotalRates, double TotalTax, double TotalPaid) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "INSERT INTO Invoice (InvoiceID,PaymentMethod,CardNum,BookingID,AddOnFee,TotalRates,TotalTax,TotalPaid) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, InvoiceID);
            statement.setString(2, PaymentMethod);
            statement.setInt(3, CardNum);
            statement.setInt(4, BookingID);
            statement.setInt(5, AddOnFee);
            statement.setInt(6, TotalRates);
            statement.setDouble(7, TotalTax);
            statement.setDouble(8, TotalPaid);

            statement.executeUpdate();
            System.out.println("Insert Invoice/payment details success!");
        } catch (SQLException e) {
            System.out.println("Failed to insert invoice/payment details!");
        } finally {
            con.close();
        }

    }
    public int getInvoiceID(int BookingID) throws SQLException{
    Connection con = DriverManager.getConnection(url);
        int InvoiceID = 0;
        try {
           
            String query = "SELECT InvoiceID FROM Invoice i INNER JOIN Booking i ON i.BookingID = b.BookingID WHERE b.BookingID = ?";
                    
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,BookingID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                InvoiceID = rs.getInt(1);
            }
            return InvoiceID;
            
        } catch (SQLException e) {

        } finally {
            con.close();
            return InvoiceID;
        }
    }
    
    public String getPaymentMethod(int InvoiceID) throws SQLException{
    Connection con = DriverManager.getConnection(url);
        String PaymentMethod = null;
        try {
           
            String query = "SELECT PaymentMethod From Invoice Where InvoiceID = ?";
                    
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,InvoiceID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                InvoiceID = rs.getInt(1);
            }
            return PaymentMethod;
        } catch (SQLException e) {

        } finally {
            con.close();
            return PaymentMethod;
        }
    
    }
    
    
     public int getTotalEarning() throws SQLException{
        
    
    Connection con = DriverManager.getConnection(url);
    int TotalEarn = 0;
    
    try{
        String query="SELECT SUM(TotalPaid) AS TotalEarn FROM Invoice WHERE InvoiceStatus = \"VALID\"";
        PreparedStatement statement = con.prepareStatement(query);
      
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
        TotalEarn = rs.getInt("TotalEarn");
        }
    
    }catch(SQLException e){
        System.out.println("Unable to get TotalEarning");
    
    }finally{
    con.close();
    return TotalEarn;
    }
    }
//    public String get(String InvoiceID) throws SQLException{
//    Connection con = DriverManager.getConnection(url);
//        String PaymentMethod = null;
//        try {
//           
//            String query = "SELECT PaymentMethod From Invoice Where InvoiceID = ?";
//                    
//            PreparedStatement statement = con.prepareStatement(query);
//            statement.setString(1,InvoiceID);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()) {
//                InvoiceID = rs.getString(1);
//            }
//            return PaymentMethod;
//        } catch (SQLException e) {
//
//        } finally {
//            con.close();
//            return PaymentMethod;
//        }
//    
//    }

    public void DisplayInvoiceDetails(TableView tableview) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT i.InvoiceID,b.BookingStatus,b.RoomID,i.AddOnFee AS [Room Price],b.CheckInDate,b.CheckOutDate,b.StayDuration,g.GuestName,g.IC_Pass,g.MobileNo,g.GuestEmail,g.GuestAddress,i.TotalRates,i.TotalPaid AS[Total Paid(After Tax)],i.PaymentMethod,i.CardNum\n" +
"FROM  (\n" +
"( Guest g INNER JOIN Booking b ON g.GuestID=b.GuestID )\n" +
" INNER JOIN Invoice i on b.BookingID = I.BookingID \n" +
"); ";
            PreparedStatement statement = con.prepareStatement(query);
           ResultSet rs = statement.executeQuery();
           table.LoadData(rs, tableview);
//            statement.setString();
        } catch (SQLException e) {
            System.out.println("Failed to load invoice data");
        } finally {
            con.close();

        }
    }
      public void DisplayActiveInvoiceDetails(TableView tableview) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT i.InvoiceID,b.BookingStatus,b.RoomID,i.AddOnFee AS [Room Price],b.CheckInDate,b.CheckOutDate, b.StayDuration,g.GuestName,g.IC_Pass,g.MobileNo,g.GuestEmail,g.GuestAddress,i.TotalRates,i.TotalPaid AS[Total Paid(After Tax)],i.PaymentMethod,i.CardNum FROM  ( ( Guest g INNER JOIN Booking b ON g.GuestID=b.GuestID )INNER JOIN Invoice i on b.BookingID = I.BookingID )WHERE b.BookingStatus = 'ACTIVE'";
            PreparedStatement statement = con.prepareStatement(query);
           ResultSet rs = statement.executeQuery();
           table.LoadData(rs, tableview);
//            statement.setString();
        } catch (SQLException e) {
            System.out.println("Failed to load invoice data");
        } finally {
            con.close();

        }
    }
      
          public void DisplayCheckOutInvoiceDetails(TableView tableview) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT i.InvoiceID,b.BookingStatus,b.RoomID,i.AddOnFee AS [Room Price],b.CheckInDate,b.CheckOutDate,b.StayDuration,g.GuestName,g.IC_Pass,g.MobileNo,g.GuestEmail,g.GuestAddress,i.TotalRates,i.TotalPaid AS[Total Paid(After Tax)],i.PaymentMethod,i.CardNum FROM  ( ( Guest g INNER JOIN Booking b ON g.GuestID=b.GuestID )INNER JOIN Invoice i on b.BookingID = I.BookingID )WHERE b.BookingStatus = 'CHECKED-OUT'";
            PreparedStatement statement = con.prepareStatement(query);
           ResultSet rs = statement.executeQuery();
           table.LoadData(rs, tableview);
//            statement.setString();
        } catch (SQLException e) {
            System.out.println("Failed to load invoice data");
        } finally {
            con.close();

        }
    }
      
      
    
    
//      public void Search

    public int getMaxInvoiceID() throws SQLException {
        Connection con = DriverManager.getConnection(url);
        int Max = 0;
        try {
            String query = "SELECT MAX(InvoiceID)+1 FROM Invoice";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Max = rs.getInt(1);
            }
        } catch (SQLException e) {

        } finally {
            con.close();
            return Max;
        }

    }
}
