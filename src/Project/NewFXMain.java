/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Project;

import javafx.application.Application;
import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author user
 */
public class NewFXMain extends Application {
    private double xOffset = 0 , yOffset = 0; 
    
   public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)throws Exception {
//   
        //load FXML file and returns the JAVAFx GUI component declared in the FXML file 
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")); //treat the path carefully, one wrong words would resu;t in exception in application start method
        Scene scene = new Scene(root);
        //makes undecorated window movable 
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        
        scene.getStylesheets().add("/fxml/Main.css");
        //remove title bar 
        scene.setFill(Color.TRANSPARENT);
       primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //popup
        
    }

    /**
     * @param args the command line arguments
     */
    
    
}
