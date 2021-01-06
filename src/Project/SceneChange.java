/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author user
 */
public class SceneChange {
    
    @FXML
    public Pane expandedMenu;
    @FXML
    private Pane queenPane, twinPane, FamPane, VIPPane, cashPane, cardPane;
    //generic 
    public void changeScreen(String url,AnchorPane myPane) throws IOException{
       AnchorPane pane = FXMLLoader.load(getClass().getResource(url));//load the Main pane into pane
          myPane.getChildren().setAll(pane);
    }
    //switch to make reservation page 
    public void BookRoom(AnchorPane myPane) throws IOException {
           AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/MakeReservation.fxml"));//load the Main pane into pane
          myPane.getChildren().setAll(pane); //the login pane will be replaced by the Main pane
    }

    
    public void Guest(AnchorPane myPane) throws IOException {
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/GuestInfo.fxml"));//load the Main pane into pane
          myPane.getChildren().setAll(pane); //the login pane will be replaced by the Main pane
    }

    //open "view invoice" page
    public void Invoice(AnchorPane myPane) throws IOException {
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Invoice.fxml"));//load the Main pane into pane
          myPane.getChildren().setAll(pane); //the login pane will be replaced by the Main pane
    }

    //open "manage reservation" page
    public void ManageBooking(AnchorPane myPane) throws IOException {
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/ManageReservation.fxml"));//load the Main pane into pane
          myPane.getChildren().setAll(pane); //the login pane will be replaced by the Main pane
    }
   
    public void LogOut(AnchorPane myPane) throws IOException {
     AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));//load the Main pane into pane
          myPane.getChildren().setAll(pane); //the login pane will be replaced by the Main pane
    }
    
    public void Main(AnchorPane myPane) throws IOException{
    AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));//load the Main pane into pane
          myPane.getChildren().setAll(pane); 
    }
    public void Expand(){
        
    
        expandedMenu.setVisible(true);
    }
    public void Collapse(){
    
         expandedMenu.setVisible(false);
    }
    public void Disable(Pane visiblePane,Pane disablePane1,Pane disablePane2,Pane disablePane3){
        
        visiblePane.setDisable(false);
        disablePane1.setDisable(true);
        disablePane2.setDisable(true);
        disablePane3.setDisable(true);
    
    }
   //overloading 
    public void Disable(Pane p1){
    p1.setDisable(true);
    }
    public void Disable (Control control){
    control.setDisable(true);
    }
    public void Enable(Pane p1,Pane p2,Pane p3,Pane p4){
       p1.setDisable(false);
       p2.setDisable(false);
       p3.setDisable(false);
       p4.setDisable(false);
    }
    //overloading
    public void Enable(Pane p1){
    p1.setDisable(false);
    }
     public void Enable (Control control){
    control.setDisable(false);
    }
    
    public void cmbClear(ComboBox cmb){
   cmb.getSelectionModel().clearSelection();
// Clear value of ComboBox because clearSelection() does not do it
cmb.setValue(null);
    }
    
}

