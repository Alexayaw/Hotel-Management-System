/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.sql.*;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 *
 * @author user
 */
public class Room {

    public int RoomID;
    public String RoomStatus;
    public static String RoomType;
    String url = "jdbc:sqlite:C:/sqlite1/hotel.db";
    Table table = new Table();
    private ObservableList<ObservableList> data;
    Popup popup = new Popup();

    public ArrayList getRoomID(String RoomType) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        ArrayList<Integer> list = new ArrayList<Integer>();
        try {
//        RoomType = "Twin";
            String query = "SELECT RoomID FROM Room WHERE RoomType = ? AND RoomStatus = 'AVAILABLE' ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, RoomType);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) { //iterate over the resulted set 

                list.add(rs.getInt("RoomID"));

            }

            return list;
        } catch (SQLException e) {

        } finally {

            con.close();
            return list;

        }
    }
    
    public int getRoomCount() throws SQLException{
    Connection con = DriverManager.getConnection(url);
    int RoomCount=0;
    try{
        String query ="SELECT COUNT(RoomID) AS RoomCount FROM Room WHERE RoomStatus = 'AVAILABLE' ";
        PreparedStatement statement= con.prepareStatement(query);
        ResultSet rs=statement.executeQuery();
        if(rs.next()){
        RoomCount = rs.getInt("RoomCount");
        }
        return RoomCount;
    }catch(SQLException e)
    {
    }finally{
    con.close();
    return RoomCount;
    }
    }
    

    public int getRoomPrice(int RoomID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        int Price = 0;
        try {
//        RoomType = "Twin";
            String query = "SELECT RoomPrice FROM Room WHERE RoomID = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, RoomID);
            ResultSet rs = statement.executeQuery();
            Price = rs.getInt("RoomPrice");
            return Price;
        } catch (SQLException e) {

        } finally {

            con.close();
            return Price;
        }
    }

    public String getRoomType(int RoomID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        String type = null;
//        RoomType = "Twin";
        try {
            String query = "SELECT RoomType FROM Room WHERE RoomID = ? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, RoomID);
            ResultSet rs = statement.executeQuery();
            type = rs.getString("RoomType");
            return type;
        } catch (SQLException e) {

        } finally {
            con.close();
            return type;
        }
    }

    public void DisplayRoomDetails(TableView tbv) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT * FROM Room  ";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tbv);

        } catch (SQLException e) {
            System.out.println("Failed to display room details");
        } finally {
            con.close();
        }

    }

    public void SearchRoomDetails(TableView tbv, int RoomID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT * FROM Room WHERE RoomID=? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, RoomID);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tbv);
            
        } catch (SQLException e) {
            System.out.println("Failed to display room details");
        } finally {
            con.close();
        }

    }

    public boolean VerifyRoomID(int RoomID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT RoomID From Room Where RoomID=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, RoomID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                con.close();
                return true;
            }
        } catch (SQLException e) {

        } finally {
            con.close();
        }
       
        return false;

    }

    public int getRoomID(int BookingID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT RoomID From Booking Where BookingID=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, BookingID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                RoomID = rs.getInt("RoomID");
                con.close();

                return RoomID;
            }
        } catch (SQLException e) {

        } finally {
            con.close();
            return RoomID;
        }

    }

    public void UpdateRoomStatusBooked(int RoomID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "UPDATE [Room] SET [RoomStatus] = 'BOOKED' WHERE [RoomID]=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, RoomID);
            statement.executeUpdate();
            System.out.println("Update room" + RoomID + "status to booked.");

        } catch (SQLException e) {
            System.out.println("Failed to update room status to booked");

        } finally {
            con.close();
        }

    }

    public void UpdateRoomStatusAvailable(int RoomID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "UPDATE [Room] SET [RoomStatus] = 'AVAILABLE' WHERE [RoomID]=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, RoomID);
            statement.executeUpdate();
            System.out.println("Update room" + RoomID + "status to Available.");

        } catch (SQLException e) {
            System.out.println("Failed to update room status to Available");

        } finally {
            con.close();
        }

    }

    public void AddRoom(int RoomID, String RoomType,int RoomPrice) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "INSERT INTO [ROOM](RoomID,RoomStatus,RoomType,RoomPrice) VALUES(?,'Available',?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, RoomID);
            statement.setString(2, RoomType);
            statement.setInt(3, RoomPrice);
            statement.executeUpdate();
           
        } catch (SQLException e) {
            System.out.println("Failed to create room");

        } finally {
            con.close();
        }
    }
    
    public void DeleteRoom (int RoomID) throws SQLException{
     Connection con = DriverManager.getConnection(url);
        try {
            String query = "DELETE FROM [Room] WHERE [RoomID] = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, RoomID);
            statement.executeUpdate();
            popup.confirmation("Room " + RoomID  + "has been deleted");
        } catch (SQLException e) {
            System.out.println("Failed to delete room");

        } finally {
            con.close();
        }
    
    
    }
    
    
    


    

    public static void main(String[] args) throws SQLException {
        Room room = new Room();
        room.AddRoom(208, "Twin", 50);
//        System.out.println(room.getRoomType());
    }

}
