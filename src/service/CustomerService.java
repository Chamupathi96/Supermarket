package service;

import java.sql.SQLException;
import java.util.List;

import dao.CustomerDAO;
import model.CustomerModel;

public class CustomerService {
    private CustomerDAO customerDAO ;
       public CustomerService () {
        this.customerDAO = new CustomerDAO();
       }

       public void addCustomer(CustomerModel customer) throws SQLException {
        customerDAO.addCustomer(customer);
       }

       public void updateCustomer (CustomerModel customer) throws SQLException {
        customerDAO.updateCustomer(customer);
       }

       public void deleteCustomer(int CustomerId) throws SQLException {
        customerDAO.deleteCustomer(CustomerId);
       }

       public List <CustomerModel> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
       }
}
