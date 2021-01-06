/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author user
 */
public class Popup {

    public void SettingPopup() throws IOException {

//        Popup popup = new Popup();
//PopupController controller = new PopupController();
//FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopUp.fxml"));
//loader.setController(controller);
//popup.getContent().add((Parent)loader.load());
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL); // set the stage as pop-up 
        stage.setTitle("System setting");
        Parent popup = FXMLLoader.load(getClass().getResource("/fxml/SystemSetting.fxml")); //treat the path carefully, one wrong words would resu;t in exception in application start method
        Scene scene = new Scene(popup);
         stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public boolean ExitPopup() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    public void loginConfirm() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Login Sucess! Welcome Back!");

        alert.showAndWait();
    }
    
    public void confirmation(String msg){
    Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public void displayError(String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        
        alert.showAndWait();
    }

    public boolean logoutConfirm() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log Out Confirmation ");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }
    public boolean ActionConfirm(String msg){
    Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    
    }
}
