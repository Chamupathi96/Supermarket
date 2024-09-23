package service;

import java.sql.SQLException;
import java.util.List;

import dao.OrderDAO;
import model.OrderDetailsModel;
import model.OrderModel;

public class OrderService {
    

    private OrderDAO orderDAO;
    


      public OrderService(){
        this.orderDAO = new OrderDAO();
       
       }

       public void placeOrder(OrderModel order, int itemId, int quantity) throws SQLException {
        orderDAO.addOrder(order);
       
        orderDAO.updateStockQuantity(itemId, quantity);
    }

public void saveOrderDetails(OrderDetailsModel orderDetails) throws SQLException {
    orderDAO.addOrderDetails(orderDetails);
}



    public List<OrderModel> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }
}
