/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.sql.*;

/**
 *
 * @author user
 */
public class sqlCon {

    public static void connect(){
        Connection con = null;
        try {
           
            String url ="jdbc:sqlite:C:/sqlite/db/hotel.db";
            con = DriverManager.getConnection(url);
            System.out.println("cONNECTION SUCESS");
    }
    catch (SQLException e){
System.out.println(e.getMessage());
    
    }finally {
        try{
        if (con !=null){
            con.close();
        }
        
        }catch (SQLException ex){
        System.out.println(ex.getMessage());
        }
        }
 
        // TODO: handle exception
    
}
    
    public static void main(String[] args){
    connect();
    }

}
