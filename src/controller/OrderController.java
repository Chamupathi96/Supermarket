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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ItemModel;
import model.OrderDetailsModel;
import model.OrderModel;
import service.ItemService;

import service.OrderService;

public class OrderController {

    @FXML
    private AnchorPane orderRoot;

    @FXML
    private TableView<ItemModel> tblItem;
    
   @FXML
   private TableColumn<ItemModel, Double> colPrice;

    @FXML
    private TableColumn<ItemModel, Integer> colItemId;

    @FXML
    private TableColumn<ItemModel, String> colItemCategory;

    @FXML
    private TableColumn<ItemModel, String> colItemName;

    @FXML
    private TableColumn<ItemModel, Integer> colStockQuantity;



    @FXML
    private ComboBox<String> cbCategory;

    @FXML
    private ComboBox<String> cbItemNames;

    @FXML
    private DatePicker dpOrderDate;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtOrderQuantity;

    @FXML
    private TextField txtTotal;


    @FXML
    private TableView<OrderModel> tblOrder;

    @FXML
    private TableColumn<OrderModel, Integer> colCustomerId;

    @FXML
    private TableColumn<OrderModel, java.sql.Date> colOrderDate;

    @FXML
    private TableColumn<OrderModel, Integer> colOrderId;

    @FXML
    private TableColumn<OrderModel, Double> colTotal;

private ItemService itemService;
private OrderService orderService;

public OrderController(){
    this.itemService = new ItemService();
    this.orderService = new OrderService();
}
   
@FXML
    void initialize() {

        //item Table
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStockQuantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));

        //Order Table
       colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
       colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
       colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
       colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));



        loadTableData();
        populateComboBoxes();
        loadOrderData();

        // Set listener for item selection in table
        tblItem.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillItemDetails(newSelection);
            }
        });

        // Set listener for quantity input to calculate total
        txtOrderQuantity.textProperty().addListener((obs, oldText, newText) -> {
            calculateTotal();
        });
       

       }

       private void loadTableData() {
        try {
            ObservableList<ItemModel> items = FXCollections.observableArrayList(itemService.getAllItems());
            tblItem.setItems(items);
        } catch (SQLException e) {
            showError("Error loading items", e.getMessage());
        }
    }

    private void loadOrderData() {
        try {
            ObservableList<OrderModel> orders = FXCollections.observableArrayList(orderService.getAllOrders());
            tblOrder.setItems(orders);
        } catch (SQLException e) {
            showError("Error loading orders", e.getMessage());
        }
    }

   

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        try {
            ItemModel selectedItem = tblItem.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                showError("Select Item", "Please select an item to order.");
                return;
            }

            int itemId = selectedItem.getItemId();
            int quantity = Integer.parseInt(txtOrderQuantity.getText());
            double total = Double.parseDouble(txtTotal.getText());

            OrderModel order = new OrderModel();
            order.setCustomerId(Integer.parseInt(txtCustomerId.getText()));
            order.setOrderDate(java.sql.Date.valueOf(dpOrderDate.getValue()));
            order.setTotal(total);

            orderService.placeOrder(order, itemId, quantity);

        OrderDetailsModel orderDetails = new OrderDetailsModel();
        orderDetails.setOrderId(order.getOrderId()); // Assuming you have set the orderId in OrderModel
        orderDetails.setProductId(itemId);
        orderDetails.setQuantity(quantity);
        orderDetails.setPrice(selectedItem.getPrice());

        orderService.saveOrderDetails(orderDetails);

             showAlert("Order Placed", "Your order has been successfully placed!");
            loadTableData(); // Refresh the items table
            loadOrderData(); // Refresh the orders table
            clearFields(); // Clear the form fields
        } catch (SQLException e) {
            showError("Error placing order", e.getMessage());
        } catch (NumberFormatException e) {
            showError("Input Error", "Please enter a valid quantity.");
        }
    }

    private void fillItemDetails(ItemModel selectedItem) {
        cbItemNames.setValue(selectedItem.getItemName());
        cbCategory.setValue(selectedItem.getCategory());
        calculateTotal();
    }

    private void calculateTotal() {
        ItemModel selectedItem = tblItem.getSelectionModel().getSelectedItem();
    if (selectedItem == null) {
        txtTotal.setText("");
        return; // No item selected
    }
    
    try {
        int quantity = Integer.parseInt(txtOrderQuantity.getText());
        if (quantity < 0) {
            showError("Input Error", "Quantity cannot be negative.");
            txtTotal.setText("");
            return;
        }
        double total = quantity * selectedItem.getPrice();
        txtTotal.setText(String.valueOf(total));
    } catch (NumberFormatException e) {
        txtTotal.setText(""); // Clear total if input is invalid
    }
    }

   private void populateComboBoxes() {
    try {
        List<String> categories = itemService.getCategories();
        cbCategory.getItems().addAll(categories);

        List<String> itemNames = itemService.getItemNames();
        cbItemNames.getItems().addAll(itemNames);
    } catch (SQLException e) {
        showError("Error loading combo box data", e.getMessage());
    }
}
   


private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
   

private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtOrderId.clear();
        txtCustomerId.clear();
        txtOrderQuantity.clear();
        txtTotal.clear();
    dpOrderDate.setValue(null);    // Clear DatePicker (set to null)
    cbItemNames.getSelectionModel().clearSelection(); // Clear ComboBox selection
    cbCategory.getSelectionModel().clearSelection();
    }

   

    @FXML
    void btnBackToHomeOnAction(ActionEvent event) {

     try {
            // Load the Home.fxml UI again
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene to the Home UI
            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

 }

   
   



}
