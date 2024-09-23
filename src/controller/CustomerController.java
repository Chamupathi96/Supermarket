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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CustomerModel;
import service.CustomerService;

public class CustomerController {

    @FXML
    private AnchorPane customerRoot;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtEmail;

    @FXML
    private TableView<CustomerModel> tblCustomer;


     @FXML
    private TableColumn<CustomerModel, String> colAddress;

    @FXML
    private TableColumn<CustomerModel, Integer> colCustomerId;

    @FXML
    private TableColumn<CustomerModel, String> colCustomerName;

    @FXML
    private TableColumn<CustomerModel, String> colEmail;

    @FXML
    private TableColumn<CustomerModel, String> colPhoneNumber;

   private CustomerService customerService;
       
      public CustomerController() {
         this.customerService = new CustomerService();
      }

      @FXML
       void initialize(){
         colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
         colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
         colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
         colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
         colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

         loadTableData();

         tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable ,oldValue , newValue)->{
            if(newValue !=null){
               populateFields(newValue);
            }
         });
       }

       private void loadTableData(){
         try {
            ObservableList <CustomerModel> customers = FXCollections.observableArrayList(customerService.getAllCustomers());
            tblCustomer.setItems(customers);
         } catch (SQLException e) {
            showErrorAlert("Error loading customers",e.getMessage());
         }
       }

    @FXML
    void btnCustomerAddOnAction(ActionEvent event) {
      try{
         CustomerModel newCustomer = new CustomerModel(
            Integer.parseInt(txtCustomerId.getText()),
            txtCustomerName.getText(),
            txtEmail.getText(),
            txtPhoneNumber.getText(),
            txtAddress.getText()
         );
         customerService.addCustomer(newCustomer);
         showSuccessAlert("Success","Customer added Successfully.");
         loadTableData();
         clearFields();
      }catch(SQLException e){
         showErrorAlert("Error", "Failed to Added Customer");
      }catch (NumberFormatException e){
         showErrorAlert("Validation Error", "Invalid input.please cheque your data");
      }
    }

    @FXML
    void btnCustomerUpdateOnAction(ActionEvent event) {
         CustomerModel selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
         if(selectedCustomer == null){
            showErrorAlert("Selection Error", "No customer selected fro update");
            return;
         }

         try{
            selectedCustomer.setCustomerId(Integer.parseInt(txtCustomerId.getText()));
            selectedCustomer.setCustomerName(txtCustomerName.getText());
            selectedCustomer.setEmail(txtEmail.getText());
            selectedCustomer.setPhoneNumber(txtPhoneNumber.getText());
            selectedCustomer.setAddress(txtAddress.getText());

            customerService.updateCustomer(selectedCustomer);
            showSuccessAlert("Success","Customer Updated Successfully.");
            loadTableData();
            clearFields();
         }catch(SQLException e){
            showErrorAlert("Error","Failed to update customer");
         }catch(NumberFormatException e){
            showErrorAlert("Validation Error", "Invalid input, Please Cheque your data");
         }
    }

    @FXML
    void btnCustomerDeleteOnAction(ActionEvent event) {
         CustomerModel selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
         if(selectedCustomer != null){
            try{
               customerService.deleteCustomer(selectedCustomer.getCustomerId());
               showSuccessAlert("Success","Customer deleted Successfully");
               loadTableData();
               clearFields();
            }catch(SQLException e){
               showErrorAlert("Error", "Failed to delete Customer");
            }
         }else{
            showErrorAlert("Selection Error", "No Customer selected, Please select an Customer to Delete");
         }
    }

   private void showErrorAlert (String title, String message){
      Alert alert = new Alert (AlertType.ERROR);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
   }

   private void clearFields(){
      txtCustomerId.clear();
      txtCustomerName.clear();
      txtEmail.clear();
      txtPhoneNumber.clear();
      txtAddress.clear();
   }

   private void populateFields( CustomerModel customer){
      txtCustomerId.setText(String.valueOf(customer.getCustomerId()));
      txtCustomerName.setText(customer.getCustomerName());
      txtEmail.setText(customer.getEmail());
      txtPhoneNumber.setText(customer.getPhoneNumber());
      txtAddress.setText(customer.getAddress());
   }

   private void showSuccessAlert (String title , String message){
      Alert alert = new Alert (Alert.AlertType.INFORMATION);
      alert.setTitle(title);
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
   }


    @FXML
    void btnBackToHomeOnAction(ActionEvent event) {
     try {
        Parent homeRoot = FXMLLoader.load(getClass().getResource("/view/Home.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(homeRoot);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
     } catch (IOException e) {
        e.printStackTrace();
     }
    }


    
}
