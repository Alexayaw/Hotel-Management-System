/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

/**
 *
 * @author user
 */
import java.sql.*;
import javafx.scene.control.TableView;

public class User {

    String url = "jdbc:sqlite:C:/sqlite1/hotel.db";
    Table table = new Table();
    public static String UserID1;
    Popup popup = new Popup();

    public boolean loginValidate(String UserID, String UserPassword) throws SQLException {
 Connection con = DriverManager.getConnection(url);
        try {

           
            
            System.out.println("cONNECTION SUCESS");
            String query = "SELECT * FROM Login WHERE UserID = ? AND UserPassword=?"; //? is the placeholder to pass the parameter
            PreparedStatement loginQuery = con.prepareStatement(query);
            //first parameter
            loginQuery.setString(1, UserID);  //assign value for the first parameter
            //second parameter
            loginQuery.setString(2, UserPassword);
            ResultSet rs = loginQuery.executeQuery();
            if (rs.next()) {
                UserID1 = rs.getString("UserID");
                System.out.println(UserID1);
                return true;
            }
           

        } catch (SQLException e) {
            System.out.println(e);

        }
        finally{
         con.close();
        }

        return false;
    }

    public String getUserID() {
        return UserID1;

    }

    public void DisplayUserDetails(TableView tableview) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT * FROM Login";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            table.LoadData(rs, tableview);
        } catch (SQLException e) {
            System.out.println("Unable to load system user data");

        } finally {
            con.close();
        }
    }
    
    public boolean verifyUserID(String UserID) throws SQLException{
    Connection con = DriverManager.getConnection(url);
        try {
            String query = "SELECT [UserID] FROM Login Where UserID =?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, UserID);
            
           ResultSet rs =statement.executeQuery();
           if (rs.next()){
                System.out.print("Found user");
              
           return true;
          
           }
         
//            popup.confirmation("Succesfully created new non-admin user: "+ UserID);
        } catch (SQLException e) {
            System.out.println("Unable to verify userID");

        } finally{
          con.close();
        }
        return false;
    
    }

    public void AddAdminUser(String UserID, String UserName, String UserPassword) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "INSERT INTO [Login] ([UserID],[UserName],[UserPassword]) Values(?,?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, UserID);
            statement.setString(2,UserName);
            statement.setString(3,UserPassword);
           statement.executeUpdate();
           System.out.println("Succesfully created new admin user: "+ UserID);
        } catch (SQLException e) {
            System.out.println("Unable to create new user");
            System.out.println(e);

        } finally {
            con.close();
        }
    }
    public void UpdateToAdmin(String UserID) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "UPDATE [Login]SET[IsAdmin]='1' WHERE [UserID]=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, UserID);
           statement.executeUpdate();
           System.out.println("Succesfully created new admin user: "+ UserID);
        } catch (SQLException e) {
            System.out.println("Unable to create new user");

        } finally {
            con.close();
        }
    }
    
    
     public void AddNonAdminUser(String UserID, String UserName, String UserPassword) throws SQLException {
        Connection con = DriverManager.getConnection(url);
        try {
            String query = "INSERT INTO [Login] ([UserID],[UserName],[UserPassword],[IsAdmin]) Values(?,?,?,0)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, UserID);
            statement.setString(2,UserName);
            statement.setString(3,UserPassword);
           statement.executeUpdate();
            System.out.println("Succesfully created new non-admin user: "+ UserID);
        } catch (SQLException e) {
            System.out.println("Unable to create new user");

        } finally {
            con.close();
        }
    }
     
     public void DeleteUser(String UserID) throws SQLException{
     Connection con=DriverManager.getConnection(url);
     try{
         String query="DELETE FROM [Login] Where [UserID] = ?";
         PreparedStatement statement=con.prepareStatement(query);
          statement.setString(1, UserID);
          statement.executeUpdate();
             System.out.println("User: "+ UserID+" has been deleted from the system.");
          
     }catch(SQLException e){
         System.out.println("Unable to delete user");
     }finally{
     con.close();
     }
     
     
     }
     public static void main(String[]args) throws SQLException{
     User user = new User();
     
     user.DeleteUser("TEST5");
    
     
     }
    
    
    

    public boolean CheckIsAdmin(String UserID1) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        try {
            String query = "SELECT * FROM Login WHERE IsAdmin = '1' AND UserID =? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, UserID1);
            ResultSet rs = statement.executeQuery();
           
            if (rs.next()) {
                System.out.println(UserID1 + "is an admin");
                return true;

            }

        } catch (SQLException e) {
            System.out.println("Unable to verify Admin status");

        } finally {
            con.close();
        }
        return false;
    }

}
