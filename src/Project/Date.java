/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author user
 */
public class Date {
    public void formatDate(Date date){
     SimpleDateFormat sdf = new SimpleDateFormat( "MM-dd-yyyy");
     sdf.format(date);
    }
    public static void main(String[] args) {
       // LocalDate instance
    LocalDate localDate = LocalDate.now();
    System.out.println("LocalDate : " + localDate);

    // Get sql Date from LocalDate instance
    java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
    System.out.println("LocalDate to sql Date : " + sqlDate);
    
//
////        String formattedDate = formatter.format(LocalDate.now());
//
//        System.out.println(formattedDate);
    }
}
