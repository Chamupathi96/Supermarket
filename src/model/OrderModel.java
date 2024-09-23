package model;

import java.sql.Date;

public class OrderModel {
    
    private int orderId;
    private int customerId;
    private Date orderDate;
    private double total;

    
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    
    public OrderModel(int orderId, int customerId, Date orderDate, double total) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.total = total;
    }

    
    public OrderModel() {
    }

    
    @Override
    public String toString() {
        return "OrderModel [orderId=" + orderId + ", customerId=" + customerId + ", orderDate=" + orderDate + ", total="
                + total + "]";
    }


    
}
