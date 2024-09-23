package controller;

import java.io.IOException;
import java.sql.SQLException;

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
import model.ItemModel;
import service.ItemService;

public class ItemController {
    
    @FXML
    private AnchorPane itemRoot;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtStockQuantity;


    @FXML
    private TableView<ItemModel> tblItem;


    @FXML
    private TableColumn<ItemModel, String> colItemCategory;

    @FXML
    private TableColumn<ItemModel, Integer> colItemId;

    @FXML
    private TableColumn<ItemModel, String> colItemName;

    @FXML
    private TableColumn<ItemModel, Double> colPrice;

    @FXML
    private TableColumn<ItemModel, Integer> colStockQuantity;

    private ItemService itemService;

    public ItemController() {
        this.itemService = new ItemService();
    }

@FXML
    void initialize() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStockQuantity.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));

        loadTableData();

        // Add listener to update text fields when an item is selected
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateFields(newValue);
            }
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


    @FXML
    void btnAddItemOnAction(ActionEvent event) {
      

        try {
            ItemModel newItem = new ItemModel(
                Integer.parseInt(txtItemId.getText()),
                txtItemName.getText(),
                txtCategory.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtStockQuantity.getText())
            );
            itemService.addItem(newItem);
            showSuccessAlert("Success", "Item added successfully.");
            loadTableData();  // Refresh table data
            clearFields();
        } catch (SQLException e) {
            showErrorAlert("Error", "Failed to add item.");
        } catch (NumberFormatException e) {
            showErrorAlert("Validation Error", "Invalid input. Please check your data.");
        }

        
    }

    @FXML
    void btnItemUpdateOnAction(ActionEvent event) {
          // Get the selected item from the table view
    ItemModel selectedItem = tblItem.getSelectionModel().getSelectedItem();
    
    if (selectedItem == null) {
        showErrorAlert("Selection Error", "No item selected for update.");
        return;
    }

    try {
        // Update the selected item with new data from text fields
        selectedItem.setItemId(Integer.parseInt(txtItemId.getText())); // Item ID remains the same, but you might need to verify
        selectedItem.setItemName(txtItemName.getText());
        selectedItem.setCategory(txtCategory.getText());
        selectedItem.setPrice(Double.parseDouble(txtPrice.getText()));
        selectedItem.setStockQuantity(Integer.parseInt(txtStockQuantity.getText()));

        itemService.updateItem(selectedItem);
        showSuccessAlert("Success", "Item updated successfully.");
        loadTableData(); // Refresh table data
        clearFields();
    } catch (SQLException e) {
        showErrorAlert("Error", "Failed to update item.");
    } catch (NumberFormatException e) {
        showErrorAlert("Validation Error", "Invalid input. Please check your data.");
    }
    }

    @FXML
    void btnItemDeleteOnAction(ActionEvent event) {
      // Check if an item is selected
      ItemModel selectedItem = tblItem.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
          try {
              itemService.deleteItem(selectedItem.getItemId());
              showSuccessAlert("Success", "Item deleted successfully.");
              loadTableData();  // Refresh table data
              clearFields();
          } catch (SQLException e) {
              showErrorAlert("Error", "Failed to delete item.");
          }
      } else {
          showErrorAlert("Selection Error", "No item selected. Please select an item to delete.");
      }
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

 private void clearFields() {
    txtItemId.clear();
    txtItemName.clear();
    txtCategory.clear();
    txtPrice.clear();
    txtStockQuantity.clear();
}

private void showErrorAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}


private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void populateFields(ItemModel item) {
        txtItemId.setText(String.valueOf(item.getItemId()));
        txtItemName.setText(item.getItemName());
        txtCategory.setText(item.getCategory());
        txtPrice.setText(String.valueOf(item.getPrice()));
        txtStockQuantity.setText(String.valueOf(item.getStockQuantity()));
    }


    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

   


}
