package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.ItemModel;

public class ItemDAO {

    private Connection connection;

    public ItemDAO() {
        try {
             this.connection = DBConnection.getInstance().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItem(ItemModel item) throws SQLException {
        String sql = "INSERT INTO Items ( name, category, price, stock_quantity) VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
           
            ps.setString(1, item.getItemName());
            ps.setString(2, item.getCategory());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getStockQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            System.err.println("Error while adding item: " + e.getMessage());
            throw new SQLException("Failed to add item. Please try again.", e);
        }
     }
 
public void updateItem(ItemModel item) throws SQLException{
    String sql = "UPDATE Items SET name = ?, category = ?, price = ? ,stock_quantity = ? WHERE id = ?";
    try(PreparedStatement ps = connection.prepareStatement(sql)){
        ps.setString(1, item.getItemName());
        ps.setString(2, item.getCategory());
        ps.setDouble(3, item.getPrice());
        ps.setInt(4, item.getStockQuantity());
        ps.setInt(5, item.getItemId());
        ps.executeUpdate();
    }
}

public void deleteItem(int itemId) throws SQLException {
    String sql = "DELETE FROM Items WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        // Log the itemId for debugging
        System.out.println("Attempting to delete item with ID: " + itemId);
        
        ps.setInt(1, itemId);
        int rowsAffected = ps.executeUpdate();
        
        // Log the result of the operation
        if (rowsAffected > 0) {
            System.out.println("Item with ID " + itemId + " deleted successfully.");
        } else {
            System.out.println("No item found with ID " + itemId + ".");
        }
    } catch (SQLException e) {
        // Log the exception for debugging purposes
        System.err.println("Error while deleting item: " + e.getMessage());
        throw new SQLException("Failed to delete item. Please try again.", e);
    }
}

public void updateStockQuantity(int itemId, int newQuantity) throws SQLException {
    String sql = "UPDATE Items SET stock_quantity = ? WHERE item_id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, newQuantity);
        ps.setInt(2, itemId);
        ps.executeUpdate();
    }
}

public List<ItemModel> getAllItems()throws SQLException{
    List<ItemModel> itemList = new ArrayList<>();
    String sql = "SELECT * FROM Items";
    try(PreparedStatement ps = connection.prepareStatement(sql);
       ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            ItemModel item = new ItemModel(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getDouble("price"),
                rs.getInt("stock_quantity")
                );
                itemList.add(item);

        }
       }
       return itemList;
}


public List<String> getCategories() throws SQLException {
    List<String> categories = new ArrayList<>();
    String sql = "SELECT DISTINCT category FROM Items";
    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            categories.add(rs.getString("category"));
        }
    }
    return categories;
}

public List<String> getItemNames() throws SQLException {
    List<String> itemNames = new ArrayList<>();
    String sql = "SELECT name FROM Items";
    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            itemNames.add(rs.getString("name"));
        }
    }
    return itemNames;
}



}
