package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.CustomerModel;

public class CustomerDAO {
    
    private Connection connection;

    public CustomerDAO(){
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(CustomerModel customer) throws SQLException{
        String sql = "INSERT INTO Customers (name,email,phone,address) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getAddress());
            ps.executeUpdate();
        }catch(SQLException e){
           System.err.println("Error while adding customers:" + e.getMessage());
           throw new SQLException("Failed to add customer.Please try again." + e);
        }
    }

    public void updateCustomer(CustomerModel customer) throws SQLException {
        String sql = "UPDATE Customers SET name = ?, email= ? , phone = ? , address = ? WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPhoneNumber());
            ps.setString(4, customer.getAddress());
            ps.setInt(5,customer.getCustomerId());
            ps.executeUpdate();
        }
    }

    public void deleteCustomer(int CustomerId) throws SQLException {
        String sql = "DELETE FROM Customers WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            System.out.println("Attempting to delete Customer with ID:"+CustomerId);

            ps.setInt(1,CustomerId);
            int rowsAffected = ps.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Customer with ID" + CustomerId + "deleted successfully");
            }else{
                System.out.println("No Customer found with ID" + CustomerId + ".");
            }

        }catch(SQLException e){
            System.err.println("Error while deleting customer:" + e.getMessage());
            throw new SQLException("Failed to delete customer. Please try again." +e);
        }
    }

    public List<CustomerModel> getAllCustomers() throws SQLException {
        List<CustomerModel> customerList = new ArrayList<>();
        String sql = "SELECT * FROM Customers";
        try(PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                CustomerModel customer = new CustomerModel(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
                customerList.add(customer);
            }
        }
        return customerList;
    }
    
}
