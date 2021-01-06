/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.sql.*;
import javafx.scene.control.TextField;
import Project.Popup;
import javafx.scene.control.TableView;

/**
 *
 * @author user
 */
public class Guest {

    private int GuestID;
    private String GuestName;
    private String ICPASS;
    private String MobileNo;
    private String GuestEmail;
    private String Address;
    Table table = new Table();
    Popup popup = new Popup();
    String url = "jdbc:sqlite:C:/sqlite1/hotel.db";

    public void updateGuest(int GuestID, String GuestName, String IC_PASS, String MobileNo, String Email, String Address) throws SQLException {

        Connection con = DriverManager.getConnection(url);
        try {
            String query = "UPDATE [Guest] SET GuestName = ? ,[IC_Pass] = ? ,MobileNo=?,GuestEmail=?,GuestAddress=? WHERE GuestID=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, GuestName);
            statement.setString(2, IC_PASS);
            statement.setString(3, MobileNo);
            statement.setString(4, Email);
            statement.setString(5, Address);
            statement.setInt(6, GuestID);

            statement.executeUpdate();
            System.out.println("Succesfully updated guest info");
        } catch (SQLException e) {

        } finally {
            con.close();
        }
    }

    public void insertGuestDetails(int GuestID, String GuestName, String ICPASS, String MobileNo, String GuestEmail, String Address) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        PreparedStatement statement = null;
        try {
            String query = "INSERT INTO Guest (GuestID,GuestName,[IC_Pass],MobileNo,GuestEmail,GuestAddress) VALUES (?,?,?,?,?,?)";
            statement = con.prepareStatement(query);

            statement.setInt(1, GuestID);
            statement.setString(2, GuestName);
            statement.setString(3, ICPASS);
            statement.setString(4, MobileNo);
            statement.setString(5, GuestEmail);
            statement.setString(6, Address);

            statement.executeUpdate();
            System.out.println("Insert Guest details success!");
        } catch (SQLException e) {
            System.out.println("Error while trying to insert guest details");
        } finally {

            statement.close();
            con.close();
        }
    }

    public void setICPASS() {

    }

    public void setMobileNo() {

    }

    public void setEmail() {

    }

    public void setAddress() {

    }

    public int getMaxGuestID() throws SQLException {
        int MaxID = 0;

        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT MAX(GuestID)+1 FROM Guest";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                MaxID = rs.getInt(1);
            }

        } catch (SQLException e) {

        } finally {
            con.close();
            return MaxID;
        }

    }

    public boolean verifyGuestName(String GuestName) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT GuestName FROM Guest Where GuestName = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, GuestName);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) { //if name does not exist in db, return true
                con.close();
                return true;

            } else {
                con.close();
                popup.displayError("Guest: " + GuestName + " already exist in database! Please use 'Exisitng Guest' option to check in for exisitng customer.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Failed to verify name");
        }
        return false;

    }

    public boolean verifyICPASS(String IC_Pass) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT IC_Pass FROM Guest Where IC_Pass = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, IC_Pass);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) { //if name does not exist in db, return true
                con.close();
                return true;

            } else {
                con.close();
                popup.displayError("IC/Passport: " + IC_Pass + " already exist in database! Please use 'Exisitng Guest' option to check in for exisitng customer.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Failed to verify name");
        }
        return false;

    }

    public boolean verifyOldICPASS(String IC_Pass) throws SQLException { //used to verify existing's guest ic and passporty
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT IC_Pass FROM Guest Where IC_Pass = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, IC_Pass);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) { //if name does not exist in db, return true
                con.close();
                popup.confirmation("Found Guest!");
                return true;

            } else {
                con.close();
                popup.displayError("IC/Passport: " + IC_Pass + " does not exist in database, please register guest at the 'guest details' section.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Failed to verify name");
        }
        return false;
    }

    public int getGuestID(String IC_Pass) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        int GuestID = 0;

        try {
            String query = "SELECT GuestID  FROM Guest Where IC_Pass = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, IC_Pass);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) { //if name does not exist in db, return true

                GuestID = rs.getInt(1);
                return GuestID;

            } else {
                con.close();
                popup.displayError("IC/Passport: " + IC_Pass + " does not exist in database, please register guest at the 'guest information' section.");

            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve GuestID");
        } finally {
            con.close();
            return GuestID;
        }

    }

    public int getGuestID(int BookingID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        int GuestID = 0;

        try {
            String query = "SELECT GuestID  FROM Booking Where BookingID=?  ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, BookingID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) { //if name does not exist in db, return true

                GuestID = rs.getInt(1);
                return GuestID;

            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve GuestID");
        } finally {
            con.close();
            return GuestID;
        }

    }

    public String getGuestName(int GuestID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        String GuestName = null;

        try {
            String query = "SELECT GuestName  FROM Guest Where GuestID = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, GuestID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) { //if name does not exist in db, return true

                GuestName = rs.getString(1);
                return GuestName;

            }

        } catch (SQLException e) {
            System.out.println("Failed to retriece Guest Name");
        } finally {
            con.close();
            return GuestName;
        }

    }

    public String getMobileNo(int GuestID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        String MobileNo = null;

        try {
            String query = "SELECT MobileNo  FROM Guest Where GuestID = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, GuestID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                MobileNo = rs.getString(1);
                return MobileNo;

            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve MobileNo");
        } finally {
            con.close();
            return MobileNo;
        }

    }

    public String getGuestEmail(int GuestID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        String GuestEmail = null;

        try {
            String query = "SELECT GuestEmail  FROM Guest Where GuestID = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, GuestID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                GuestEmail = rs.getString(1);
                return GuestEmail;

            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve Guest Email");
        } finally {
            con.close();
            return GuestEmail;
        }

    }

    public String getGuestAddress(int GuestID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        String GuestAddress = null;

        try {
            String query = "SELECT GuestAddress FROM Guest Where GuestID = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, GuestID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                GuestAddress = rs.getString(1);
                return GuestAddress;

            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve MobileNo");
        } finally {
            con.close();
            return GuestAddress;
        }

    }

    public void DisplayGuestDetails(TableView tableview) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT * FROM Guest";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tableview);

        } catch (SQLException e) {
            System.out.println("Failed to load Guest data into booking table");
        } finally {
            con.close();
        }

    }

    public void DisplayActiveGuestDetails(TableView tableview) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT * FROM Guest Where GuestStatus ='ACTIVE'";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tableview);

        } catch (SQLException e) {
            System.out.println("Failed to load Guest data into booking table");
        } finally {
            con.close();
        }

    }

    public void DisplayCheckedOutGuestDetails(TableView tableview) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT * FROM Guest WHERE [GuestStatus] = 'INACTIVE' ";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tableview);

        } catch (SQLException e) {
            System.out.println("Failed to load Guest data into booking table");
        } finally {
            con.close();
        }

    }

    public void SearchGuestDetails(TableView tableview, String IC_PASS) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT * FROM Guest WHERE [IC_PASS] =?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, IC_PASS);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tableview);

        } catch (SQLException e) {
            System.out.println("Failed to load Guest data into booking table");
        } finally {
            con.close();
        }

    }

    //CheckOut
    public void updateGuestStatusInactive(int GuestID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "UPDATE [Guest] SET GuestStatus = 'INACTIVE' WHERE GuestID = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, GuestID);
            statement.executeUpdate();
            System.out.println("Update Guest status successful");
        } catch (SQLException e) {
            System.out.println("Failed to update Guest status");
        } finally {
            con.close();
        }
    }
       public void updateGuestStatusActive(int GuestID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "UPDATE [Guest] SET GuestStatus = 'ACTIVE' WHERE GuestID = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, GuestID);
            statement.executeUpdate();
            System.out.println("Update Guest status successful");
        } catch (SQLException e) {
            System.out.println("Failed to update Guest status");
        } finally {
            con.close();
        }
    }
         public int getActiveGuestCount() throws SQLException {
        Connection con = DriverManager.getConnection(url);
        int GuestCount = 0 ;
        try {
            String query = "SELECT COUNT(GuestID) AS GuestCount FROM Guest WHERE GuestStatus = 'ACTIVE' ";
            PreparedStatement statement = con.prepareStatement(query);
          
            ResultSet rs= statement.executeQuery();
           if(rs.next()){
           GuestCount = rs.getInt("GuestCount");
           }
           return GuestCount;
        } catch (SQLException e) {
            System.out.println("Failed to get guest count");
        } finally {
            con.close();
            return GuestCount;
        }
    }
       
       
    
    

//    public String getName() {
//
//    }
//
//    public String getICPASS() {
//
//    }
//
//    public int getMobileNo() {
//
//    }
//
//    public String getEmail() {
//
//    }
//
//    public String getAddress() {
//
//    }
    public static void main(String[] args) throws SQLException {
        Guest guest = new Guest();
      

    }

}
