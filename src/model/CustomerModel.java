package model;

public class CustomerModel {
    
    private int customerId;
    private String customerName;
    private String email;
    private String phoneNumber;
    private String address;

    
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public CustomerModel(int customerId, String customerName, String email, String phoneNumber, String address) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
    
    public CustomerModel() {
    }

    
    @Override
    public String toString() {
        return "CustomerModel [customerId=" + customerId + ", customerName=" + customerName + ", email=" + email
                + ", phoneNumber=" + phoneNumber + ", address=" + address + "]";
    }

    
}
