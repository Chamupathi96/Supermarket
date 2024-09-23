package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.OrderDetailsModel;
import service.OrderDetailsService;

public class OrderDetailsController {

    @FXML
    private AnchorPane orderDetailsRoot;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TableView<OrderDetailsModel> tblOrderDetails;

    @FXML
    private TableColumn<OrderDetailsModel, Integer> colOrderDetailsId;

    @FXML
    private TableColumn<OrderDetailsModel, Integer> colOrderId;

    @FXML
    private TableColumn<OrderDetailsModel, Double> colPrice;

    @FXML
    private TableColumn<OrderDetailsModel, Integer> colProductId;

    @FXML
    private TableColumn<OrderDetailsModel, Integer> colQuantity;

    private OrderDetailsService orderDetailsService;

    public OrderDetailsController() {
        this.orderDetailsService = new OrderDetailsService();
    }

     @FXML
    public void initialize() {

       colOrderDetailsId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Assuming you are fetching data for a specific orderId
        int orderId = 1;  // This can be dynamic or passed from another view

        loadOrderDetails(orderId);
    }

    private void loadOrderDetails(int orderId) {
      try {
          List<OrderDetailsModel> orderDetailsList = orderDetailsService.getOrderDetailsByOrderId(orderId);
          ObservableList<OrderDetailsModel> orderDetailsObservableList = FXCollections.observableArrayList(orderDetailsList);
          tblOrderDetails.setItems(orderDetailsObservableList);
      } catch (SQLException e) {
          // Handle exceptions here
          e.printStackTrace();
      }
  }


    @FXML
    void btnViewOrderDetailsOnAction(ActionEvent event) {
        try {
            int orderId = Integer.parseInt(txtOrderId.getText());
            List<OrderDetailsModel> orderDetails = orderDetailsService.getOrderDetailsByOrderId(orderId);
            ObservableList<OrderDetailsModel> items = FXCollections.observableArrayList(orderDetails);
            tblOrderDetails.setItems(items);
        } catch (NumberFormatException e) {
            showError("Input Error", "Please enter a valid order ID.");
        } catch (SQLException e) {
            showError("Error loading order details", e.getMessage());
        }
    }

    @FXML
    void btnBackToHomeOnAction(ActionEvent event) {
        try {
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}