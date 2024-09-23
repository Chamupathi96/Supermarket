package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.OrderDetailsModel;
import model.OrderModel;

public class OrderDAO {
    
    private Connection connection;

    public OrderDAO(){
        try {
            this.connection = DBConnection.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrder(OrderModel order) throws SQLException {
        String sql = "INSERT INTO Orders (customer_id, order_date, total) VALUES (?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
        ps.setInt(1, order.getCustomerId());
        ps.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
        ps.setDouble(3, order.getTotal());
        ps.executeUpdate();

        // Retrieve generated order ID
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                order.setOrderId(generatedKeys.getInt(1)); // Set the generated order ID to the OrderModel
            }
        }
    } catch (SQLException e) {
        System.err.println("Error while adding order: " + e.getMessage());
        throw new SQLException("Unable to add order to database", e);
    }
    }

    public List<OrderModel> getAllOrders() throws SQLException {
        List<OrderModel> orderList = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try(PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                OrderModel order = new OrderModel();
                order.setOrderId(rs.getInt("id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setTotal(rs.getDouble("total"));
                orderList.add(order);
            }
        }catch (SQLException e) {
            System.err.println("Error while fetching orders: " + e.getMessage());
            throw new SQLException("Failed to retrieve orders. Please try again.", e);
        }

        return orderList;
    }

    public void updateStockQuantity(int itemId, int quantity) throws SQLException {
        String sql = "UPDATE Items SET stock_quantity = stock_quantity - ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, itemId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating stock quantity: " + e.getMessage());
            throw new SQLException("Failed to update stock quantity. Please try again.", e);
        }
    }

   public void addOrderDetails(OrderDetailsModel orderDetails) throws SQLException {
    String sql = "INSERT INTO OrderDetails (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, orderDetails.getOrderId());
        ps.setInt(2, orderDetails.getProductId());
        ps.setInt(3, orderDetails.getQuantity());
        ps.setDouble(4, orderDetails.getPrice());
        ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Error while adding order details: " + e.getMessage());
        throw new SQLException("Failed to add order details. Please try again.", e);
    }
}

}
