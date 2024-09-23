package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.OrderDetailsModel;

public class OrderDetailsDAO {

    private Connection connection;

    public OrderDetailsDAO() {
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public List<OrderDetailsModel> getOrderDetailsByOrderId(int orderId) throws SQLException {
    List<OrderDetailsModel> orderDetailsList = new ArrayList<>();
    String sql = "SELECT * FROM OrderDetails WHERE order_id = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, orderId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                OrderDetailsModel details = new OrderDetailsModel();
                details.setId(rs.getInt("id"));
                details.setOrderId(rs.getInt("order_id"));
                details.setProductId(rs.getInt("product_id"));
                details.setQuantity(rs.getInt("quantity"));
                details.setPrice(rs.getDouble("price"));
                orderDetailsList.add(details);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error while fetching order details: " + e.getMessage());
        throw new SQLException("Failed to retrieve order details. Please try again.", e);
    }

    return orderDetailsList;
}




}