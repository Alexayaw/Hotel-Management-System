/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import Project.DatabaseAction;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Project.Popup;
import Project.SceneChange;
import Project.User;

/**
 *
 * @author user
 */
public class LoginController {
    
    Popup popup = new Popup();
     @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnLogin; /*on pressed, trigger the login() event*/
    
    @FXML
    private Label loginStatus;
    
    @FXML
    private AnchorPane loginPane; //the ori pane of the login page
    @FXML
    public  ImageView exitIcon;

    
    SceneChange scene = new SceneChange();
    
    User user = new User();
    public String UserID;
    
    public void getMainController(){
//    FXMLLoader fxmlLoader = new FXMLLoader();
//Pane p = fxmlLoader.load(getClass().getResource("Main.fxml").openStream());
//MainController mainController = (MainController) fxmlLoader.getController();

    }
    public String getUserID(){
        
    return UserID;
    }
    @FXML
    public void Login(ActionEvent event) throws IOException {
         UserID = txtUserName.getText();
    String UserPassword = txtPassword.getText();
      if (UserID!=""&&UserPassword!=""){
      if (user.loginValidate(UserID, UserPassword)){
          loginStatus.setText(" ");
          popup.loginConfirm();
         scene.Main(loginPane);
          
      }
      
      else{
           loginStatus.setVisible(true);
          loginStatus.setText("Invalid user ID or password, please try again! ");
      }
      }
        
     
      }
    //exit button 
    @FXML
     void exit(MouseEvent event) {
         if (popup.ExitPopup()){
            Stage stage = (Stage) exitIcon.getScene().getWindow();
            stage.close();
        

    }
     }}


