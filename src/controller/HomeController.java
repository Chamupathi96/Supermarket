package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeController {

      @FXML
    private AnchorPane homeRoot;

    

    @FXML
    void btnItemsOnAction(ActionEvent event) {
      try {
            // Load the new Item.fxml UI
            Parent itemRoot = FXMLLoader.load(getClass().getResource("/view/Item.fxml"));

            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Set the new scene with the Item.fxml content
            Scene scene = new Scene(itemRoot);
            stage.setScene(scene);
            stage.setTitle("Items");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnCustomersOnAction(ActionEvent event) {
      try {
         Parent customerRoot = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         Scene scene = new Scene(customerRoot);
         stage.setScene(scene);
         stage.setTitle("Customers");
         stage.show();
         
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) {

      try {
        Parent orderRoot = FXMLLoader.load(getClass().getResource("/view/Order.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(orderRoot);
        stage.setScene(scene);
        stage.setTitle("Orders");
        stage.show();
        
     } catch (IOException e) {
       e.printStackTrace();
     }


    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) {

      try {
        Parent orderDetailsRoot = FXMLLoader.load(getClass().getResource("/view/OrderDetails.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(orderDetailsRoot);
        stage.setScene(scene);
        stage.setTitle("Order Details");
        stage.show();
        
     } catch (IOException e) {
       e.printStackTrace();
     }

    }

   

    @FXML
    void btnLogoutOnAction(ActionEvent event) {
        try {
            // Load the login screen FXML
            Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
    
            // Get the current stage (window)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
            // Set the new scene with the Login.fxml content
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login"); // Set the title for the login screen
            stage.show();
    
            // If using sessions, clear user session or data here
            // Example: UserSession.clear(); // Hypothetical method to clear session
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
   

    
}
