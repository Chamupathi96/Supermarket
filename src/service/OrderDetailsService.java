package service;

import java.sql.SQLException;
import java.util.List;

import dao.OrderDetailsDAO;
import model.OrderDetailsModel;

public class OrderDetailsService {

    private OrderDetailsDAO orderDetailsDAO;
      public OrderDetailsService(){
        this.orderDetailsDAO = new OrderDetailsDAO();
      }

public List<OrderDetailsModel> getOrderDetailsByOrderId(int orderId) throws SQLException {
    return orderDetailsDAO.getOrderDetailsByOrderId(orderId);
}
   
}
