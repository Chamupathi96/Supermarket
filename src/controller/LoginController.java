package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

     @FXML
    private PasswordField passwordLogin;

      @FXML
    private TextField txtUsername;

     @FXML
    private AnchorPane root;


    @FXML
    void btnLoginOnAction(ActionEvent event) {
         String username = txtUsername.getText();
         String password = passwordLogin.getText();

         try {
            if(authenticate(username,password)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
                Parent homeRoot = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(homeRoot));
                stage.setTitle("Dashboard");
                stage.show();

                 
            // Close the login stage
            Stage loginStage = (Stage) txtUsername.getScene().getWindow();
            loginStage.close();

             }else {
            // Show an error message
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid credentials. Please try again.");
            alert.showAndWait();
        }
         } catch (IOException e) {
            e.printStackTrace();
         }
    }

    private boolean authenticate(String username, String password) {
        // Replace this with actual authentication logic
        return "admin".equals(username) && "password".equals(password);
    }

    
    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
    
}
